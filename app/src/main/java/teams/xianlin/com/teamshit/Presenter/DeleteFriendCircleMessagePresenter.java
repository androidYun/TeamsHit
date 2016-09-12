package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.NetWorkResp.DeleteFriendCircleMessageResp;
import teams.xianlin.com.teamshit.PrensenterModel.DeleteFriendCircleMessageModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.DeleteFriendCircleMessageView;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/9/1.
 */
public class DeleteFriendCircleMessagePresenter extends BasePresenter<DeleteFriendCircleMessageView> implements IBasePsenter<DeleteFriendCircleMessageResp> {

    private DeleteFriendCircleMessageView deleteFriendCircleMessageView;

    private DeleteFriendCircleMessageModel deleteFriendCircleMessageModel;

    public DeleteFriendCircleMessagePresenter(DeleteFriendCircleMessageView deleteFriendCircleMessageResp) {
        this.deleteFriendCircleMessageView = deleteFriendCircleMessageResp;
        deleteFriendCircleMessageModel = new DeleteFriendCircleMessageModel(this);
    }

    public void loadData(Context mContext) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Delete_Friend_Circle_Message_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        deleteFriendCircleMessageView.showDeleteFriendMessageProgress();
        deleteFriendCircleMessageModel.loadData();
    }

    @Override
    public void onRespSucess(DeleteFriendCircleMessageResp deleteFriendCircleMessageResp) {
        deleteFriendCircleMessageView.hideDeleteFriendMessageProgress();
        deleteFriendCircleMessageView.onLoadSucess(deleteFriendCircleMessageResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        deleteFriendCircleMessageView.hideDeleteFriendMessageProgress();
        deleteFriendCircleMessageView.onLoadFail(creteErrorMsg(errorStr, RespCode, errorCode));
    }
}
