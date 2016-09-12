package teams.xianlin.com.teamshit.PrensenterModel;

import android.content.Context;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.CompleteUserInfoResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditTeamNameResp;
import teams.xianlin.com.teamshit.Presenter.EditTeamNamePresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/8.
 */
public class EditTeamNameModel {
    private EditTeamNamePresenter editTeamNamePresenter;

    public EditTeamNameModel(EditTeamNamePresenter editTeamNamePresenter) {
        this.editTeamNamePresenter = editTeamNamePresenter;
    }

    public void loadData(String uuId, String NewDeviceName) {
        Param iconUrlParam = new Param("Uuid", uuId);
        Param niackNameParam = new Param("NewDeviceName", NewDeviceName);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Edit_Team_Name_Url), new OkHttpClientManager.ResultCallback<EditTeamNameResp>() {
            @Override
            public void onError(Request request, Exception e) {
                editTeamNamePresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Edit_Team_Name_RESP, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(EditTeamNameResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    editTeamNamePresenter.onRespSucess(response);
                } else {
                    editTeamNamePresenter.onRespFail(response.getMessage(), HttpDefine.Edit_Team_Name_RESP, response.getCode());
                }
            }
        }, HttpDefine.Edit_Team_Name_RESP,iconUrlParam, niackNameParam);
    }
}
