package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.SearchFriendResp;
import teams.xianlin.com.teamshit.NetWorkResp.VerificationCodeResp;
import teams.xianlin.com.teamshit.Presenter.SearchFriendPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/9.
 */
public class SearchFriendModel {

    private SearchFriendPresenter searchFriendPresenter;

    public SearchFriendModel(SearchFriendPresenter searchFriendPresenter) {
        this.searchFriendPresenter = searchFriendPresenter;
    }

    public void loadData(String account) {

        Param AccountParam = new Param("Account", account);

        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Search_Friend_Url), new OkHttpClientManager.ResultCallback<SearchFriendResp>() {
            @Override
            public void onError(Request request, Exception e) {
                searchFriendPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Search_Friend_RESP, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(SearchFriendResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    searchFriendPresenter.onRespSucess(response);
                } else {
                    searchFriendPresenter.onRespFail(response.getMessage(), HttpDefine.Search_Friend_RESP, response.getCode());
                }
            }
        }, HttpDefine.Search_Friend_RESP, AccountParam);
    }
}
