package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.CompleteUserInfoResp;
import teams.xianlin.com.teamshit.Presenter.CompleUserInforPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/7/14.
 */
public class CompleteUserInfoModel {
    private CompleUserInforPresenter compleUserInforPresenter;

    public CompleteUserInfoModel(CompleUserInforPresenter compleUserInforPresenter) {
        this.compleUserInforPresenter = compleUserInforPresenter;
    }

    public void loadData(String IconUrl, String NickName, String UserName, String Gender,String Province, String City, String BirthDate) {
        Param iconUrlParam = new Param("IconUrl", IconUrl);
        Param niackNameParam = new Param("NickName", NickName);
        Param userNameParam = new Param("UserName", UserName);
        Param GenderParam = new Param("Gender", Gender);
        Param CityParam = new Param("City", City);
        Param birthDateParam = new Param("BirthDate", BirthDate);
        Param ProvinceParam = new Param("Province", Province);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Comlete_User_Infor_Url), new OkHttpClientManager.ResultCallback<CompleteUserInfoResp>() {
            @Override
            public void onError(Request request, Exception e) {
                compleUserInforPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Complete_User_Infor_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(CompleteUserInfoResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    compleUserInforPresenter.onRespSucess(response);
                } else {
                    compleUserInforPresenter.onRespFail(response.getMessage(), HttpDefine.Complete_User_Infor_Resp, response.getCode());
                }
            }
        }, HttpDefine.Complete_User_Infor_Resp,iconUrlParam, niackNameParam, userNameParam, GenderParam, ProvinceParam,CityParam, birthDateParam);
    }
}
