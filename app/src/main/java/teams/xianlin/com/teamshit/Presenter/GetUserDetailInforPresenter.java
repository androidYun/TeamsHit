package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.NetWorkResp.GetUserDeatilInforResp;
import teams.xianlin.com.teamshit.PrensenterModel.GetUserDetailInforModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.GetUserDetailInforView;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/15.
 */
public class GetUserDetailInforPresenter extends BasePresenter<GetUserDetailInforView> implements IBasePsenter<GetUserDeatilInforResp> {

    private GetUserDetailInforView getUserDetailInforView;

    private GetUserDetailInforModel getUserDetailInforModel;

    public GetUserDetailInforPresenter(GetUserDetailInforView getUserDetailInforView) {
        this.getUserDetailInforView = getUserDetailInforView;
        getUserDetailInforModel = new GetUserDetailInforModel(this);
    }

    public void loadData(Context mContext) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Get_Detail_User_Infor_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        getUserDetailInforView.showGetUserInforProgress();
        getUserDetailInforModel.loadData();
    }

    @Override
    public void onRespSucess(GetUserDeatilInforResp getUserDeatilInforResp) {
        getUserDetailInforView.hideGetUserInforProgress();
        getUserDetailInforView.onLoadSucess(getUserDeatilInforResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        getUserDetailInforView.hideGetUserInforProgress();
        getUserDetailInforView.onLoadFail(creteErrorMsg(errorStr, RespCode, errorCode));

    }
}
