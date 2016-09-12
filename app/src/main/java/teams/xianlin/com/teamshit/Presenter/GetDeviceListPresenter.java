package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetDeviceResp;
import teams.xianlin.com.teamshit.PrensenterModel.GetDeviceListModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.GetDeviceListView;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/2.
 */
public class GetDeviceListPresenter extends BasePresenter<GetDeviceListView> implements IBasePsenter<GetDeviceResp> {
    private GetDeviceListView getDeviceListView;

    private GetDeviceListModel getDeviceListModel;

    public GetDeviceListPresenter(GetDeviceListView getDeviceListView) {
        this.getDeviceListView = getDeviceListView;
        getDeviceListModel = new GetDeviceListModel(this);
    }

    public void loadData(Context mContext) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Get_Team_Hit_Device_List_RESP, Constant.Not_NetWork_error_Code);
            return;
        }
        getDeviceListView.showGetDevicesProgress();
        getDeviceListModel.loadData();
    }

    @Override
    public void onRespSucess(GetDeviceResp getDeviceResp) {
        getDeviceListView.hideGetDevicesProgress();
        getDeviceListView.onLoadSucess(getDeviceResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setRespCode(RespCode);
        getDeviceListView.onLoadFail(errorMsg);
        getDeviceListView.hideGetDevicesProgress();

    }
}
