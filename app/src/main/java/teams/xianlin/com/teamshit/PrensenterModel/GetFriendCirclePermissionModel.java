package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCirclePermissionResp;
import teams.xianlin.com.teamshit.Presenter.GetFriendCirclePermissionPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/19.
 */
public class GetFriendCirclePermissionModel {

    private GetFriendCirclePermissionPresenter getFriendCirclePermissionPresenter;

    public GetFriendCirclePermissionModel(GetFriendCirclePermissionPresenter getFriendCirclePermissionPresenter) {
        this.getFriendCirclePermissionPresenter = getFriendCirclePermissionPresenter;
    }

    public void loadData(long targetId) {
        Param targetIdParam = new Param("TargetId", targetId);

        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Get_Friend_Cirle_Permission_Url), new OkHttpClientManager.ResultCallback<GetFriendCirclePermissionResp>() {
            @Override
            public void onError(Request request, Exception e) {
                getFriendCirclePermissionPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Get_Friend_Circle_Permission_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(GetFriendCirclePermissionResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    getFriendCirclePermissionPresenter.onRespSucess(response);
                } else {
                    getFriendCirclePermissionPresenter.onRespFail(response.getMessage(), HttpDefine.Get_Friend_Circle_Permission_Resp, response.getCode());
                }
            }
        }, HttpDefine.Get_Friend_Circle_Permission_Resp, targetIdParam);
    }
}
