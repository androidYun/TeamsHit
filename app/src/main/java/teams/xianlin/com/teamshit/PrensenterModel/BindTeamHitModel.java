package teams.xianlin.com.teamshit.PrensenterModel;

import android.view.View;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.BindTeamHitResp;
import teams.xianlin.com.teamshit.NetWorkResp.CompleteUserInfoResp;
import teams.xianlin.com.teamshit.Presenter.BindTeamHitPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/3.
 */
public class BindTeamHitModel {

    private BindTeamHitPresenter bindTeamHitPresenter;

    public BindTeamHitModel(BindTeamHitPresenter bindTeamHitPresenter) {
        this.bindTeamHitPresenter = bindTeamHitPresenter;
    }

    public void loadData(String Uuid, int userFor) {
        Param uuidParam = new Param("Uuid", Uuid);
        Param useForParam = new Param("UserFor", userFor);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Bind_Team_Hit_Url), new OkHttpClientManager.ResultCallback<BindTeamHitResp>() {
            @Override
            public void onError(Request request, Exception e) {
                bindTeamHitPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Bind_Team_Hit_RESP, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(BindTeamHitResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    bindTeamHitPresenter.onRespSucess(response);
                } else {
                    bindTeamHitPresenter.onRespFail(response.getMessage(), HttpDefine.Bind_Team_Hit_RESP, response.getCode());
                }
            }
        }, HttpDefine.Bind_Team_Hit_RESP, uuidParam, useForParam);
    }
}
