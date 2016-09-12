package teams.xianlin.com.teamshit.RongYun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ContactNotificationMessage;
import io.rong.message.GroupNotificationMessage;
import io.rong.message.ImageMessage;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;
import teams.xianlin.com.teamshit.Activity.AMAPLocationActivity;
import teams.xianlin.com.teamshit.Activity.FriendDetailActivity;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BroadCastService.BroadcastManager;
import teams.xianlin.com.teamshit.EventBus.FriendCircleMessageEvent;
import teams.xianlin.com.teamshit.EventBus.OperatorFriendEvent;
import teams.xianlin.com.teamshit.RongYun.Provider.TeamHitPassNoteProvider;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.RongGenerate;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.db.DBManager;
import teams.xianlin.com.teamshit.db.Friend;
import teams.xianlin.com.teamshit.db.MessageUser;
import teams.xianlin.com.teamshit.db.NewFriend;

/**
 * 融云相关监听 事件集合类
 * Created by AMing on 16/1/7.
 * Company RongCloud
 */

public class SealAppContext implements RongIM.UserInfoProvider, RongIMClient.OnReceiveMessageListener, RongIM.ConversationListBehaviorListener, RongIM.GroupInfoProvider, RongIM.GroupUserInfoProvider, RongIM.LocationProvider, RongIM.ConversationBehaviorListener, RongIMClient.ConnectionStatusListener {
    public static final String Updater_Friend = "Updater_Friend";//更新新朋友
    public static final String Updater_Eddot = "Updater_Eddot";
    public static String Net_Update_Group = "Net_Update_Group";//更新群组
    private Context mContext;

    private static SealAppContext mRongCloudInstance;

    private RongIM.LocationProvider.LocationCallback mLastLocationCallback;

    private Stack<Map<String, Activity>> mActivityStack;

    public SealAppContext(Context mContext) {
        this.mContext = mContext;
        initListener();
        mActivityStack = new Stack<>();
    }

    /**
     * 初始化 RongCloud.
     *
     * @param context 上下文。
     */
    public static void init(Context context) {
        if (mRongCloudInstance == null) {
            synchronized (SealAppContext.class) {
                if (mRongCloudInstance == null) {
                    mRongCloudInstance = new SealAppContext(context);
                }
            }
        }
    }

    public boolean pushActivity(Conversation.ConversationType conversationType, String targetId, Activity activity) {
        if (conversationType == null || targetId == null || activity == null)
            return false;
        String key = conversationType.getName() + targetId;
        Map<String, Activity> map = new HashMap<>();
        map.put(key, activity);
        mActivityStack.push(map);
        return true;
    }

    public boolean popActivity(Conversation.ConversationType conversationType, String targetId) {
        if (conversationType == null || targetId == null)
            return false;
        String key = conversationType.getName() + targetId;
        Map<String, Activity> map = mActivityStack.peek();
        if (map.containsKey(key)) {
            mActivityStack.pop();
            return true;
        }
        return false;
    }

    public boolean containsInQue(Conversation.ConversationType conversationType, String targetId) {
        if (conversationType == null || targetId == null)
            return false;
        String key = conversationType.getName() + targetId;
        Map<String, Activity> map = mActivityStack.peek();
        return map.containsKey(key);
    }

    /**
     * 获取RongCloud 实例。
     *
     * @return RongCloud。
     */
    public static SealAppContext getInstance() {
        return mRongCloudInstance;
    }

    /**
     * init 后就能设置的监听
     */
    private void initListener() {
        RongIM.setConversationBehaviorListener(this);//设置会话界面操作的监听器。
        RongIM.setConversationListBehaviorListener(this);
        RongIM.setUserInfoProvider(this, true);
        RongIM.setGroupInfoProvider(this, true);
        RongIM.setLocationProvider(this);//设置地理位置提供者,不用位置的同学可以注掉此行代码
        setInputProvider();
        setUserInfoEngineListener();
        RongIM.setGroupUserInfoProvider(this, true);
    }

    /**
     * 需要 rongcloud connect 成功后设置的 listener
     */
    public void setUserInfoEngineListener() {
        UserInfoEngine.getInstance(mContext).setListener(new UserInfoEngine.UserInfoListener() {
            @Override
            public void onResult(UserInfo info) {
                if (info != null && RongIM.getInstance() != null) {
                    if (TextUtils.isEmpty(String.valueOf(info.getPortraitUri()))) {
                        info.setPortraitUri(Uri.parse(RongGenerate.generateDefaultAvatar(info.getName(), info.getUserId())));
                    }
                    RongIM.getInstance().refreshUserInfoCache(info);
                }
            }
        });
    }

    /**
     * 共享地理位置
     */
    private void setInputProvider() {
//
        RongIM.getInstance().getRongIMClient().setOnReceiveMessageListener(this);
        RongIM.setConnectionStatusListener(this);
//        InputProvider.ExtendProvider[] singleProvider = {
//                new ImageInputProvider(RongContext.getInstance()),
//                new RealTimeLocationInputProvider(RongContext.getInstance()) //带位置共享的地理位置
//        };

        InputProvider.ExtendProvider[] muiltiProvider = {
                new ImageInputProvider(RongContext.getInstance()),
                new LocationInputProvider(RongContext.getInstance()),//地理位置
                new TeamHitPassNoteProvider(RongContext.getInstance())
        };
        // RongIM.resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, singleProvider);
        RongIM.resetInputExtensionProvider(Conversation.ConversationType.DISCUSSION, muiltiProvider);
        RongIM.resetInputExtensionProvider(Conversation.ConversationType.CUSTOMER_SERVICE, muiltiProvider);
        RongIM.resetInputExtensionProvider(Conversation.ConversationType.GROUP, muiltiProvider);
        RongIM.resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, muiltiProvider);
    }

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {

    }

    @Override
    public void onStartLocation(Context context, LocationCallback locationCallback) {
        /**
         * demo 代码  开发者需替换成自己的代码。
         */
        SealAppContext.getInstance().setLastLocationCallback(locationCallback);
        Intent intent = new Intent(context, AMAPLocationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        Intent friendDetailIntent = new Intent(context, FriendDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.Friend_Detail_Type, 2);//传递2  是一个UserId 如果是1  则是传递的一个详情类
        bundle.putString(Constant.Target_Id, userInfo.getUserId());
        friendDetailIntent.putExtras(bundle);
        context.startActivity(friendDetailIntent);
        return true;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }

    @Override
    public boolean onReceived(Message message, int i) {
        LogUtil.d("我知道了啊啊");
        MessageContent messageContent = message.getContent();
        if (messageContent instanceof ContactNotificationMessage) {//联系链接通知
            ContactNotificationMessage contactNotificationMessage = (ContactNotificationMessage) messageContent;
            if (contactNotificationMessage.getOperation().equals(Constant.CONTACT_OPERATION_REQUEST)) {//链接操作请求
                UserInfo userInfo = contactNotificationMessage.getUserInfo();
                if (userInfo != null) {
                    NewFriend newFriend = new NewFriend(userInfo.getUserId(), String.valueOf(userInfo.getPortraitUri()), userInfo.getName());
                    DBManager.getInstance(mContext).getDaoSession().getNewFriendDao().insertOrReplace(newFriend);
                }
                //对方发来好友邀请
                BroadcastManager.getInstance(mContext).sendBroadcast(SealAppContext.Updater_Eddot, contactNotificationMessage.getSourceUserId());
            } else if (contactNotificationMessage.getOperation().equals(Constant.CONTACT_OPERATION_ACCEPT_RESPONSE)) {//对方同意我的好友详情
                //对方同意我的好友请求
                UserInfo userInfo = messageContent.getUserInfo();
                if (userInfo != null) {
                    DBManager.getInstance(mContext).getDaoSession().getFriendDao().insertOrReplace(new Friend(userInfo.getUserId(), userInfo.getName(), String.valueOf(userInfo.getPortraitUri()), userInfo.getName(), null, null));
                    String mTargetId = contactNotificationMessage.getSourceUserId();
                }
                BroadcastManager.getInstance(mContext).sendBroadcast(Updater_Friend);//更新好友
                BroadcastManager.getInstance(mContext).sendBroadcast(SealAppContext.Updater_Eddot);//更新好友添加标识
            } else if (contactNotificationMessage.getOperation().equals(Constant.Delete_OperaTion_Respose)) {//删除好友操作
                String mTargetId = contactNotificationMessage.getSourceUserId();
                if (!StringUtils.isBlank(mTargetId)) {
                    DBManager.getInstance(mContext).getDaoSession().getFriendDao().deleteByKey(mTargetId);
                    RongIM.getInstance().getRongIMClient().removeConversation(Conversation.ConversationType.PRIVATE, mTargetId);
                    EventBus.getDefault().post(new OperatorFriendEvent(2, Long.valueOf(mTargetId)));//更新好友列表
                }
            } else if (contactNotificationMessage.getOperation().equals(Constant.Friend_Circle_Message_OperaTion_Respose)) {
                UserInfo userInfo = contactNotificationMessage.getUserInfo();
                if (userInfo != null) {
                    MessageUser messageUser = new MessageUser(userInfo.getUserId(), String.valueOf(userInfo.getPortraitUri()), userInfo.getName());
                    DBManager.getInstance(mContext).getDaoSession().getMessageUserDao().insert(messageUser);
                    EventBus.getDefault().post(new FriendCircleMessageEvent());
                }
            }
//                // 发广播通知更新好友列表
//            BroadcastManager.getInstance(mContext).sendBroadcast(UPDATEREDDOT);
//            }
        } else if (messageContent instanceof GroupNotificationMessage) {//是否属于群组通知
            GroupNotificationMessage groupNotificationMessage = (GroupNotificationMessage) messageContent;
            LogUtil.e("Seal" + groupNotificationMessage.getMessage());
            if (groupNotificationMessage.getOperation().equals(GroupNotificationMessage.GROUP_OPERATION_KICKED)) {
            } else if (groupNotificationMessage.getOperation().equals(GroupNotificationMessage.GROUP_OPERATION_ADD)) {
            } else if (groupNotificationMessage.getOperation().equals(GroupNotificationMessage.GROUP_OPERATION_QUIT)) {
            } else if (groupNotificationMessage.getOperation().equals(GroupNotificationMessage.GROUP_OPERATION_RENAME)) {
            }
            BroadcastManager.getInstance(mContext).sendBroadcast(SealAppContext.Net_Update_Group);//更新群组
        } else if (messageContent instanceof ImageMessage) {//是否输入图片信息通知
            ImageMessage imageMessage = (ImageMessage) messageContent;
            Log.e("imageMessage", imageMessage.getRemoteUri().toString());
        }
        return true;
    }


    @Override
    public Group getGroupInfo(String s) {
        return null;
    }

    @Override
    public GroupUserInfo getGroupUserInfo(String s, String s1) {
        return null;
    }

    @Override
    public UserInfo getUserInfo(String s) {
        Friend friend = DBManager.getInstance(mContext).getDaoSession().getFriendDao().load(s);
        if (friend != null) {
            UserInfo userInfo = new UserInfo(friend.getUserId(), friend.getName(), Uri.parse(friend.getPortraitUri()));
            return userInfo;
        } else {
            return UserInfoEngine.getInstance(mContext).startEngine(Long.valueOf(s));
        }
    }

    public LocationCallback getLastLocationCallback() {
        return mLastLocationCallback;
    }

    public void setLastLocationCallback(LocationCallback lastLocationCallback) {
        this.mLastLocationCallback = lastLocationCallback;
    }
}
