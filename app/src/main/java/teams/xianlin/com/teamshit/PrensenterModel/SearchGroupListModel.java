package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.SearchGroupListResp;
import teams.xianlin.com.teamshit.NetWorkResp.VerificationCodeResp;
import teams.xianlin.com.teamshit.Presenter.SearchGroupListPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/10.
 */
public class SearchGroupListModel {

    private SearchGroupListPresenter searchGroupListPresenter;

    public SearchGroupListModel(SearchGroupListPresenter searchGroupListPresenter) {
        this.searchGroupListPresenter = searchGroupListPresenter;
    }

    public void loadData(String GroupAccount) {

        Param groupAccountParam = new Param("GroupAccount", GroupAccount);

        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Search_GroupS_Url), new OkHttpClientManager.ResultCallback<SearchGroupListResp>() {
            @Override
            public void onError(Request request, Exception e) {
                searchGroupListPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Search_Group_RESP, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(SearchGroupListResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    searchGroupListPresenter.onRespSucess(response);
                } else {
                    searchGroupListPresenter.onRespFail(response.getMessage(), HttpDefine.Search_Group_RESP, response.getCode());
                }
            }
        }, HttpDefine.Search_Group_RESP,groupAccountParam);
    }
}
