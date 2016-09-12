package teams.xianlin.com.teamshit.Presenter.Base;

import teams.xianlin.com.teamshit.NetWorkResp.CancleThumbsUpResp;
import teams.xianlin.com.teamshit.NetWorkResp.DeleteCommentResp;
import teams.xianlin.com.teamshit.NetWorkResp.DeleteTakeResp;
import teams.xianlin.com.teamshit.NetWorkResp.PublishCommentResp;
import teams.xianlin.com.teamshit.NetWorkResp.ThumbsUpResp;

/**
 * Created by Administrator on 2016/8/27.
 */
public interface IBaseOperateFriendCirclePresenter {
    void onRespSucess(ThumbsUpResp thumbsUpResp);

    void onRespSucess(CancleThumbsUpResp cancleThumbsUpResp);

    void onRespSucess(PublishCommentResp publishCommentResp);

    void onRespSucess(DeleteTakeResp deleteTakeResp);

    void onRespSucess(DeleteCommentResp deleteCommentResp);

    void onRespFail(String errorStr, int RespCode, int errorCode);
}
