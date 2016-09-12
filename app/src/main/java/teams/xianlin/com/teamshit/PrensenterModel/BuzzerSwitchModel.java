package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.BuzzerSwitchResp;
import teams.xianlin.com.teamshit.NetWorkResp.IndicatorSwitchResp;
import teams.xianlin.com.teamshit.Presenter.BuzzerSwitchPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/5.
 */
public class BuzzerSwitchModel {
    private BuzzerSwitchPresenter buzzerSwitchPresenter;

    public BuzzerSwitchModel(BuzzerSwitchPresenter buzzerSwitchPresenter) {
        this.buzzerSwitchPresenter = buzzerSwitchPresenter;
    }

    public void loadData(int BuzzerState, String Uuid) {


        Param buzzerSwitchParam = new Param("BuzzerState", BuzzerState);
        Param UuidParam = new Param("Uuid", Uuid);

        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Buzzer_Switch_Url), new OkHttpClientManager.ResultCallback<BuzzerSwitchResp>() {
            @Override
            public void onError(Request request, Exception e) {
                buzzerSwitchPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Buzzer_Switch_RESP, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(BuzzerSwitchResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    buzzerSwitchPresenter.onRespSucess(response);
                } else {
                    buzzerSwitchPresenter.onRespFail(response.getMessage(), HttpDefine.Buzzer_Switch_RESP, response.getCode());
                }
            }
        }, HttpDefine.Buzzer_Switch_RESP,buzzerSwitchParam, UuidParam);
    }
}
