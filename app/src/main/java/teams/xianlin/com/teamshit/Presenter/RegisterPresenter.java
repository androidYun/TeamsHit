package teams.xianlin.com.teamshit.Presenter;

import android.app.Activity;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.RegisterResp;
import teams.xianlin.com.teamshit.PrensenterModel.RegisterModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.RegisterView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/7/12.
 */
public class RegisterPresenter extends BasePresenter<RegisterView> implements IBasePsenter<RegisterResp> {
    private RegisterView registerView;

    private RegisterModel registerModel;

    public RegisterPresenter(RegisterView registerView) {
        this.registerView = registerView;
        registerModel = new RegisterModel(this);
        attchView(registerView);
    }

    public void loadData(String Phone, String Password, String code, String mVerificationCode, Activity context) {
        if (!NetUtils.getinStance().isNetworkAvailable(context)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Register_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(Phone)) {
            onRespFail("请输入手机号", HttpDefine.Register_Resp, Constant.Param_error_Code);
            return;
        }
        if (StringUtils.isBlank(Password)) {
            onRespFail("请输入密码", HttpDefine.Register_Resp, Constant.Param_error_Code);
            return;
        }
        if (Password.length() < 6 || Password.length() >= 20) {
            onRespFail("密码长度大于6位。小于20位", HttpDefine.Register_Resp, Constant.Param_error_Code);
            return;
        }
//        if (StringUtils.isBlank(code)) {
//            onRespFail("请验证码", HttpDefine.Register_Resp, Constant.Param_error_Code);
//            return;
//        }
//        if (StringUtils.isBlank(mVerificationCode)) {
//            onRespFail("请先获取验证码", HttpDefine.Register_Resp, Constant.Param_error_Code);
//            return;
//        }
//        if (!mVerificationCode.equals(code)) {
//            onRespFail("验证码不正确", HttpDefine.Register_Resp, Constant.Param_error_Code);
//            return;
//        }
        registerView.showRegisterProgress();
        registerModel.loadData(Phone, Password);
    }

    @Override
    public void onRespSucess(RegisterResp registerResp) {
        registerView.hideRegsiterProgress();
        registerView.onLoadSucess(registerResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        registerView.hideRegsiterProgress();
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setRespCode(RespCode);
        errorMsg.setErrorCode(errorCode);
        errorMsg.setErrorMsg(errorStr);
        registerView.onLoadFail(errorMsg);
    }
}
