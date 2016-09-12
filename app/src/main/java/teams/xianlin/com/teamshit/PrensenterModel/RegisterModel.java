package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.RegisterResp;
import teams.xianlin.com.teamshit.Presenter.RegisterPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/7/12.
 */
public class RegisterModel {
    private RegisterPresenter registerPresenter;

    public RegisterModel(RegisterPresenter registerPresenter) {
        this.registerPresenter = registerPresenter;
    }


    public void loadData(String phone, String pwd) {
        Param phoneParam = new Param("Phone", phone);
        Param pwdParam = new Param("Password", pwd);
        Param typeParam = new Param("RegFromType", "3");
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getBaseUrl(HttpSeviceClass.Register_URL), new OkHttpClientManager.ResultCallback<RegisterResp>() {
            @Override
            public void onError(Request request, Exception e) {

                registerPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Register_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(RegisterResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    registerPresenter.onRespSucess(response);
                } else {
                    registerPresenter.onRespFail(response.getMessage(), HttpDefine.Register_Resp, response.getCode());
                }
            }
        },  HttpDefine.Register_Resp,phoneParam, pwdParam, typeParam);
    }
}
