package teams.xianlin.com.teamshit.Presenter;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.LoginResp;
import teams.xianlin.com.teamshit.PrensenterModel.LoginModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.LoginView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;

/**
 * Created by Administrator on 2016/7/6.
 */
public class LoginPresenter extends BasePresenter implements IBasePsenter<LoginResp> {
    private LoginView loginView;

    private LoginModel loginModel;

    private int Command;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModel(this);
    }

    public void loadData(String Account, String Password) {
        this.Command = HttpDefine.Login_Resp;
        if (StringUtils.isBlank(Account)) {
            onRespFail("用户名不能为空", Command, Constant.Param_error_Code);
            return;
        }
        if (StringUtils.isBlank(Password)) {
            onRespFail("密码不能为空", Command, Constant.Param_error_Code);
            return;
        }
        loginView.showLoginProgress();
        loginModel.postAysData(Account, Password,  Command);


    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorcode) {
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setRespCode(RespCode);
        loginView.onLoadFail(errorMsg);
        loginView.hideLoginProgress();

    }

    @Override
    public void onRespSucess(LoginResp loginResp) {
        loginView.onLoadSucess(loginResp);
        loginView.hideLoginProgress();
    }
}
