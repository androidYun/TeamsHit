package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.VerificationCodeResp;
import teams.xianlin.com.teamshit.Presenter.VerificationPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/7/12.
 */
public class VerificationModel {
    private VerificationPresenter verificationPresenter;

    public VerificationModel(VerificationPresenter verificationPresenter) {
        this.verificationPresenter = verificationPresenter;
    }

    public void loadData(String phoneNumber) {

        Param vericetionParam = new Param("PhoneNumber", phoneNumber);

        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getBaseUrl(HttpSeviceClass.Verification_Code_Url), new OkHttpClientManager.ResultCallback<VerificationCodeResp>() {
            @Override
            public void onError(Request request, Exception e) {
                verificationPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Verification_Code_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(VerificationCodeResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    verificationPresenter.onRespSucess(response);
                } else {
                    verificationPresenter.onRespFail(response.getMessage(), HttpDefine.Verification_Code_Resp, response.getCode());
                }
            }
        }, HttpDefine.Verification_Code_Resp, vericetionParam);
    }
}
