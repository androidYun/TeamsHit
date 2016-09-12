package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.CancleThumbsUpResp;
import teams.xianlin.com.teamshit.NetWorkResp.DeleteCommentResp;
import teams.xianlin.com.teamshit.NetWorkResp.DeleteTakeResp;
import teams.xianlin.com.teamshit.NetWorkResp.PublishCommentResp;
import teams.xianlin.com.teamshit.NetWorkResp.ThumbsUpResp;

/**
 * Created by Administrator on 2016/8/27.
 */
public interface OperateFriendCircleView {
    void onLoadSucess(ThumbsUpResp thumbsUpResp);

    void onLoadSucess(CancleThumbsUpResp cancleThumbsUpResp);

    void onLoadSucess(PublishCommentResp publishCommentResp);

    void onLoadSucess(DeleteTakeResp deleteTakeResp);

    void onLoadSucess(DeleteCommentResp deleteCommentResp);

    void onLoadFail(ErrorMsg errorMsg);

    void showRegisterProgress();

    void hideRegsiterProgress();
}
