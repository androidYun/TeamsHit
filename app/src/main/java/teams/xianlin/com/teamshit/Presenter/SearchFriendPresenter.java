package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.SearchFriendResp;
import teams.xianlin.com.teamshit.PrensenterModel.SearchFriendModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.SearchFriendView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/9.
 */
public class SearchFriendPresenter extends BasePresenter<SearchFriendView> implements IBasePsenter<SearchFriendResp> {
    private SearchFriendView searchFriendView;

    private SearchFriendModel searchFriendModel;


    public SearchFriendPresenter(SearchFriendView searchFriendView) {
        this.searchFriendView = searchFriendView;
        searchFriendModel = new SearchFriendModel(this);
        attchView(searchFriendView);
    }

    public void loadData(Context mContext, String Account) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Search_Friend_RESP, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(Account)) {
            onRespFail("账号不能为空", HttpDefine.Search_Friend_RESP, Constant.Param_error_Code);
        }
        searchFriendView.showSearchFriendProgress();
        searchFriendModel.loadData(Account);
    }

    @Override
    public void onRespSucess(SearchFriendResp searchFriendResp) {
        searchFriendView.hideSearchFriendProgress();
        searchFriendView.onLoadSucess(searchFriendResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        searchFriendView.hideSearchFriendProgress();
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setRespCode(RespCode);
        searchFriendView.onLoadFail(errorMsg);
    }
}
