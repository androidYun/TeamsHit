package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.ConfigWifiResp;
import teams.xianlin.com.teamshit.PrensenterModel.ConfigWifiModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.ConfigWifiView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/7/29.
 */
public class ConfigWifiPresenter extends BasePresenter<ConfigWifiView> implements IBasePsenter<ConfigWifiResp> {
    private ConfigWifiView configWifiView;

    private ConfigWifiModel configWifiModel;

    public ConfigWifiPresenter(ConfigWifiView configWifiView) {
        this.configWifiView = configWifiView;
        configWifiModel = new ConfigWifiModel(this);
        attchView(configWifiView);
    }

    public void loadData(String ssid, int security, String key, int ip, Context context) {
        if (!NetUtils.getinStance().isNetworkAvailable(context)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Config_Wifi_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(ssid)) {
            onRespFail("请选择你要链接的wifi", HttpDefine.Complete_User_Infor_Resp, Constant.Param_error_Code);
        }
        if (StringUtils.isBlank(key)) {
            onRespFail("密码不能为空", HttpDefine.Complete_User_Infor_Resp, Constant.Param_error_Code);
            return;
        }
        configWifiView.showConfigWifiProgress();
        configWifiModel.loadData(ssid, security, key, ip);
    }

    @Override
    public void onRespSucess(ConfigWifiResp configWifiResp) {
        configWifiView.hideConfigWifiProgress();
        configWifiView.onLoadSucess(configWifiResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setRespCode(RespCode);
        configWifiView.onLoadFail(errorMsg);
        configWifiView.hideConfigWifiProgress();
    }
}
