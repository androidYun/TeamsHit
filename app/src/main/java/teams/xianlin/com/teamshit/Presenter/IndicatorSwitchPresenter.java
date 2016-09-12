package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.IndicatorSwitchResp;
import teams.xianlin.com.teamshit.PrensenterModel.IndicatorSwitchModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.IndicatorSwitchView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/5.
 */
public class IndicatorSwitchPresenter extends BasePresenter<IndicatorSwitchView> implements IBasePsenter<IndicatorSwitchResp> {

    private IndicatorSwitchView indicatorSwitchView;

    private IndicatorSwitchModel indicatorSwitchModel;

    public IndicatorSwitchPresenter(IndicatorSwitchView indicatorSwitchView) {
        this.indicatorSwitchView = indicatorSwitchView;
        indicatorSwitchModel = new IndicatorSwitchModel(this);
        attchView(indicatorSwitchView);
    }

    public void loadData(int indicatorSwitch, String Uuid, Context mContext) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Indicator_Switch_RESP, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(Uuid)) {
            onRespFail("发生错误，请重新请求", HttpDefine.Bind_Team_Hit_RESP, Constant.Param_error_Code);
            return;
        }
        indicatorSwitchView.showIndicatorSwitchProgress();
        indicatorSwitchModel.loadData(indicatorSwitch, Uuid);
    }

    @Override
    public void onRespSucess(IndicatorSwitchResp indicatorSwitchResp) {
        indicatorSwitchView.hideIndicatorSwitchProgress();
        indicatorSwitchView.onLoadSucess(indicatorSwitchResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        indicatorSwitchView.hideIndicatorSwitchProgress();
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setRespCode(RespCode);
        indicatorSwitchView.onLoadFail(errorMsg);
    }
}
