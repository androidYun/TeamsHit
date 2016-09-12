package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.SetDisPlayNameResp;
import teams.xianlin.com.teamshit.NetWorkResp.SetTargetPermissionResp;
import teams.xianlin.com.teamshit.Presenter.SetTargetPermissionPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/19.
 */
public class SetTargetPermissionModel {
    private SetTargetPermissionPresenter setTargetPermissionPresenter;

    public SetTargetPermissionModel(SetTargetPermissionPresenter setTargetPermissionPresenter) {
        this.setTargetPermissionPresenter = setTargetPermissionPresenter;
    }

    public void loadData(long targetId, int Permission) {
        Param targetIdParam = new Param("TargetId", targetId);
        Param permissionParam = new Param("Permission", Permission);

        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Set_Target_Permission_Url), new OkHttpClientManager.ResultCallback<SetTargetPermissionResp>() {
            @Override
            public void onError(Request request, Exception e) {
                setTargetPermissionPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Set_Target_Permission_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(SetTargetPermissionResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    setTargetPermissionPresenter.onRespSucess(response);
                } else {
                    setTargetPermissionPresenter.onRespFail(response.getMessage(), HttpDefine.Set_Target_Permission_Resp, response.getCode());
                }
            }
        }, HttpDefine.Set_Target_Permission_Resp, targetIdParam, permissionParam);
    }
}
