package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendsListResp;
import teams.xianlin.com.teamshit.PrensenterModel.GetFriendListModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.GetFriendListView;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/7/16.
 */
public class GetFriendListPresenter extends BasePresenter<GetFriendListView> implements IBasePsenter<GetFriendsListResp> {
    private GetFriendListView getFriendListView;

    private GetFriendListModel getFriendListModel;

    public GetFriendListPresenter(GetFriendListView getFriendListView) {
        this.getFriendListView = getFriendListView;
        getFriendListModel = new GetFriendListModel(this);
        attchView(getFriendListView);
    }

    public void loadData(Context mContext) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.GET_FRIEND_LIST_RESP, Constant.Not_NetWork_error_Code);
            return;
        }

        getFriendListModel.loadData();
    }

    @Override
    public void onRespSucess(GetFriendsListResp getFriendsListResp) {
        getFriendListView.onLoadSucess(getFriendsListResp);

    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setErrorCode(errorCode);
        errorMsg.setRespCode(RespCode);
        getFriendListView.onLoadFail(errorMsg);
    }
}
