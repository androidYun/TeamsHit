package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.GetUserDeatilInforResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetUserInforResp;
import teams.xianlin.com.teamshit.Presenter.GetUserDetailInforPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/15.
 */
public class GetUserDetailInforModel {
    private GetUserDetailInforPresenter getUserDetailInforPresenter;

    public GetUserDetailInforModel(GetUserDetailInforPresenter getUserDetailInforPresenter) {
        this.getUserDetailInforPresenter = getUserDetailInforPresenter;
    }

    public void loadData() {
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Get_Detail_User_Infor_Url), new OkHttpClientManager.ResultCallback<GetUserDeatilInforResp>() {

            @Override
            public void onError(Request request, Exception e) {
                getUserDetailInforPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Get_Detail_User_Infor_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(GetUserDeatilInforResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    getUserDetailInforPresenter.onRespSucess(response);
                } else {
                    getUserDetailInforPresenter.onRespFail(response.getMessage(), HttpDefine.Get_Detail_User_Infor_Resp, response.getCode());
                }
            }
        }, HttpDefine.Get_Detail_User_Infor_Resp);
    }
}
