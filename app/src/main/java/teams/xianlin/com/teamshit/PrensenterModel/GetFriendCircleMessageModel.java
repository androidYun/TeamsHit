package teams.xianlin.com.teamshit.PrensenterModel;

import android.content.Context;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleMessageResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetUserDeatilInforResp;
import teams.xianlin.com.teamshit.Presenter.GetFriendCircleMessagePresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/9/1.
 */
public class GetFriendCircleMessageModel {
    private GetFriendCircleMessagePresenter getFriendCircleMessagePresenter;

    public GetFriendCircleMessageModel(GetFriendCircleMessagePresenter getFriendCircleMessagePresenter) {
        this.getFriendCircleMessagePresenter = getFriendCircleMessagePresenter;
    }

    public void loadData(int CurPage, int CurCount, long MessageId) {
        Param curPageParam = new Param("CurPage", CurPage);
        Param curCountParam = new Param("CurCount", CurCount);
        Param MessageIdParam = new Param("MessageId", MessageId);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.GET_Friend_Circle_Message_List_Url), new OkHttpClientManager.ResultCallback<GetFriendCircleMessageResp>() {

            @Override
            public void onError(Request request, Exception e) {
                getFriendCircleMessagePresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Get_Friend_Circle_Message_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(GetFriendCircleMessageResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    getFriendCircleMessagePresenter.onRespSucess(response);
                } else {
                    getFriendCircleMessagePresenter.onRespFail(response.getMessage(), HttpDefine.Get_Friend_Circle_Message_Resp, response.getCode());
                }
            }
        }, HttpDefine.Get_Friend_Circle_Message_Resp, curPageParam, curCountParam, MessageIdParam);
    }
}
