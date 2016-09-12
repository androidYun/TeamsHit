package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.PublishCommentResp;
import teams.xianlin.com.teamshit.NetWorkResp.ThumbsUpResp;
import teams.xianlin.com.teamshit.Presenter.OperateFriendCirclePresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/27.
 */
public class PublishCommentModel {
    OperateFriendCirclePresenter operateFriendCirclePresenter;

    public PublishCommentModel(OperateFriendCirclePresenter operateFriendCirclePresenter) {
        this.operateFriendCirclePresenter = operateFriendCirclePresenter;
    }

    public void loadData(String TargetId, String TakeId, String TakeUserId, String CommentId, int IsReply, String ReviewContent) {
        Param userIdParam = new Param("TargetId", TargetId);
        Param takeIdParam = new Param("TakeId", TakeId);
        Param commentIdParam = new Param("CommentId", CommentId);
        Param isReplyParam = new Param("IsReply", IsReply);
        Param ReviewContentParam = new Param("ReviewContent", ReviewContent);
        Param TakeUserIdParam = new Param("TakeUserId", TakeUserId);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Friend_Circle_Publish_Comment_Url), new OkHttpClientManager.ResultCallback<PublishCommentResp>() {
            @Override
            public void onError(Request request, Exception e) {
                operateFriendCirclePresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Friend_Circle_PublishComment_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(PublishCommentResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    operateFriendCirclePresenter.onRespSucess(response);
                } else {
                    operateFriendCirclePresenter.onRespFail(response.getMessage(), HttpDefine.Friend_Circle_PublishComment_Resp, response.getCode());
                }
            }
        }, HttpDefine.Friend_Circle_PublishComment_Resp, userIdParam, takeIdParam, TakeUserIdParam, commentIdParam, isReplyParam, ReviewContentParam);
    }
}
