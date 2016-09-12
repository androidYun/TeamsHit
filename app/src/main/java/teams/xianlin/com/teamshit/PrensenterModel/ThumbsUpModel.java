package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.BindTeamHitResp;
import teams.xianlin.com.teamshit.NetWorkResp.ThumbsUpResp;
import teams.xianlin.com.teamshit.Presenter.OperateFriendCirclePresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/27.
 */
public class ThumbsUpModel {
    OperateFriendCirclePresenter operateFriendCirclePresenter;

    public ThumbsUpModel(OperateFriendCirclePresenter operateFriendCirclePresenter) {
        this.operateFriendCirclePresenter = operateFriendCirclePresenter;
    }

    public void loadData(String UserId, String TakeId) {
        Param userIdParam = new Param("UserId", UserId);
        Param takeIdParam = new Param("TakeId", TakeId);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Friend_Circle_Thumbs_Url), new OkHttpClientManager.ResultCallback<ThumbsUpResp>() {
            @Override
            public void onError(Request request, Exception e) {
                operateFriendCirclePresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Friend_Circle_Thumbs_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(ThumbsUpResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    operateFriendCirclePresenter.onRespSucess(response);
                } else {
                    operateFriendCirclePresenter.onRespFail(response.getMessage(), HttpDefine.Friend_Circle_Thumbs_Resp, response.getCode());
                }
            }
        }, HttpDefine.Friend_Circle_Thumbs_Resp, userIdParam, takeIdParam);
    }
}
