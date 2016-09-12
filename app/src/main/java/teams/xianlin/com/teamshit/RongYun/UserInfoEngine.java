package teams.xianlin.com.teamshit.RongYun;

import android.content.Context;
import android.net.Uri;

import io.rong.imlib.model.UserInfo;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetUserInforResp;
import teams.xianlin.com.teamshit.Presenter.GetUserInfoPresenter;
import teams.xianlin.com.teamshit.PresenterView.GetUserInfoView;
import teams.xianlin.com.teamshit.Utils.LogUtil;

/**
 * 用户信息提供者的异步请求类
 * Created by Administrator on 2016/8/13.
 */
public class UserInfoEngine implements GetUserInfoView {
    private static UserInfoEngine instance;

    private Context context;

    private GetUserInfoPresenter getUserInfoPresenter;

    private UserInfoListener mUserInforListener;//接口回调
    private UserInfo mUserInfo;

    public static UserInfoEngine getInstance(Context context) {
        if (instance == null) {
            instance = new UserInfoEngine(context);
        }
        return instance;
    }

    private UserInfoEngine(Context context) {
        this.context = context;
        getUserInfoPresenter = new GetUserInfoPresenter(this);

    }

    public UserInfo startEngine(long userId) {
        if (getUserInfoPresenter != null) {
            getUserInfoPresenter.loadData(context, userId);
        }
        LogUtil.d("Main" + mUserInfo);
        return mUserInfo;
    }

    public void setListener(UserInfoListener listener) {
        this.mUserInforListener = listener;
    }

    @Override
    public void onLoadSucess(GetUserInforResp getUserInforResp) {//返回成功
        GetUserInforResp.UserInforBean userInforBean = getUserInforResp.getUserInfor();
        if (userInforBean != null) {
            mUserInfo = new UserInfo(String.valueOf(userInforBean.getUserId()), userInforBean.getNickname(), Uri.parse(userInforBean.getPortraitUri()));
            if (mUserInforListener != null) {
                mUserInforListener.onResult(mUserInfo);
            }
            LogUtil.d("函数" + userInforBean);
        }
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        if (mUserInforListener != null) {
            mUserInforListener.onResult(null);
        }
    }

    @Override
    public void showGetUserInforProgress() {

    }

    @Override
    public void hideGetUserInforProgress() {

    }

    public interface UserInfoListener {
        void onResult(UserInfo info);
    }
}
