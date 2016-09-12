package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.java_websocket.drafts.Draft_17;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.widget.InputView;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpSocketDefine;
import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.EventBus.SocketMessageEvent;
import teams.xianlin.com.teamshit.NetWorkRequest.SocketRequest.BoastPrepareRequest;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.DensityUtil;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.SysUtils;
import teams.xianlin.com.teamshit.Utils.WebSocketUtils;
import teams.xianlin.com.teamshit.db.DBManager;
import teams.xianlin.com.teamshit.db.Friend;
import teams.xianlin.com.teamshit.db.Groups;
import teams.xianlin.com.teamshit.widget.DialogUtils.SelectorDiceNumberDialog;
import teams.xianlin.com.teamshit.widget.Game.BoastPrepareView;
import teams.xianlin.com.teamshit.widget.Game.BoastStartView;
import teams.xianlin.com.teamshit.widget.Game.GameTitleNavi;

/**
 * Created by Administrator on 2016/9/6.
 */
public class ChatRoomActivity extends BaseActivity implements View.OnTouchListener {
    private final static int INPUT_WIGTH = 100;
    private GameTitleNavi gameTitleNavi;

    private ImageView imgStretch;

    private FrameLayout rongContent;
    /**
     * 主界面Id
     */
    private FrameLayout mainLayout;
    /**
     * 准备界面的Id
     */


    /**
     * 开始界面
     */

    /**
     * 对话框
     */
    private SelectorDiceNumberDialog selectorDiceNumberDialog;//选择点数的对话框

    private ConversationFragment fragment;//群聊回话界面

    private String mTargetId;//对方Id

    private Conversation.ConversationType mConversationType;//回话类型

    private LayoutInflater layoutInflater;//布局加载器

    private Groups mGroups;//群消息

    private WebSocketUtils webSocketUtils;//Socket管理类

    private BoastPrepareView boastPrepareView;//吹牛准备界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_group_room);
        initialView();
        initialData();
        inflateView();
        inifalteData();
        enterFragment(mConversationType, mTargetId);
    }


    @Override
    public void initialView() {
        layoutInflater = getLayoutInflater();
        selectorDiceNumberDialog = new SelectorDiceNumberDialog(mActivity);
        webSocketUtils = new WebSocketUtils(Constant.socketUri, new Draft_17());
        mainLayout = (FrameLayout) findViewById(R.id.main_layout);
        gameTitleNavi = (GameTitleNavi) findViewById(R.id.navi_game_top);
        imgStretch = (ImageView) findViewById(R.id.img_stretch);
        rongContent = (FrameLayout) findViewById(R.id.rong_content);
        View rollDiceView = getRollDiceView();//获取准备View
        mainLayout.addView(rollDiceView);
        // imgStretch.setOnTouchListener(this);
        imgStretch.setOnClickListener(this);
    }

    @Override
    protected void initialData() {
        Intent mIntent = getIntent();
        mConversationType = Conversation.ConversationType.GROUP;
        Bundle bundle = mIntent.getExtras();
        mGroups = (Groups) bundle.getSerializable(Constant.GroupBean);
        mTargetId = mGroups.getGroupId();//群ID
    }

    @Override
    protected void inflateView() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rongContent
                .getLayoutParams();
        int layoutHeight = DensityUtil.dip2px(mActivity, INPUT_WIGTH);
        params.height = layoutHeight;
        rongContent.setLayoutParams(params);
    }

    private void inifalteData() {
        gameTitleNavi.setGroupData(mGroups);
    }

    public View getPrePareView() {
        List<UserBean> userBeanList = new ArrayList<>();
        userBeanList.add(getUserBean());
        userBeanList.add(getUserBean());
        userBeanList.add(getUserBean());
        userBeanList.add(getUserBean());
        userBeanList.add(null);
        userBeanList.add(null);
        boastPrepareView = new BoastPrepareView(mActivity, null, userBeanList);
        return boastPrepareView;
    }

    public View getRollDiceView() {
        View rollDiceView = new BoastStartView(mActivity);
        return rollDiceView;
    }

    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {
        /**
         * 来刷新好友信息
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

    @Override
    public void onClick(View view) {
        super.onClick(view);
//        selectorDiceNumberDialog.show();
//        int mScreenHeight = SysUtils.getInatcne().getWindowHeight(mActivity);
//        int mMaxHeight = mScreenHeight / 2;
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rongContent
//                .getLayoutParams();
//        int layoutHeight = DensityUtil.dip2px(mActivity, mMaxHeight);
//        params.height = layoutHeight;
//        rongContent.setLayoutParams(params);
        LogUtil.d("我知道了啊");
        final boolean[] blocking = new boolean[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WebSocketUtils c = new WebSocketUtils(URI.create("ws://192.168.1.111:8181/"), new Draft_17());
                    blocking[0] = c.connectBlocking();
                    LogUtil.d("设置链接" + blocking[0]);
                    BoastPrepareRequest socketRequest = new BoastPrepareRequest();
                    //   c.send("测试--handshake");
                    // packetResp.setMessage(packetResp);
                    c.sendMessage(socketRequest);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    LogUtil.d("设置链接" + blocking[0] + "   " + e.toString() + "   " + e.getMessage());

                }
            }
        }).start();
        LogUtil.d("设置链接" + blocking[0]);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() != imgStretch.getId()) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                moveViewWithFinger(view, motionEvent.getRawX(), motionEvent.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }


    /**
     * 设置View的布局属性，使得view随着手指移动 注意：view所在的布局必须使用RelativeLayout 而且不得设置居中等样式
     *
     * @param view
     * @param rawX
     * @param rawY
     */
    private void moveViewWithFinger(View view, float rawX, float rawY) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rongContent
                .getLayoutParams();
        int mScreenHeight = SysUtils.getInatcne().getWindowHeight(mActivity);
        int mMaxHeight = mScreenHeight / 2;
        int layoutHeight = (int) (mScreenHeight - rawY);
        int px = DensityUtil.dip2px(mActivity, INPUT_WIGTH);
        if (layoutHeight <= px) {
            layoutHeight = px;
        } else if (layoutHeight > mMaxHeight) {
            layoutHeight = mMaxHeight;
        }
        params.height = layoutHeight;
        rongContent.setLayoutParams(params);
    }

    public void onEventMainThread(SocketMessageEvent event) {
            boastPrepareView.loadData(event.getMessage());
    }
}
