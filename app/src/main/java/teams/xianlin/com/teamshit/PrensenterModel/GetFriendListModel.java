package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendsListResp;
import teams.xianlin.com.teamshit.NetWorkResp.VerificationCodeResp;
import teams.xianlin.com.teamshit.Presenter.GetFriendListPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/7/16.
 */
public class GetFriendListModel {
    private GetFriendListPresenter getFriendListPresenter;

    public GetFriendListModel(GetFriendListPresenter getFriendListPresenter) {
        this.getFriendListPresenter = getFriendListPresenter;
    }

    public void loadData() {
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.GET_FRIENDS_List_Url), new OkHttpClientManager.ResultCallback<GetFriendsListResp>() {
            @Override
            public void onError(Request request, Exception e) {
                getFriendListPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.GET_FRIEND_LIST_RESP, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(GetFriendsListResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    getFriendListPresenter.onRespSucess(response);
                } else {
                    getFriendListPresenter.onRespFail(response.getMessage(), HttpDefine.GET_FRIEND_LIST_RESP, response.getCode());
                }
            }
        },HttpDefine.GET_FRIEND_LIST_RESP);
    }
}
