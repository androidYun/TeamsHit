package teams.xianlin.com.teamshit.Presenter;

import android.app.Activity;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.VerificationCodeResp;
import teams.xianlin.com.teamshit.PrensenterModel.VerificationModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.VerificationCodeView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/7/12.
 */
public class VerificationPresenter extends BasePresenter<VerificationCodeView> implements IBasePsenter<VerificationCodeResp> {
    private VerificationCodeView verificationCodeView;

    private VerificationModel verificationModel;

    public VerificationPresenter(VerificationCodeView verificationCodeView) {
        this.verificationCodeView = verificationCodeView;
        attchView(verificationCodeView);
        verificationModel = new VerificationModel(this);
    }

    public void loadData(String phoneNumber, Activity context) {
        if (!NetUtils.getinStance().isNetworkAvailable(context)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Verification_Code_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(phoneNumber)) {
            onRespFail("请输入手机号", HttpDefine.Verification_Code_Resp, Constant.Param_error_Code);
            return;
        }
        verificationModel.loadData(phoneNumber);
    }

    @Override
    public void onRespSucess(VerificationCodeResp verificationCodeResp) {
        verificationCodeView.onLoadSucess(verificationCodeResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setErrorCode(errorCode);
        errorMsg.setRespCode(RespCode);
        verificationCodeView.onLoadFail(errorMsg);
    }
}
