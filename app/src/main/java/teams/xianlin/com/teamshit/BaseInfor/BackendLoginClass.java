package teams.xianlin.com.teamshit.BaseInfor;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.Interface.BackendLoginCallBack;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendsListResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetGroupListResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetUserDeatilInforResp;
import teams.xianlin.com.teamshit.NetWorkResp.LoginResp;
import teams.xianlin.com.teamshit.Presenter.GetFriendListPresenter;
import teams.xianlin.com.teamshit.Presenter.GetGroupListPresenter;
import teams.xianlin.com.teamshit.Presenter.GetUserDetailInforPresenter;
import teams.xianlin.com.teamshit.Presenter.LoginPresenter;
import teams.xianlin.com.teamshit.PresenterView.GetFriendListView;
import teams.xianlin.com.teamshit.PresenterView.GetGroupListView;
import teams.xianlin.com.teamshit.PresenterView.GetUserDetailInforView;
import teams.xianlin.com.teamshit.PresenterView.LoginView;
import teams.xianlin.com.teamshit.TeamsHitApplication;
import teams.xianlin.com.teamshit.Utils.SPUtils;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.db.DBManager;
import teams.xianlin.com.teamshit.db.Friend;
import teams.xianlin.com.teamshit.db.Groups;

/**
 * Created by Administrator on 2016/7/16.
 */
public class BackendLoginClass implements LoginView, GetFriendListView, GetGroupListView, GetUserDetailInforView {
    public static final int Account_Pwd_null = 1001;//密码和账号为空

    public Context mContext;

    private BackendLoginCallBack backendLoginCallBack;

    private LoginPresenter loginPresenter;

    private GetFriendListPresenter getFriendListPresenter;

    private GetGroupListPresenter getGroupListPresenter;

    private GetUserDetailInforPresenter getUserDetailInforPresenter;

    private String mOldAccount;

    private String mOldPwd;

    private String mNewAccount;

    private String mNewPwd;

    private String userToken;

    private String rongToken;

    public BackendLoginClass(Context context, BackendLoginCallBack backendLoginCallBack) {
        this.mContext = context;
        this.backendLoginCallBack = backendLoginCallBack;
        loginPresenter = new LoginPresenter(this);
        getFriendListPresenter = new GetFriendListPresenter(this);
        getGroupListPresenter = new GetGroupListPresenter(this);
        getUserDetailInforPresenter = new GetUserDetailInforPresenter(this);
        initialData();
    }


    public void startBackLogin() {
        if (isAccountAndPwd(mOldAccount, mOldPwd)) {
            loginAccount();
        }
    }

    public void startBackLogin(String mNewAccount, String mNewPwd) {
        if (!mNewAccount.equals(mOldAccount)) {
            //和上次登录账户不一致 或者 换设备登录  重新网络拉取好友 和 群组数据
            DBManager.getInstance(mContext).getDaoSession().getFriendDao().deleteAll();//清空上个用户的数据库
            DBManager.getInstance(mContext).getDaoSession().getGroupsDao().deleteAll();
            ToastUtil.show(mContext, "账户改变");
        }
        this.mOldAccount = mNewAccount;
        this.mOldPwd = mNewPwd;
        if (isAccountAndPwd(mOldAccount, mOldPwd)) {
            loginAccount();
        }

    }

    public void initialData() {
        mOldAccount = (String) SPUtils.getInstance().get(mContext, Constant.User_Account, "");
        mOldPwd = (String) SPUtils.getInstance().get(mContext, Constant.User_Pwd, "");
    }

    public boolean isAccountAndPwd(String mAccount, String mPwd) {
        if (StringUtils.isBlank(mAccount) || StringUtils.isBlank(mPwd)) {
            ErrorMsg errorMsg = new ErrorMsg();
            errorMsg.setErrorMsg("账号密码不能为空");
            errorMsg.setErrorCode(Account_Pwd_null);
            errorMsg.setRespCode(HttpDefine.Login_Resp);
            backendLoginCallBack.BackendLoginFail(errorMsg);
            return false;
        }
        return true;
    }

    public void loginAccount() {
        loginPresenter.loadData(mOldAccount, mOldPwd);
    }


    @Override
    public void onLoadSucess(LoginResp loginResp) {
        rongToken = loginResp.getRongToken();
        userToken = loginResp.getUserToken();
        TeamHitContext.getInstance().setmUserToken(userToken);
        TeamHitContext.getInstance().setmRongToken(rongToken);
        TeamHitContext.getInstance().setUserId(loginResp.getUserId());
        SPUtils.put(mContext, Constant.User_Pwd, mOldPwd);
        SPUtils.put(mContext, Constant.User_Account, mOldAccount);
        SPUtils.put(mContext, Constant.User_Token, userToken);
        SPUtils.put(mContext, Constant.Rong_Token, rongToken);
        SPUtils.put(mContext, Constant.User_Id, loginResp.getUserId());
        connect(rongToken);
        if (!loginResp.getCompleteInfor()) {
            ErrorMsg errorMsg = new ErrorMsg();
            errorMsg.setErrorCode(Constant.Not_Complete_Infor_Code);
            errorMsg.setErrorMsg("去完善资料");
            errorMsg.setRespCode(loginResp.getCommand());
            backendLoginCallBack.BackendLoginFail(errorMsg);
            return;
        }
        getUserDetailInforPresenter.loadData(mContext);
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {//登录失败
        if (errorMsg.getErrorCode() == HttpDefine.Get_Detail_User_Infor_Resp) {//获取个人详细信息失败
            getFriendListPresenter.loadData(mContext);
            return;
        }
        backendLoginCallBack.BackendLoginFail(errorMsg);
    }

    @Override
    public void showLoginProgress() {

    }

    @Override
    public void hideLoginProgress() {

    }

    @Override
    public void onLoadSucess(GetUserDeatilInforResp getUserDeatilInforResp) {
        GetUserDeatilInforResp.DetailUserInforBean detailInfor = getUserDeatilInforResp.getDetailInfor();
        if (detailInfor != null) {
            UserBean userBean = new UserBean();
            String portraitUri = detailInfor.getPortraitUri();
            long userId = detailInfor.getUserId();
            String nackName = detailInfor.getNickName();
            userBean.setPortraitUri(portraitUri);
            userBean.setDisplayName(nackName);
            userBean.setUserId(userId);
            TeamHitContext.getInstance().setUserBean(userBean);
        }
        getFriendListPresenter.loadData(mContext);
    }

    @Override
    public void showGetUserInforProgress() {

    }

    @Override
    public void hideGetUserInforProgress() {

    }

    @Override
    public void onLoadSucess(GetFriendsListResp loginResp) {
        List<GetFriendsListResp.Friend> friendList = loginResp.getFriendList();
        if (friendList != null && friendList.size() > 0) {
            for (GetFriendsListResp.Friend friend : friendList) {
                if (friend.getStatus() == 1 || friend.getStatus() == 2 || friend.getStatus() == 3) {
                    DBManager.getInstance(mContext).getDaoSession().getFriendDao().insertOrReplace(new Friend(
                            String.valueOf(friend.getTargetId()),
                            friend.getNickName(),
                            friend.getPortraitUri(),
                            friend.getDisplayName(),
                            null,
                            null
                    ));
                }
            }
        }
        getGroupListPresenter.loadData(userToken, mContext);
    }

    @Override
    public void showGetFriendsProgress() {

    }

    @Override
    public void hideGetFriendsProgress() {

    }

    @Override
    public void onLoadSucess(GetGroupListResp getGroupListResp) {
        List<GetGroupListResp.Group> groupList = getGroupListResp.getGroupList();
        if (groupList != null && groupList.size() > 0) {
            DBManager.getInstance(mContext).getDaoSession().getGroupsDao().deleteAll();
            for (GetGroupListResp.Group group : groupList) {
                DBManager.getInstance(mContext).getDaoSession().getGroupsDao().insertOrReplace(
                        new Groups(group.getGroupId(), group.getGroupName(), group.getPortraitUri(), group.getRole(), group.getGroupType(), group.getCreatorTime(), group.getIntroduce(), group.getNumber()));
            }
        }
        backendLoginCallBack.BackendLoginsuccess();
    }

    @Override
    public void showGeGroupssProgress() {

    }

    @Override
    public void hideGetGroupsProgress() {

    }

    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token) {

        if (mContext.getApplicationInfo().packageName.equals(TeamsHitApplication.getCurProcessName(mContext))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.d("Main", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    Log.d("Main", "--onSuccess" + userid);

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("Main", "--onError" + errorCode);
                }
            });
        }
    }
}
