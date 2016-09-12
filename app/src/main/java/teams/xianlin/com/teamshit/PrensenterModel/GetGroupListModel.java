package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendsListResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetGroupListResp;
import teams.xianlin.com.teamshit.Presenter.GetGroupListPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/7/16.
 */
public class GetGroupListModel {
    private GetGroupListPresenter getGroupListPresenter;

    public GetGroupListModel(GetGroupListPresenter getGroupListPresenter) {
        this.getGroupListPresenter = getGroupListPresenter;
    }
    public void loadData(String token) {
        Param vericetionParam = new Param("Token", token);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.GET_Group_List_Url), new OkHttpClientManager.ResultCallback<GetGroupListResp>() {
            @Override
            public void onError(Request request, Exception e) {
                getGroupListPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.GET_Grop_LIST_RESP, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(GetGroupListResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    getGroupListPresenter.onRespSucess(response);
                } else {
                    getGroupListPresenter.onRespFail(response.getMessage(), HttpDefine.GET_Grop_LIST_RESP, response.getCode());
                }
            }
        }, HttpDefine.GET_Grop_LIST_RESP,vericetionParam);
    }
}
