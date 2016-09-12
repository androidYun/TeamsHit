package teams.xianlin.com.teamshit.PrensenterModel;

import android.content.Context;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.BuzzerSwitchResp;
import teams.xianlin.com.teamshit.NetWorkResp.SetDisPlayNameResp;
import teams.xianlin.com.teamshit.Presenter.SetDisPlayNamePresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/19.
 */
public class SetDisPlayNameModel {

    SetDisPlayNamePresenter setDisPlayNamePresenter;

    public SetDisPlayNameModel(SetDisPlayNamePresenter setDisPlayNamePresenter) {
        this.setDisPlayNamePresenter = setDisPlayNamePresenter;
    }

    public void loadData(long targetId, String DisplayName) {

        Param targetIdParam = new Param("TargetId", targetId);
        Param displayNameParam = new Param("DisplayName", DisplayName);

        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Set_Display_Name_Url), new OkHttpClientManager.ResultCallback<SetDisPlayNameResp>() {
            @Override
            public void onError(Request request, Exception e) {
                setDisPlayNamePresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Set_Display_Name_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(SetDisPlayNameResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    setDisPlayNamePresenter.onRespSucess(response);
                } else {
                    setDisPlayNamePresenter.onRespFail(response.getMessage(), HttpDefine.Set_Display_Name_Resp, response.getCode());
                }
            }
        }, HttpDefine.Set_Display_Name_Resp, targetIdParam, displayNameParam);
    }
}
