package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleMessageResp;
import teams.xianlin.com.teamshit.PrensenterModel.GetFriendCircleMessageModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.GetFriendCircleMessageView;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/9/1.
 */
public class GetFriendCircleMessagePresenter extends BasePresenter implements IBasePsenter<GetFriendCircleMessageResp> {

    private GetFriendCircleMessageView getFriendCircleMessgeView;

    private GetFriendCircleMessageModel getFriendCircleMessageModel;

    public GetFriendCircleMessagePresenter(GetFriendCircleMessageView getFriendCircleMessgeView) {
        this.getFriendCircleMessgeView = getFriendCircleMessgeView;
        getFriendCircleMessageModel = new GetFriendCircleMessageModel(this);
    }

    public void loadData(Context mContext, int CurPage, int CurCount, long MessageId) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Get_Friend_Circle_Message_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        getFriendCircleMessgeView.showGetFriendCircleMessageProgress();
        getFriendCircleMessageModel.loadData(CurPage, CurCount, MessageId);
    }

    @Override
    public void onRespSucess(GetFriendCircleMessageResp getFriendCircleMessgeResp) {
        getFriendCircleMessgeView.hideGetFriendCircleMessageProgress();
        getFriendCircleMessgeView.onLoadSucess(getFriendCircleMessgeResp);

    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        getFriendCircleMessgeView.hideGetFriendCircleMessageProgress();
        getFriendCircleMessgeView.onLoadFail(creteErrorMsg(errorStr, RespCode, errorCode));
    }
}
