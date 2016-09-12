package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.NetWorkResp.GetUserInforResp;
import teams.xianlin.com.teamshit.PrensenterModel.GetUserInfoModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.GetUserInfoView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/13.
 */
public class GetUserInfoPresenter extends BasePresenter<GetUserInfoView> implements IBasePsenter<GetUserInforResp> {
    private GetUserInfoView getUserInfoView;

    private GetUserInfoModel getUserInfoModel;

    public GetUserInfoPresenter(GetUserInfoView getUserInfoView) {
        this.getUserInfoView = getUserInfoView;
        getUserInfoModel = new GetUserInfoModel(this);
        attchView(getUserInfoView);
    }

    public void loadData(Context context, long UserId) {
        if (!NetUtils.getinStance().isNetworkAvailable(context)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Get_User_Infor_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(String.valueOf(UserId))) {
            onRespFail("用户id不能为空", HttpDefine.Bind_Team_Hit_RESP, Constant.Param_error_Code);
        }
        getUserInfoView.showGetUserInforProgress();
        getUserInfoModel.loadData(UserId);
    }

    @Override
    public void onRespSucess(GetUserInforResp getUserInforResp) {
        getUserInfoView.hideGetUserInforProgress();
        getUserInfoView.onLoadSucess(getUserInforResp);

    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        getUserInfoView.hideGetUserInforProgress();
        getUserInfoView.onLoadFail(creteErrorMsg(errorStr, RespCode, errorCode));
    }
}
