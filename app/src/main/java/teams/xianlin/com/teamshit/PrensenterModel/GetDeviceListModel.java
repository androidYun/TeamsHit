package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.NetWorkResp.GetDeviceResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendsListResp;
import teams.xianlin.com.teamshit.Presenter.GetDeviceListPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/2.
 */
public class GetDeviceListModel {
    private GetDeviceListPresenter getDeviceListPresenter;

    public GetDeviceListModel(GetDeviceListPresenter getDeviceListPresenter) {
        this.getDeviceListPresenter = getDeviceListPresenter;
    }

    public void loadData() {
        OkHttpClientManager.getInstance().getAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Get_Device_List_Url), new OkHttpClientManager.ResultCallback<GetDeviceResp>() {
            @Override
            public void onError(Request request, Exception e) {
                getDeviceListPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Get_Team_Hit_Device_List_RESP, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(GetDeviceResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    getDeviceListPresenter.onRespSucess(response);
                } else {
                    getDeviceListPresenter.onRespFail(response.getMessage(), HttpDefine.Get_Team_Hit_Device_List_RESP, response.getCode());
                }
            }
        }, HttpDefine.Get_Team_Hit_Device_List_RESP);
    }
}
