package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.PublishTakeResp;
import teams.xianlin.com.teamshit.Presenter.PublishTakePresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/29.
 */
public class PublishTakeModel {
    private PublishTakePresenter publishTakePresenter;

    public PublishTakeModel(PublishTakePresenter publishTakePresenter) {
        this.publishTakePresenter = publishTakePresenter;
    }

    public void loadData(String PhotoLists, String TakeContent) {
        Param takeContentParam = new Param("TakeContent", TakeContent);
        Param photoListsParam = new Param("PhotoLists", PhotoLists);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Friend_Circle_Publish_Take_Url), new OkHttpClientManager.ResultCallback<PublishTakeResp>() {
            @Override
            public void onError(Request request, Exception e) {

                publishTakePresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Publish_Friend_Circle_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(PublishTakeResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    publishTakePresenter.onRespSucess(response);
                } else {
                    publishTakePresenter.onRespFail(response.getMessage(), HttpDefine.Publish_Friend_Circle_Resp, response.getCode());
                }
            }
        }, HttpDefine.Publish_Friend_Circle_Resp, takeContentParam, photoListsParam);
    }
}
