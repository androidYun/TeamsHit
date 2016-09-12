package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.NetWorkResp.DeleteFriendCircleMessageResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleMessageResp;
import teams.xianlin.com.teamshit.Presenter.DeleteFriendCircleMessagePresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/9/1.
 */
public class DeleteFriendCircleMessageModel {

    private DeleteFriendCircleMessagePresenter deleteFriendCircleMessagePresenter;

    public DeleteFriendCircleMessageModel(DeleteFriendCircleMessagePresenter deleteFriendCircleMessagePresenter) {
        this.deleteFriendCircleMessagePresenter = deleteFriendCircleMessagePresenter;
    }

    public void loadData() {
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Delete_Friend_Circle_Message_List_Url), new OkHttpClientManager.ResultCallback<DeleteFriendCircleMessageResp>() {

            @Override
            public void onError(Request request, Exception e) {
                deleteFriendCircleMessagePresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Delete_Friend_Circle_Message_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(DeleteFriendCircleMessageResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    deleteFriendCircleMessagePresenter.onRespSucess(response);
                } else {
                    deleteFriendCircleMessagePresenter.onRespFail(response.getMessage(), HttpDefine.Delete_Friend_Circle_Message_Resp, response.getCode());
                }
            }
        }, HttpDefine.Delete_Friend_Circle_Message_Resp);
    }
}
