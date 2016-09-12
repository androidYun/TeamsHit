package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleListResp;
import teams.xianlin.com.teamshit.PrensenterModel.GetFriendCircleListModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.GetFriendCircleListView;

/**
 * Created by Administrator on 2016/8/26.
 */
public class GetFriendCircleListPresenter extends BasePresenter<GetFriendCircleListView> implements IBasePsenter<GetFriendCircleListResp> {
    GetFriendCircleListView getFriendCircleListView;

    private GetFriendCircleListModel getFriendCircleListModel;

    public GetFriendCircleListPresenter(GetFriendCircleListView getFriendCircleListView) {
        this.getFriendCircleListView = getFriendCircleListView;
        getFriendCircleListModel = new GetFriendCircleListModel(this);
        attchView(getFriendCircleListView);
    }

    public void loadData(Context context, int CurPage, int CurCount) {
        getFriendCircleListView.showGetFriendCircleListProgress();
        getFriendCircleListModel.loadData(CurPage, CurCount);
    }

    @Override
    public void onRespSucess(GetFriendCircleListResp getFriendCircleListResp) {
        getFriendCircleListView.hideGetFriendCircleListProgress();
        getFriendCircleListView.onLoadSucess(getFriendCircleListResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        getFriendCircleListView.hideGetFriendCircleListProgress();
        getFriendCircleListView.onLoadFail(creteErrorMsg(errorStr, RespCode, errorCode));
    }
}
