package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.DeleteCommentResp;
import teams.xianlin.com.teamshit.NetWorkResp.ThumbsUpResp;
import teams.xianlin.com.teamshit.Presenter.OperateFriendCirclePresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/31.
 */
public class DeleteCommentModel {
    private OperateFriendCirclePresenter operateFriendCirclePresenter;

    public DeleteCommentModel(OperateFriendCirclePresenter operateFriendCirclePresenter) {
        this.operateFriendCirclePresenter = operateFriendCirclePresenter;
    }

    public void loadData(String UserId, String TakeId) {
        Param commentIdParam = new Param("CommentId", UserId);
        Param takeIdParam = new Param("TakeId", TakeId);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Friend_Circle_Delete_Comment_Url), new OkHttpClientManager.ResultCallback<DeleteCommentResp>() {
            @Override
            public void onError(Request request, Exception e) {
                operateFriendCirclePresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Delete_Comment_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(DeleteCommentResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    operateFriendCirclePresenter.onRespSucess(response);
                } else {
                    operateFriendCirclePresenter.onRespFail(response.getMessage(), HttpDefine.Delete_Comment_Resp, response.getCode());
                }
            }
        }, HttpDefine.Delete_Comment_Resp, commentIdParam, takeIdParam);
    }
}
