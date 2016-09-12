package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.NetWorkResp.CancleThumbsUpResp;
import teams.xianlin.com.teamshit.NetWorkResp.DeleteCommentResp;
import teams.xianlin.com.teamshit.NetWorkResp.DeleteTakeResp;
import teams.xianlin.com.teamshit.NetWorkResp.PublishCommentResp;
import teams.xianlin.com.teamshit.NetWorkResp.ThumbsUpResp;
import teams.xianlin.com.teamshit.PrensenterModel.CancleThumbsUpModel;
import teams.xianlin.com.teamshit.PrensenterModel.DeleteCommentModel;
import teams.xianlin.com.teamshit.PrensenterModel.DeleteTakeModel;
import teams.xianlin.com.teamshit.PrensenterModel.PublishCommentModel;
import teams.xianlin.com.teamshit.PrensenterModel.ThumbsUpModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBaseOperateFriendCirclePresenter;
import teams.xianlin.com.teamshit.PresenterView.OperateFriendCircleView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/27.
 */
public class OperateFriendCirclePresenter extends BasePresenter<OperateFriendCircleView> implements IBaseOperateFriendCirclePresenter {
    private OperateFriendCircleView operateFriendCircleView;

    private ThumbsUpModel thumbsUpModel;

    private CancleThumbsUpModel cancleThumbsUpModel;

    private PublishCommentModel publishCommentModel;

    private DeleteTakeModel deleteTakeModel;

    private DeleteCommentModel deleteCommentModel;


    public OperateFriendCirclePresenter(OperateFriendCircleView operateFriendCircleView) {
        this.operateFriendCircleView = operateFriendCircleView;
        thumbsUpModel = new ThumbsUpModel(this);
        cancleThumbsUpModel = new CancleThumbsUpModel(this);
        publishCommentModel = new PublishCommentModel(this);
        deleteTakeModel = new DeleteTakeModel(this);
        deleteCommentModel = new DeleteCommentModel(this);
    }


    public void deleteTakeData(Context mContext, String UserId, String TakeId) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Friend_Circle_DeleteTake_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        operateFriendCircleView.showRegisterProgress();
        deleteTakeModel.loadData(UserId, TakeId);
    }

    public void publishCommentData(Context mContext, String TargetId, String takeId, String TakeUserId, String Commentid, int Isreply, String Reviewcontent) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Friend_Circle_PublishComment_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        operateFriendCircleView.showRegisterProgress();
        publishCommentModel.loadData(TargetId, takeId, TakeUserId, Commentid, Isreply, Reviewcontent);
    }


    public void ThumbsUpData(Context mContext, String UserId, String TakeId) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Friend_Circle_Thumbs_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        operateFriendCircleView.showRegisterProgress();
        thumbsUpModel.loadData(UserId, TakeId);
    }

    public void cancleThumbsUpData(Context mContext, String UserId, String TakeId) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Friend_Circle_Cancle_Thumbs_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        operateFriendCircleView.showRegisterProgress();
        cancleThumbsUpModel.loadData(UserId, TakeId);
    }

    public void deleteCommentData(Context mContext, String CommentId, String TakeId) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Delete_Comment_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(CommentId)) {
            onRespFail("发生错误，请重新请求", HttpDefine.Delete_Comment_Resp, Constant.Param_error_Code);
            return;
        }
        if (StringUtils.isBlank(TakeId)) {
            onRespFail("发生错误，请重新请求", HttpDefine.Delete_Comment_Resp, Constant.Param_error_Code);
            return;
        }
        operateFriendCircleView.showRegisterProgress();
        deleteCommentModel.loadData(CommentId, TakeId);
    }

    @Override
    public void onRespSucess(ThumbsUpResp thumbsUpResp) {
        operateFriendCircleView.hideRegsiterProgress();
        operateFriendCircleView.onLoadSucess(thumbsUpResp);
    }

    @Override
    public void onRespSucess(CancleThumbsUpResp cancleThumbsUpResp) {
        operateFriendCircleView.hideRegsiterProgress();
        operateFriendCircleView.onLoadSucess(cancleThumbsUpResp);
    }

    @Override
    public void onRespSucess(PublishCommentResp publishCommentResp) {
        operateFriendCircleView.hideRegsiterProgress();
        operateFriendCircleView.onLoadSucess(publishCommentResp);
    }

    @Override
    public void onRespSucess(DeleteTakeResp deleteTakeResp) {
        operateFriendCircleView.hideRegsiterProgress();
        operateFriendCircleView.onLoadSucess(deleteTakeResp);
    }

    @Override
    public void onRespSucess(DeleteCommentResp deleteCommentResp) {
        operateFriendCircleView.hideRegsiterProgress();
        operateFriendCircleView.onLoadSucess(deleteCommentResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        operateFriendCircleView.hideRegsiterProgress();
    }


}
