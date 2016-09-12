package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.SetUserPermissionResp;
import teams.xianlin.com.teamshit.Presenter.SetUserPermissionPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/19.
 */
public class SetUserPermissionModel {

    private SetUserPermissionPresenter setUserPermissionPresenter;

    public SetUserPermissionModel(SetUserPermissionPresenter setUserPermissionPresenter) {
        this.setUserPermissionPresenter = setUserPermissionPresenter;
    }

    public void loadData(long targetId, int Permission) {
        Param targetIdParam = new Param("TargetId", targetId);
        Param permissionParam = new Param("Permission", Permission);

        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Set_User_Permission_Url), new OkHttpClientManager.ResultCallback<SetUserPermissionResp>() {
            @Override
            public void onError(Request request, Exception e) {
                setUserPermissionPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Set_User_Permission_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(SetUserPermissionResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    setUserPermissionPresenter.onRespSucess(response);
                } else {
                    setUserPermissionPresenter.onRespFail(response.getMessage(), HttpDefine.Set_User_Permission_Resp, response.getCode());
                }
            }
        }, HttpDefine.Set_User_Permission_Resp, targetIdParam, permissionParam);
    }
}
