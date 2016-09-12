package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.SearchGroupListResp;
import teams.xianlin.com.teamshit.PrensenterModel.SearchGroupListModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.SearchGroupListView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/10.
 */
public class SearchGroupListPresenter extends BasePresenter<SearchGroupListView> implements IBasePsenter<SearchGroupListResp> {
    private SearchGroupListView searchGroupListView;

    private SearchGroupListModel searchGroupListModel;

    public SearchGroupListPresenter(SearchGroupListView searchGroupListView) {
        this.searchGroupListView = searchGroupListView;
        searchGroupListModel = new SearchGroupListModel(this);
        attchView(searchGroupListView);
    }

    public void loadData(Context mContext, String groupAccount) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Search_Group_RESP, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(groupAccount)) {
            onRespFail("群号或者昵称不能为空", HttpDefine.Search_Group_RESP, Constant.Param_error_Code);
            return;
        }
        searchGroupListView.showSearchGroupListProgress();
        searchGroupListModel.loadData(groupAccount);
    }

    @Override
    public void onRespSucess(SearchGroupListResp searchGroupListResp) {
        searchGroupListView.hideSearchGroupListProgress();
        searchGroupListView.onLoadSucess(searchGroupListResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        searchGroupListView.hideSearchGroupListProgress();
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setErrorCode(errorCode);
        errorMsg.setRespCode(RespCode);
        searchGroupListView.onLoadFail(errorMsg);
    }
}
