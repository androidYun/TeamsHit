package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.EventBus.EditDisplayNameEvent;
import teams.xianlin.com.teamshit.EventBus.OperatorFriendEvent;
import teams.xianlin.com.teamshit.Interface.EditNameCallBack;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCirclePermissionResp;
import teams.xianlin.com.teamshit.NetWorkResp.OperateFriendResp;
import teams.xianlin.com.teamshit.NetWorkResp.SetDisPlayNameResp;
import teams.xianlin.com.teamshit.NetWorkResp.SetTargetPermissionResp;
import teams.xianlin.com.teamshit.NetWorkResp.SetUserPermissionResp;
import teams.xianlin.com.teamshit.Presenter.GetFriendCirclePermissionPresenter;
import teams.xianlin.com.teamshit.Presenter.OperateFriendPresenter;
import teams.xianlin.com.teamshit.Presenter.SetDisPlayNamePresenter;
import teams.xianlin.com.teamshit.Presenter.SetTargetPermissionPresenter;
import teams.xianlin.com.teamshit.Presenter.SetUserPermissionPresenter;
import teams.xianlin.com.teamshit.PresenterView.GetFriendCirclePermissionView;
import teams.xianlin.com.teamshit.PresenterView.OperateFriendView;
import teams.xianlin.com.teamshit.PresenterView.SetDisPlayNameView;
import teams.xianlin.com.teamshit.PresenterView.SetTargetPermissionView;
import teams.xianlin.com.teamshit.PresenterView.SetUserPermissionView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.db.DBManager;
import teams.xianlin.com.teamshit.db.Friend;
import teams.xianlin.com.teamshit.widget.DialogUtils.EditNameDialog;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/8/18.
 */
public class SetFriendDataActivity extends BaseActivity implements OperateFriendView, GetFriendCirclePermissionView, SetDisPlayNameView, SetUserPermissionView, SetTargetPermissionView, EditNameCallBack {
    @Bind(R.id.navi_top)
    TitleNavi naviTop;
    @Bind(R.id.layout_set_display_name)
    RelativeLayout layoutSetDisplayName;
    @Bind(R.id.ckb_not_his_look_friend_circle)
    CheckBox ckbNotHisLookFriendCircle;
    @Bind(R.id.layout_not_his_look_friend_circle)
    RelativeLayout layoutNotHisLookFriendCircle;
    @Bind(R.id.ckb_not_look_friend_circle)
    CheckBox ckbNotLookFriendCircle;
    @Bind(R.id.layout_not_look_friend_circle)
    RelativeLayout layoutNotLookFriendCircle;
    @Bind(R.id.ckb_join_black)
    CheckBox ckbJoinBlack;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.txt_display_name)
    TextView txtDisplayName;

    private OperateFriendPresenter operateFriendPresenter;//操作好友

    private GetFriendCirclePermissionPresenter getFriendCirclePermissionPresenter;//修改朋友圈权限

    private SetDisPlayNamePresenter setDisPlayNamePresenter;//修改备注

    private SetTargetPermissionPresenter setTargetPermissionPresenter;//设置朋友访问我的朋友圈

    private SetUserPermissionPresenter setUserPermissionPresenter;//修改我看不看朋友全的权限

    private EditNameDialog editNameDialog;//编辑备注名称

    private Long mTargetId;//对方id

    private String mDisPlayName;//显示名称

    private int mTargetPermission = 1;//1、让他看我的朋友圈2、不让他看我的朋友

    private int mUserPermission = 1;//1、看他的朋友圈2、不看他的朋友圈

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_detail_layout);
        ButterKnife.bind(this);
        initialData();
        inflateView();
    }

    @Override
    protected void initialData() {

        operateFriendPresenter = new OperateFriendPresenter(this);
        getFriendCirclePermissionPresenter = new GetFriendCirclePermissionPresenter(this);
        setDisPlayNamePresenter = new SetDisPlayNamePresenter(this);
        setTargetPermissionPresenter = new SetTargetPermissionPresenter(this);
        setUserPermissionPresenter = new SetUserPermissionPresenter(this);
        editNameDialog = new EditNameDialog(mActivity, this);
        Intent intent = getIntent();
        mTargetId = intent.getLongExtra(Constant.Target_Id, 0);
        mDisPlayName = intent.getStringExtra(Constant.DisPlay_Name);
        getFriendCirclePermissionPresenter.loadData(mActivity, mTargetId);
        naviTop.setClickCallBack(this);
    }

    @Override
    protected void inflateView() {
        naviTop.setBackTitle("详细资料");
        naviTop.setTitle("资料设置");
        editNameDialog.setTitle("修改备注名称");
        txtDisplayName.setText("" + mDisPlayName);
    }

    @OnClick({R.id.btn_confirm, R.id.ckb_not_his_look_friend_circle, R.id.ckb_not_look_friend_circle, R.id.layout_set_display_name})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_confirm:
                operateFriendPresenter.loadData(mTargetId, "删除好友", 0, 5, mActivity);
                break;
            case R.id.ckb_not_his_look_friend_circle:
                setTargetPermissionPresenter.loadData(mActivity, mTargetId, 2 / mTargetPermission);//2/2=1  2/1=2
                break;
            case R.id.ckb_not_look_friend_circle:
                setUserPermissionPresenter.loadData(mActivity, mTargetId, 2 / mUserPermission);//设置权限
                break;
            case R.id.layout_set_display_name:
                editNameDialog.show();
                break;
        }
    }

    @Override
    public void onLoadSucess(OperateFriendResp operateFriendResp) {
        DBManager.getInstance(mActivity).getDaoSession().getFriendDao().deleteByKey(String.valueOf(mTargetId));
        EventBus.getDefault().post(new OperatorFriendEvent(2, mTargetId));
        RongIM.getInstance().getRongIMClient().removeConversation(Conversation.ConversationType.PRIVATE, String.valueOf(mTargetId));
        this.finish();
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        ToastUtil.show(mActivity, "删除失败");
    }

    @Override
    public void showOperateFriendProgress() {
        loadDialog.show();
    }

    @Override
    public void hideOperateFriendProgress() {
        loadDialog.hide();
    }

    @Override
    public void onLoadSucess(GetFriendCirclePermissionResp getFriendCirclePermissionResp) {
        if (getFriendCirclePermissionResp != null) {
            mTargetPermission = getFriendCirclePermissionResp.getTargetPermission();
            mUserPermission = getFriendCirclePermissionResp.getUserPermission();
            if (getFriendCirclePermissionResp.getTargetPermission() == 1) {//1、让他看我的朋友圈2、不让他看我的朋友
                ckbNotHisLookFriendCircle.setChecked(false);
            } else if (getFriendCirclePermissionResp.getTargetPermission() == 2) {
                ckbNotHisLookFriendCircle.setChecked(true);
            }
            if (getFriendCirclePermissionResp.getUserPermission() == 1) {
                ckbNotLookFriendCircle.setChecked(false);
            } else if (getFriendCirclePermissionResp.getUserPermission() == 2) {
                ckbNotLookFriendCircle.setChecked(true);
            }
        }
    }

    @Override
    public void showFriendCirclePermissionProgress() {
        loadDialog.show();
    }

    @Override
    public void hideFriendCirclePermissionProgress() {
        loadDialog.hide();
    }

    @Override
    public void onLoadSucess(SetDisPlayNameResp setDiaPlayNameResp) {
        txtDisplayName.setText("" + mDisPlayName);
        Friend friend = DBManager.getInstance(mActivity).getDaoSession().getFriendDao().load(String.valueOf(mTargetId));
        friend.setDisplayName("" + mDisPlayName);
        DBManager.getInstance(mActivity).getDaoSession().getFriendDao().insertOrReplace(friend);
        EventBus.getDefault().post(new EditDisplayNameEvent(mDisPlayName));
        UserInfo userInfo = new UserInfo(friend.getUserId(), friend.getDisplayName(), Uri.parse(friend.getPortraitUri()));
        RongIM.getInstance().refreshUserInfoCache(userInfo);
    }

    @Override
    public void showSetDiaPlayNameProgress() {
        loadDialog.show();
    }

    @Override
    public void hideSetDiaPlayNameProgress() {
        loadDialog.hide();
    }

    @Override
    public void onEditTeamNameSuccess(String result) {
        mDisPlayName = result;
        setDisPlayNamePresenter.loadData(mActivity, mTargetId, result);

    }

    @Override
    public void onLoadSucess(SetTargetPermissionResp setTargetPermissionResp) {
        mTargetPermission = 2 / mTargetPermission;
    }

    @Override
    public void showSetTargetPermissionProgress() {
        loadDialog.show();
    }

    @Override
    public void hideSetTargetPermissionProgress() {
        loadDialog.hide();
    }

    @Override
    public void onLoadSucess(SetUserPermissionResp setUserPermissionResp) {
        mUserPermission = 2 / mUserPermission;//2/1=2    2/2==1
    }

    @Override
    public void showSetUserPermissionProgress() {
        loadDialog.show();
    }

    @Override
    public void hideSetUserPermissionProgress() {
        loadDialog.hide();
    }
}
