package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.BuzzerSwitchResp;
import teams.xianlin.com.teamshit.PrensenterModel.BuzzerSwitchModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.BuzzerSwitchView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/5.
 */
public class BuzzerSwitchPresenter extends BasePresenter<BuzzerSwitchView> implements IBasePsenter<BuzzerSwitchResp> {
    private BuzzerSwitchView buzzerSwitchView;

    private BuzzerSwitchModel buzzerSwitchModel;

    public BuzzerSwitchPresenter(BuzzerSwitchView buzzerSwitchView) {
        this.buzzerSwitchView = buzzerSwitchView;
        buzzerSwitchModel = new BuzzerSwitchModel(this);
        attchView(buzzerSwitchView);
    }

    public void loadData(int buzzerSwitch, String Uuid, Context mContext) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Buzzer_Switch_RESP, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(Uuid)) {
            onRespFail("发生错误，请重新请求", HttpDefine.Bind_Team_Hit_RESP, Constant.Param_error_Code);
            return;
        }
        buzzerSwitchView.showBuzzerSwitchProgress();
        buzzerSwitchModel.loadData(buzzerSwitch,Uuid);
    }

    @Override
    public void onRespSucess(BuzzerSwitchResp buzzerSwitchResp) {
        buzzerSwitchView.hideBuzzerSwitchProgress();
        buzzerSwitchView.onLoadSucess(buzzerSwitchResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        buzzerSwitchView.hideBuzzerSwitchProgress();
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setRespCode(RespCode);
        buzzerSwitchView.onLoadFail(errorMsg);
    }
}
