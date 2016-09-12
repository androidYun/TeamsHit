package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.NetWorkResp.GetNewFriendListResp;
import teams.xianlin.com.teamshit.PrensenterModel.GetNewFriendListModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.GetNewFriendListView;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/12.
 */
public class GetNewFriendListPresenter extends BasePresenter<GetNewFriendListView> implements IBasePsenter<GetNewFriendListResp> {
    public GetNewFriendListView getNewFriendListView;

    private GetNewFriendListModel getNewFriendListModel;

    public GetNewFriendListPresenter(GetNewFriendListView getNewFriendListView) {
        this.getNewFriendListView = getNewFriendListView;
        getNewFriendListModel = new GetNewFriendListModel(this);
        attchView(getNewFriendListView);
    }

    public void loadData(Context mContext) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Get_New_Friend_List_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        getNewFriendListView.showGetNewFriendListProgress();
        getNewFriendListModel.loadData();
    }

    @Override
    public void onRespSucess(GetNewFriendListResp getNewFriendListResp) {
        getNewFriendListView.hideGetNewFriendListProgress();
        getNewFriendListView.onLoadSucess(getNewFriendListResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        getNewFriendListView.hideGetNewFriendListProgress();
        getNewFriendListView.onLoadFail(creteErrorMsg(errorStr, RespCode, errorCode));
    }
}
