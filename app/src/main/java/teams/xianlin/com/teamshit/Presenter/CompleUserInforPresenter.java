package teams.xianlin.com.teamshit.Presenter;

import android.app.Activity;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.CompleteUserInfoResp;
import teams.xianlin.com.teamshit.PrensenterModel.CompleteUserInfoModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.CompleUserInforView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/7/14.
 */
public class CompleUserInforPresenter extends BasePresenter<CompleUserInforView> implements IBasePsenter<CompleteUserInfoResp> {
    private CompleUserInforView compleUserInforView;

    private CompleteUserInfoModel completeUserInfoModel;

    public CompleUserInforPresenter(CompleUserInforView compleUserInforView) {
        this.compleUserInforView = compleUserInforView;
        completeUserInfoModel = new CompleteUserInfoModel(this);
        attchView(compleUserInforView);
    }

    public void loadData(String IconUrl, String NickName, String UserName, String Gender, String Province, String City, String BirthDate, Activity context) {
        if (!NetUtils.getinStance().isNetworkAvailable(context)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Complete_User_Infor_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(IconUrl)) {
            onRespFail("没有上传头像", HttpDefine.Complete_User_Infor_Resp, Constant.Param_error_Code);
            return;
        }
        if (StringUtils.isBlank(NickName)) {
            onRespFail("昵称不能为空", HttpDefine.Complete_User_Infor_Resp, Constant.Param_error_Code);
            return;
        }
        if (StringUtils.isBlank(UserName)) {
            onRespFail("用户名不能为空", HttpDefine.Complete_User_Infor_Resp, Constant.Param_error_Code);
            return;
        }
        if (StringUtils.isBlank(Gender)) {
            onRespFail("请选择性别", HttpDefine.Complete_User_Infor_Resp, Constant.Param_error_Code);
            return;
        }
        if (StringUtils.isBlank(Province)) {
            onRespFail("请选择省份", HttpDefine.Complete_User_Infor_Resp, Constant.Param_error_Code);
            return;
        }
        if (StringUtils.isBlank(City)) {
            onRespFail("请选择城市", HttpDefine.Complete_User_Infor_Resp, Constant.Param_error_Code);
            return;
        }
//        if (StringUtils.isBlank(BirthDate)) {
//            onRespFail("请输入生日", HttpDefine.Complete_User_Infor_Resp, Constant.Param_error_Code);
//            return;
//        }
        compleUserInforView.showCompleUserInforProgress();
        completeUserInfoModel.loadData(IconUrl, NickName, UserName, Gender, Province, City, BirthDate);
    }

    @Override
    public void onRespSucess(CompleteUserInfoResp completeUserInfoResp) {
        compleUserInforView.hideCompleUserInforProgress();
        compleUserInforView.onLoadSucess(completeUserInfoResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setRespCode(RespCode);
        compleUserInforView.onLoadFail(errorMsg);
        compleUserInforView.hideCompleUserInforProgress();
    }
}
