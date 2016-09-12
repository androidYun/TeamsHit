package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.GetGroupDetailResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetGroupListResp;
import teams.xianlin.com.teamshit.Presenter.GetGroupDetailPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/9/7.
 */
public class GetGroupDetailModel {

    private GetGroupDetailPresenter getGroupDetailPresenter;

    public GetGroupDetailModel(GetGroupDetailPresenter getGroupDetailPresenter) {
        this.getGroupDetailPresenter = getGroupDetailPresenter;
    }
    public void loadData(String GroupId) {
        Param GroupIdParam = new Param("GroupId", GroupId);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Create_Group_Detail_Url), new OkHttpClientManager.ResultCallback<GetGroupDetailResp>() {
            @Override
            public void onError(Request request, Exception e) {
                getGroupDetailPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Get_Group_Detail_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(GetGroupDetailResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    getGroupDetailPresenter.onRespSucess(response);
                } else {
                    getGroupDetailPresenter.onRespFail(response.getMessage(), HttpDefine.Get_Group_Detail_Resp, response.getCode());
                }
            }
        }, HttpDefine.Get_Group_Detail_Resp,GroupIdParam);
    }
}
