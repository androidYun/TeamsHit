package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleListResp;
import teams.xianlin.com.teamshit.Presenter.GetFriendCircleListPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/26.
 */
public class GetFriendCircleListModel {
    private GetFriendCircleListPresenter getFriendCircleListPresenter;

    public GetFriendCircleListModel(GetFriendCircleListPresenter getFriendCircleListPresenter) {
        this.getFriendCircleListPresenter = getFriendCircleListPresenter;
    }

    public void loadData(int CurPage, int CurCount) {
        Param curPageParam = new Param("CurPage",CurPage);
        Param curCountParam = new Param("CurCount",CurCount);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Get_Friend_Cirle_List_Url), new OkHttpClientManager.ResultCallback<GetFriendCircleListResp>() {
            @Override
            public void onError(Request request, Exception e) {
                getFriendCircleListPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Get_Friend_Circle_List_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(GetFriendCircleListResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    getFriendCircleListPresenter.onRespSucess(response);
                } else {
                    getFriendCircleListPresenter.onRespFail(response.getMessage(), HttpDefine.Get_Friend_Circle_List_Resp, response.getCode());
                }
            }
        }, HttpDefine.Get_Friend_Circle_List_Resp, curPageParam, curCountParam);
    }
}
