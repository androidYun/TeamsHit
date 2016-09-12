package teams.xianlin.com.teamshit.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.amap.api.services.core.LatLonPoint;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.EventBus.OperatorFriendEvent;
import teams.xianlin.com.teamshit.NetWorkResp.OperateFriendResp;
import teams.xianlin.com.teamshit.Presenter.OperateFriendPresenter;
import teams.xianlin.com.teamshit.PresenterView.OperateFriendView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/8/10.
 */
public class AddFriendsActivity extends BaseActivity implements OperateFriendView {

    @Bind(R.id.navi_friend)
    TitleNavi naviFriend;
    @Bind(R.id.edit_reamark)
    EditText editReamark;
    OperateFriendPresenter operateFriendPresenter;
    @Bind(R.id.ckb_friend_circle)
    CheckBox ckbFriendCircle;

    private Long mUserId;//手机号

    private boolean isFriendPersision = false;//时候让别人看自己的朋友圈

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_friend_verifition);
        ButterKnife.bind(this);
        initialData();
        inflateView();

    }

    @Override
    protected void initialData() {
        mUserId = getIntent().getLongExtra(Constant.User_Id, 0);
        operateFriendPresenter = new OperateFriendPresenter(this);
        naviFriend.setClickCallBack(this);
    }

    @Override
    protected void inflateView() {
        naviFriend.setBackTitle("对对碰");
        naviFriend.setFinish("发送");
    }

    @Override
    public void onLoadSucess(OperateFriendResp operateFriendResp) {
        this.finish();
        EventBus.getDefault().post(new OperatorFriendEvent(1, mUserId));
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        ToastUtil.show(mActivity, errorMsg.getErrorMsg() + "");
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
    public void onRightClick() {
        super.onRightClick();
        String remark = editReamark.getText().toString();
        operateFriendPresenter.loadData(mUserId, editReamark.getText().toString(), 0, 1, mActivity);//applyId  这个只需要同意或者是拒绝的时候使用
    }

    @OnClick({R.id.ckb_friend_circle})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        LogUtil.d("WFESFV");
        switch (view.getId()) {
            case R.id.ckb_friend_circle:
                isFriendPersision = ckbFriendCircle.isChecked();
                break;

        }
    }
}
