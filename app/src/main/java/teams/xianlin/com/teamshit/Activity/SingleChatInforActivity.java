package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/8/17.
 */
public class SingleChatInforActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.navi_top)
    TitleNavi naviTop;
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.img_add)
    ImageView imgAdd;
    @Bind(R.id.ckb_top_new)
    CheckBox ckbTopNew;
    @Bind(R.id.ckb_new_not_disturb)
    CheckBox ckbNewNotDisturb;
    @Bind(R.id.layout_chat_record)
    RelativeLayout layoutChatRecord;
    @Bind(R.id.layout_clear_chat_history)
    RelativeLayout layoutClearChatHistory;

    private String mUserId;//自己UserID

    private String mTargetId;//好友的UserID

    private boolean mIsTop;//消息时候置顶

    private Conversation.ConversationNotificationStatus mConversationNotificationStatus;//消息免打扰状态


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_infor);
        ButterKnife.bind(this);
        initialData();
        inflateView();
    }

    @Override
    protected void initialData() {
        Intent intent = getIntent();
        mTargetId = intent.getStringExtra(Constant.Target_Id);
        if (RongIM.getInstance() != null && RongIM.getInstance().getRongIMClient() != null || RongIM.getInstance().getRongIMClient().getConversation(Conversation.ConversationType.PRIVATE, mTargetId) != null) {
            mIsTop = RongIM.getInstance().getRongIMClient().getConversation(Conversation.ConversationType.PRIVATE, mTargetId).isTop();
            RongIM.getInstance().getRongIMClient().getConversationNotificationStatus(Conversation.ConversationType.PRIVATE, mTargetId, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                @Override
                public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                    if (conversationNotificationStatus.equals(Conversation.ConversationNotificationStatus.DO_NOT_DISTURB)) {//不打扰
                        ckbNewNotDisturb.setChecked(false);
                        mConversationNotificationStatus = Conversation.ConversationNotificationStatus.NOTIFY;
                    } else if (conversationNotificationStatus.equals(Conversation.ConversationNotificationStatus.NOTIFY)) {//打扰
                        ckbNewNotDisturb.setChecked(true);
                        mConversationNotificationStatus = Conversation.ConversationNotificationStatus.DO_NOT_DISTURB;
                    }
                    LogUtil.d("我知道啊" + mConversationNotificationStatus + "     " + conversationNotificationStatus);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    ckbNewNotDisturb.setChecked(false);
                    mConversationNotificationStatus = Conversation.ConversationNotificationStatus.DO_NOT_DISTURB;
                }
            });

        }

    }

    @Override
    protected void inflateView() {
        naviTop.setBackTitle("聊天信息");
        naviTop.setClickCallBack(this);
        ckbTopNew.setChecked(mIsTop);
    }

    @OnClick({R.id.layout_chat_record, R.id.layout_clear_chat_history, R.id.ckb_new_not_disturb, R.id.ckb_top_new})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.layout_chat_record:

                break;

            case R.id.layout_clear_chat_history:
                if (RongIM.getInstance() != null && RongIM.getInstance().getRongIMClient() != null) {
                    RongIM.getInstance().getRongIMClient().clearMessages(Conversation.ConversationType.PRIVATE, mTargetId, new RongIMClient.ResultCallback<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            ToastUtil.show(mActivity, "删除成功");
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            ToastUtil.show(mActivity, "删除失败");
                        }

                    });
                }
                break;
            case R.id.ckb_new_not_disturb://消息免打扰设置
                LogUtil.d("我知dd道啊" + mConversationNotificationStatus);
                RongIM.getInstance().getRongIMClient().setConversationNotificationStatus(Conversation.ConversationType.PRIVATE, mTargetId, mConversationNotificationStatus, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                    @Override
                    public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                        LogUtil.d("消息免打扰成功");
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        LogUtil.d("消息免打扰失败");
                    }
                });
                break;
        }
    }

    @OnCheckedChanged({R.id.ckb_new_not_disturb, R.id.ckb_top_new})
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {

        switch (compoundButton.getId()) {
            case R.id.ckb_top_new:
                if (RongIM.getInstance() != null && RongIM.getInstance().getRongIMClient() != null) {
                    RongIM.getInstance().getRongIMClient().setConversationToTop(Conversation.ConversationType.PRIVATE, mTargetId, !mIsTop, new RongIMClient.ResultCallback<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            LogUtil.d("成功");
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            LogUtil.d("失败");
                        }
                    });
                }
                break;
        }
    }
}
