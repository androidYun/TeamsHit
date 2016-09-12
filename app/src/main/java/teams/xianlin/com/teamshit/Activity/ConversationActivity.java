package teams.xianlin.com.teamshit.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.eventbus.EventBus;
import io.rong.imkit.RongCallKit;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.widget.AlterDialogFragment;
import io.rong.imkit.widget.InputView;
import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.location.RealTimeLocationConstant;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Discussion;
import io.rong.imlib.model.PublicServiceProfile;
import io.rong.imlib.model.UserInfo;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.EventBus.OperatorFriendEvent;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.RongYun.RongEvent;
import teams.xianlin.com.teamshit.RongYun.SealAppContext;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.SPUtils;
import teams.xianlin.com.teamshit.db.DBManager;
import teams.xianlin.com.teamshit.db.Friend;
import teams.xianlin.com.teamshit.widget.DialogUtils.LoadingDialog;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/7/7.
 */
public class ConversationActivity extends BaseActivity implements RongIMClient.RealTimeLocationListener {
    @Bind(R.id.navi_top)
    TitleNavi naviTop;
    private String mTargetId;//好友id

    private Conversation.ConversationType mConversationType;//回话类型

    private String mTitle;

    private LoadingDialog mDialog;

    private RelativeLayout mRealTimeBar;//real-time bar

    private RealTimeLocationConstant.RealTimeLocationStatus currentLocationStatus;

    /**
     * 是否在讨论组内，如果不在讨论组内，则进入不到讨论组设置页面
     */
    private boolean isDiscussion = false;

    private boolean isFromPush = false;//是否来自于推送

    private final String TextTypingTitle = "对方正在输入...";

    private final String VoiceTypingTitle = "对方正在讲话...";

    public static final int SET_TEXT_TYPING_TITLE = 1;//正在输入文字

    public static final int SET_VOICE_TYPING_TITLE = 2;//正在输入语音

    public static final int SET_TARGETID_TITLE = 0;

    private Intent mIntent;

    private String mNickName;//昵称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        naviTop.setClickCallBack(this);
    }

    @Override
    protected void initialData() {
        mIntent = getIntent();
        if (mIntent == null || mIntent.getData() == null)
            return;
        mTargetId = mIntent.getData().getQueryParameter(Constant.Target_Id);  //10000 为 Demo Server 加好友的 id，若 targetId 为 10000，则为加好友消息，默认跳转到 NewFriendListActivity
        if (mTargetId != null && mTargetId.equals("10000")) {  // Demo 逻辑
            startActivity(new Intent(ConversationActivity.this, NewFriendActivity.class));
            return;
        }
        mConversationType = Conversation.ConversationType.valueOf(mIntent.getData()
                .getLastPathSegment().toUpperCase(Locale.getDefault()));
        setActionBarTitle(mConversationType, mTargetId);
        mTitle = mIntent.getData().getQueryParameter(Constant.connection_title);
        isPushMessage(mIntent);
        //setRealTime();//开始共享位置
        Friend friend = DBManager.getInstance(mActivity).getDaoSession().getFriendDao().load(mTargetId);
        SealAppContext.getInstance().pushActivity(mConversationType, mTargetId, this);
        if (friend != null) {
            mNickName = friend.getName();
        }
    }

    @Override
    protected void inflateView() {
        setSpeaking();
        naviTop.setBackTitle("" + mNickName);
        naviTop.setFinishImgVisible();
    }

    /**
     * 判断聊天的状态
     */
    public void setSpeaking() {
        RongIMClient.setTypingStatusListener(new RongIMClient.TypingStatusListener() {
            @Override
            public void onTypingStatusChanged(Conversation.ConversationType type, String targetId, Collection<TypingStatus> typingStatusSet) {
                //当输入状态的会话类型和targetID与当前会话一致时，才需要显示
                if (type.equals(mConversationType) && targetId.equals(mTargetId)) {
                    int count = typingStatusSet.size();
                    //count表示当前会话中正在输入的用户数量，目前只支持单聊，所以判断大于0就可以给予显示了
                    if (count > 0) {
                        Iterator iterator = typingStatusSet.iterator();
                        TypingStatus status = (TypingStatus) iterator.next();
                        String objectName = status.getTypingContentType();
                        MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
                        MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
                        //匹配对方正在输入的是文本消息还是语音消息
                        if (objectName.equals(textTag.value())) {
                            mHandler.sendEmptyMessage(SET_TEXT_TYPING_TITLE);
                        } else if (objectName.equals(voiceTag.value())) {
                            mHandler.sendEmptyMessage(SET_VOICE_TYPING_TITLE);
                        }
                    } else {//当前会话没有用户正在输入，标题栏仍显示原来标题
                        mHandler.sendEmptyMessage(SET_TARGETID_TITLE);
                    }
                }
            }
        });
    }

    Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SET_TEXT_TYPING_TITLE:

                    break;
                case SET_VOICE_TYPING_TITLE:

                    break;
                case SET_TARGETID_TITLE:

                    break;
                default:
                    break;
            }
            return true;
        }
    }
    );

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent == null || intent.getData() == null)
            return;
        enterFragment(Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault())), intent.getData().getQueryParameter("targetId"));
    }

    /**
     * 判断是否是 Push 消息，判断是否需要做 connect 操作
     *
     * @param intent
     */
    private void isPushMessage(Intent intent) {
        if (intent == null || intent.getData() == null)
            return;
        //push
        if (intent.getData().getScheme().equals("rong") && intent.getData().getQueryParameter("isFromPush") != null) {

            //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息
            if (intent.getData().getQueryParameter("isFromPush").equals("true")) {
                //只有收到系统消息和不落地 push 消息的时候，pushId 不为 null。而且这两种消息只能通过 server 来发送，客户端发送不了。
                String id = intent.getData().getQueryParameter("pushId");
//                RongIM.getInstance().getRongIMClient().recordNotificationEvent(id);
                if (mDialog != null && !mDialog.isShowing()) {
                    mDialog.show();
                }
                isFromPush = true;
                enterActivity();
            } else if (RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED)) {
                if (mDialog != null && !mDialog.isShowing()) {
                    mDialog.show();
                }
                enterActivity();
            } else {
                enterFragment(mConversationType, mTargetId);
            }

        } else {
            if (RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED)) {
                if (mDialog != null && !mDialog.isShowing()) {
                    mDialog.show();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        enterActivity();
                    }
                }, 300);
            } else {
                enterFragment(mConversationType, mTargetId);
            }
        }
    }

    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType
     * @param mTargetId
     */
    private ConversationFragment fragment;

    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {
        /**
         * 来刷新好友信心
         */

        Friend friend = DBManager.getInstance(mActivity).getDaoSession().getFriendDao().load(mTargetId);
        if (friend != null) {
            LogUtil.d("" + friend.toString());
            RongIM.getInstance().refreshUserInfoCache(new UserInfo(friend.getUserId(), friend.getDisplayName(), Uri.parse(friend.getPortraitUri())));
        }
        fragment = new ConversationFragment();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();
        fragment.setUri(uri);
        fragment.setInputBoardListener(new InputView.IInputBoardListener() {
            @Override
            public void onBoardExpanded(int height) {
                LogUtil.e("Main", "onBoardExpanded h : " + height);
            }

            @Override
            public void onBoardCollapsed() {
                LogUtil.e("Main", "onBoardCollapsed");
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //xxx 为你要加载的 id
        transaction.add(R.id.rong_content, fragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 收到 push 消息后，选择进入哪个 Activity
     * 如果程序缓存未被清理，进入 MainActivity
     * 程序缓存被清理，进入 LoginActivity，重新获取token
     * <p/>
     * 作用：由于在 manifest 中 intent-filter 是配置在 ConversationActivity 下面，所以收到消息后点击notifacition 会跳转到 DemoActivity。
     * 以跳到 MainActivity 为例：
     * 在 ConversationActivity 收到消息后，选择进入 MainActivity，这样就把 MainActivity 激活了，当你读完收到的消息点击 返回键 时，程序会退到
     * MainActivity 页面，而不是直接退回到 桌面。
     */
    private void enterActivity() {
        String token = (String) SPUtils.get(ConversationActivity.this, Constant.Rong_Token, "");//loginToken

        if (token.equals("default")) {
            LogUtil.e("ConversationActivity push", "push2");
            startActivity(new Intent(ConversationActivity.this, LoginActivity.class));
            finish();
        } else {
            LogUtil.e("ConversationActivity push", "push3");
            reconnect(token);
        }
    }

    private void reconnect(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e("Main", "---onTokenIncorrect--");
            }

            @Override
            public void onSuccess(String s) {
                Log.i("Main", "---onSuccess--" + s);
                LogUtil.e("ConversationActivity push", "push4");

                if (mDialog != null)
                    mDialog.dismiss();
                enterFragment(mConversationType, mTargetId);

            }

            @Override
            public void onError(RongIMClient.ErrorCode e) {
                Log.e("Main", "---onError--" + e);
                if (mDialog != null)
                    mDialog.dismiss();

                enterFragment(mConversationType, mTargetId);
            }
        });

    }

    @Override
    public void onRightImgClick() {
        super.onRightImgClick();
        Intent singleChatInforIntent = new Intent(mActivity, SingleChatInforActivity.class);
        singleChatInforIntent.putExtra(Constant.Target_Id, mTargetId);
        startActivity(singleChatInforIntent);
    }

    public void onEventMainThread(OperatorFriendEvent event) {
        if (event.getFriendType() == 2 && mTargetId.equals(String.valueOf(event.getFriendType()))) {
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        if ("ConversationActivity".equals(this.getClass().getSimpleName()))
            SealAppContext.getInstance().popActivity(mConversationType, mTargetId);
        //CallKit start 3
        RongCallKit.setGroupMemberProvider(null);
        //CallKit end 3
        super.onDestroy();
    }

    /**
     * 设置会话页面 Title
     *
     * @param conversationType 会话类型
     * @param targetId         目标 Id
     */
    private void setActionBarTitle(Conversation.ConversationType conversationType, String targetId) {

        if (conversationType == null)
            return;

        if (conversationType.equals(Conversation.ConversationType.PRIVATE)) {
            naviTop.setBackTitle(targetId);
        } else if (conversationType.equals(Conversation.ConversationType.GROUP)) {
            naviTop.setBackTitle(targetId);
        } else if (conversationType.equals(Conversation.ConversationType.DISCUSSION)) {
            naviTop.setBackTitle(targetId);
        } else if (conversationType.equals(Conversation.ConversationType.CHATROOM)) {
            naviTop.setBackTitle(mTitle);
        } else if (conversationType.equals(Conversation.ConversationType.SYSTEM)) {
            naviTop.setBackTitle(getResources().getString(R.string.de_actionbar_system));
        } else if (conversationType.equals(Conversation.ConversationType.APP_PUBLIC_SERVICE)) {
            setAppPublicServiceActionBar(targetId);
        } else if (conversationType.equals(Conversation.ConversationType.PUBLIC_SERVICE)) {
            setPublicServiceActionBar(targetId);
        } else if (conversationType.equals(Conversation.ConversationType.CUSTOMER_SERVICE)) {
            naviTop.setBackTitle(getResources().getString(R.string.main_customer));
        } else {
            naviTop.setBackTitle(getResources().getString(R.string.de_actionbar_sub_defult));
        }
    }

    /**
     * 设置群聊界面 ActionBar
     *
     * @param targetId
     */
    private void setGroupActionBar(String targetId) {
        if (!TextUtils.isEmpty(mTitle)) {
            naviTop.setBackTitle(mTitle);
        } else {
            naviTop.setBackTitle(targetId);
        }
    }

    /**
     * 设置应用公众服务界面 ActionBar
     */
    private void setAppPublicServiceActionBar(String targetId) {
        if (targetId == null)
            return;

        RongIM.getInstance().getPublicServiceProfile(Conversation.PublicServiceType.APP_PUBLIC_SERVICE
                , targetId, new RongIMClient.ResultCallback<PublicServiceProfile>() {
                    @Override
                    public void onSuccess(PublicServiceProfile publicServiceProfile) {
                        naviTop.setBackTitle(publicServiceProfile.getName());
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
    }

    /**
     * 设置公共服务号 ActionBar
     */
    private void setPublicServiceActionBar(String targetId) {

        if (targetId == null)
            return;


        RongIM.getInstance().getPublicServiceProfile(Conversation.PublicServiceType.PUBLIC_SERVICE
                , targetId, new RongIMClient.ResultCallback<PublicServiceProfile>() {
                    @Override
                    public void onSuccess(PublicServiceProfile publicServiceProfile) {
                        naviTop.setBackTitle(publicServiceProfile.getName().toString());
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
    }

    /**
     * 设置讨论组界面 ActionBar
     */
    private void setDiscussionActionBar(String targetId) {

        if (targetId != null) {

            RongIM.getInstance().getDiscussion(targetId
                    , new RongIMClient.ResultCallback<Discussion>() {
                        @Override
                        public void onSuccess(Discussion discussion) {
                            naviTop.setBackTitle(discussion.getName());
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode e) {
                            if (e.equals(RongIMClient.ErrorCode.NOT_IN_DISCUSSION)) {
                                naviTop.setBackTitle("不在讨论组中");
                                isDiscussion = true;
                                supportInvalidateOptionsMenu();
                            }
                        }
                    });
        } else {
            naviTop.setBackTitle("讨论组");
        }
    }

    /**
     * 设置私聊界面 ActionBar
     */
    private void setPrivateActionBar(String targetId) {
        if (!TextUtils.isEmpty(mTitle)) {
            setTitle(mTitle);
            naviTop.setBackTitle(mTitle);
        } else {
            naviTop.setBackTitle(targetId);
        }
    }
  /*－－－－－－－－－－－－－地理位置共享 start－－－－－－－－－*/

    private void setRealTime() {

        mRealTimeBar = (RelativeLayout) this.findViewById(R.id.layout);

        mRealTimeBar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (currentLocationStatus == null)
                    currentLocationStatus = RongIMClient.getInstance().getRealTimeLocationCurrentState(mConversationType, mTargetId);

                if (currentLocationStatus == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_INCOMING) {

                    final AlterDialogFragment alterDialogFragment = AlterDialogFragment.newInstance("", "加入位置共享", "取消", "加入");
                    alterDialogFragment.setOnAlterDialogBtnListener(new AlterDialogFragment.AlterDialogBtnListener() {

                        @Override
                        public void onDialogPositiveClick(AlterDialogFragment dialog) {
                            RealTimeLocationConstant.RealTimeLocationStatus status = RongIMClient.getInstance().getRealTimeLocationCurrentState(mConversationType, mTargetId);

                            if (status == null || status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_IDLE) {
                                startRealTimeLocation();
                            } else {
                                joinRealTimeLocation();
                            }

                        }

                        @Override
                        public void onDialogNegativeClick(AlterDialogFragment dialog) {
                            alterDialogFragment.dismiss();
                        }
                    });
                    alterDialogFragment.show(getSupportFragmentManager());

                } else {
                    Intent intent = new Intent(ConversationActivity.this, RealTimeLocationActivity.class);
                    intent.putExtra("conversationType", mConversationType.getValue());
                    intent.putExtra("targetId", mTargetId);
                    startActivity(intent);
                }
            }
        });

        if (!TextUtils.isEmpty(mTargetId) && mConversationType != null) {

            RealTimeLocationConstant.RealTimeLocationErrorCode errorCode = RongIMClient.getInstance().getRealTimeLocation(mConversationType, mTargetId);
            if (errorCode == RealTimeLocationConstant.RealTimeLocationErrorCode.RC_REAL_TIME_LOCATION_SUCCESS || errorCode == RealTimeLocationConstant.RealTimeLocationErrorCode.RC_REAL_TIME_LOCATION_IS_ON_GOING) {
                RongIMClient.getInstance().addRealTimeLocationListener(mConversationType, mTargetId, this);//设置监听
                currentLocationStatus = RongIMClient.getInstance().getRealTimeLocationCurrentState(mConversationType, mTargetId);

                if (currentLocationStatus == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_INCOMING) {
                    showRealTimeLocationBar(currentLocationStatus);
                }
            }
        }
    }

    private void startRealTimeLocation() {
        RongIMClient.getInstance().startRealTimeLocation(mConversationType, mTargetId);
        Intent intent = new Intent(ConversationActivity.this, RealTimeLocationActivity.class);
        intent.putExtra("conversationType", mConversationType.getValue());
        intent.putExtra("targetId", mTargetId);
        startActivity(intent);
    }

    private void joinRealTimeLocation() {
        RongIMClient.getInstance().joinRealTimeLocation(mConversationType, mTargetId);
        Intent intent = new Intent(ConversationActivity.this, RealTimeLocationActivity.class);
        intent.putExtra("conversationType", mConversationType.getValue());
        intent.putExtra("targetId", mTargetId);
        startActivity(intent);
    }

    private void showRealTimeLocationBar(RealTimeLocationConstant.RealTimeLocationStatus status) {

        if (status == null)
            status = RongIMClient.getInstance().getRealTimeLocationCurrentState(mConversationType, mTargetId);

        final List<String> userIds = RongIMClient.getInstance().getRealTimeLocationParticipants(mConversationType, mTargetId);

        if (status != null && status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_INCOMING) {

            if (userIds != null && userIds.get(0) != null && userIds.size() == 1) {
//                locationid = userIds.get(0);
//                request(GETUSERINFO);
            } else {
                if (userIds != null && userIds.size() > 0) {
                    if (mRealTimeBar != null) {
                        TextView textView = (TextView) mRealTimeBar.findViewById(android.R.id.text1);
                        if (userIds.size() <= 1) {
                            textView.setText(userIds.size() + " " + getApplicationContext().getString(R.string.person_is_sharing_loaction));
                        } else {
                            textView.setText(userIds.size() + " " + getApplicationContext().getString(R.string.persons_are_sharing_loaction));
                        }
                    }
                } else {
                    if (mRealTimeBar != null && mRealTimeBar.getVisibility() == View.VISIBLE) {
                        mRealTimeBar.setVisibility(View.GONE);
                    }
                }
            }

        } else if (status != null && status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_OUTGOING) {
            TextView textView = (TextView) mRealTimeBar.findViewById(android.R.id.text1);
            textView.setText(getApplicationContext().getString(R.string.you_are_sharing_location));
        } else {

            if (mRealTimeBar != null && userIds != null) {
                TextView textView = (TextView) mRealTimeBar.findViewById(android.R.id.text1);
                if (userIds.size() <= 1) {
                    textView.setText(userIds.size() + " " + getApplicationContext().getString(R.string.person_is_sharing_loaction));
                } else {
                    textView.setText(userIds.size() + " " + getApplicationContext().getString(R.string.persons_are_sharing_loaction));
                }
            }
        }

        if (userIds != null && userIds.size() > 0) {

            if (mRealTimeBar != null && mRealTimeBar.getVisibility() == View.GONE) {
                mRealTimeBar.setVisibility(View.VISIBLE);
            }
        } else {

            if (mRealTimeBar != null && mRealTimeBar.getVisibility() == View.VISIBLE) {
                mRealTimeBar.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onStatusChange(final RealTimeLocationConstant.RealTimeLocationStatus status) {
        currentLocationStatus = status;

        EventBus.getDefault().post(status);

        if (status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_IDLE) {
            hideRealTimeBar();
            RealTimeLocationConstant.RealTimeLocationErrorCode errorCode = RongIMClient.getInstance().getRealTimeLocation(mConversationType, mTargetId);

            if (errorCode == RealTimeLocationConstant.RealTimeLocationErrorCode.RC_REAL_TIME_LOCATION_SUCCESS) {
                RongIM.getInstance().insertMessage(mConversationType, mTargetId, RongIM.getInstance().getCurrentUserId(),
                        InformationNotificationMessage.obtain(getApplicationContext().getString(R.string.location_sharing_ended)));
            }
        } else if (status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_OUTGOING) {//发自定义消息
            showRealTimeLocationBar(status);
        } else if (status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_INCOMING) {
            showRealTimeLocationBar(status);
        } else if (status == RealTimeLocationConstant.RealTimeLocationStatus.RC_REAL_TIME_LOCATION_STATUS_CONNECTED) {
            showRealTimeLocationBar(status);
        }

    }


    @Override
    public void onReceiveLocation(double latitude, double longitude, String userId) {
        LogUtil.e("userid = " + userId + ", lat: " + latitude + " long :" + longitude);
        EventBus.getDefault().post(RongEvent.RealTimeLocationReceiveEvent.obtain(userId, latitude, longitude));
    }

    @Override
    public void onParticipantsJoin(String userId) {
        EventBus.getDefault().post(RongEvent.RealTimeLocationJoinEvent.obtain(userId));

        if (RongIMClient.getInstance().getCurrentUserId().equals(userId)) {
            showRealTimeLocationBar(null);
        }
    }

    @Override
    public void onParticipantsQuit(String userId) {
        EventBus.getDefault().post(RongEvent.RealTimeLocationQuitEvent.obtain(userId));
    }

    @Override
    public void onError(RealTimeLocationConstant.RealTimeLocationErrorCode errorCode) {
        LogUtil.e("onError:---" + errorCode);
    }

    private void hideRealTimeBar() {
        if (mRealTimeBar != null) {
            mRealTimeBar.setVisibility(View.GONE);
        }
    }

    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
