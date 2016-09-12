package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.LoginResp;
import teams.xianlin.com.teamshit.Presenter.LoginPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/7/6.
 */
public class LoginModel {
    private LoginPresenter loginPresenter;

    public LoginModel(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    public void postAysData(String Account, String pwd, final int Command) {
        Param accountParam = new Param("Account", Account);
        Param pwdParam = new Param("Password", pwd);


        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getBaseUrl(HttpSeviceClass.Login_Url), new <LoginResp>OkHttpClientManager.ResultCallback<LoginResp>() {
            @Override
            public void onError(Request request, Exception e) {
                loginPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Login_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(LoginResp loginResp) {

                if (loginResp != null && loginResp.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    loginPresenter.onRespSucess(loginResp);
                } else if (loginResp == null) {
                    loginPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Login_Resp,  Constant.Param_error_Code);
                } else {
                    loginPresenter.onRespFail(loginResp.getMessage(), HttpDefine.Login_Resp, loginResp.getCode());
                }

            }
        }, HttpDefine.Login_Resp, accountParam, pwdParam);
    }
}
