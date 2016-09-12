package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.BindTeamHitResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetUserInforResp;
import teams.xianlin.com.teamshit.Presenter.GetUserInfoPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/13.
 */
public class GetUserInfoModel {
    private GetUserInfoPresenter getUserInfoPresenter;


    public GetUserInfoModel(GetUserInfoPresenter getUserInfoPresenter) {
        this.getUserInfoPresenter = getUserInfoPresenter;
    }

    public void loadData(long UserId) {
        Param userIdParam = new Param("UserId", UserId);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Get_User_Infor_Url), new OkHttpClientManager.ResultCallback<GetUserInforResp>() {

            @Override
            public void onError(Request request, Exception e) {
                getUserInfoPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Get_User_Infor_Resp, Constant.Param_error_Code);
            }
            @Override
            public void onResponse(GetUserInforResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    getUserInfoPresenter.onRespSucess(response);
                } else {
                    getUserInfoPresenter.onRespFail(response.getMessage(), HttpDefine.Get_User_Infor_Resp, response.getCode());
                }
            }
        }, HttpDefine.Get_User_Infor_Resp, userIdParam);
    }
}
