package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendsListResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetNewFriendListResp;
import teams.xianlin.com.teamshit.Presenter.GetNewFriendListPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/12.
 */
public class GetNewFriendListModel {
    private GetNewFriendListPresenter getNewFriendListPresenter;

    public GetNewFriendListModel(GetNewFriendListPresenter getNewFriendListPresenter) {
        this.getNewFriendListPresenter = getNewFriendListPresenter;
    }

    public void loadData() {
        OkHttpClientManager.getInstance().getAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Get_New_Friend_List_Url), new OkHttpClientManager.ResultCallback<GetNewFriendListResp>() {
            @Override
            public void onError(Request request, Exception e) {
                getNewFriendListPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Get_New_Friend_List_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(GetNewFriendListResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    getNewFriendListPresenter.onRespSucess(response);
                } else {
                    getNewFriendListPresenter.onRespFail(response.getMessage(), HttpDefine.Get_New_Friend_List_Resp, response.getCode());
                }
            }
        }, HttpDefine.Get_New_Friend_List_Resp);
    }
}
