package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.IndicatorSwitchResp;
import teams.xianlin.com.teamshit.NetWorkResp.VerificationCodeResp;
import teams.xianlin.com.teamshit.Presenter.IndicatorSwitchPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/5.
 */
public class IndicatorSwitchModel {

    private IndicatorSwitchPresenter indicatorSwitchPresenter;

    public IndicatorSwitchModel(IndicatorSwitchPresenter indicatorSwitchPresenter) {
        this.indicatorSwitchPresenter = indicatorSwitchPresenter;
    }

    public void loadData(int indicatorSwitch, String Uuid) {

        Param indicatorStateParam = new Param("IndicatorState", indicatorSwitch);

        Param UuidParam = new Param("Uuid", Uuid);

        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Indicator_Switch_URL), new OkHttpClientManager.ResultCallback<IndicatorSwitchResp>() {
            @Override
            public void onError(Request request, Exception e) {
                indicatorSwitchPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Indicator_Switch_RESP, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(IndicatorSwitchResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    indicatorSwitchPresenter.onRespSucess(response);
                } else {
                    indicatorSwitchPresenter.onRespFail(response.getMessage(), HttpDefine.Indicator_Switch_RESP, response.getCode());
                }
            }
        },HttpDefine.Indicator_Switch_RESP, indicatorStateParam, UuidParam);
    }
}
