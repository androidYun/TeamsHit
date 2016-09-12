package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkRequest.ConfigWifi;
import teams.xianlin.com.teamshit.NetWorkResp.CompleteUserInfoResp;
import teams.xianlin.com.teamshit.NetWorkResp.ConfigWifiResp;
import teams.xianlin.com.teamshit.Presenter.ConfigWifiPresenter;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/7/29.
 */
public class ConfigWifiModel {

    private ConfigWifiPresenter configWifiPresenter;

    public ConfigWifiModel(ConfigWifiPresenter configWifiPresenter) {
        this.configWifiPresenter = configWifiPresenter;
    }

    public void loadData(String ssid, int security, String key, int ip) {
        ConfigWifi configWifi = new ConfigWifi();
        configWifi.setSsid(ssid);
        configWifi.setSecurity(security);
        configWifi.setKey(key);
        configWifi.setIp(ip);
        OkHttpClientManager.getInstance().postAsyn(HttpSeviceClass.Config_Wifi_Url, new OkHttpClientManager.ResultCallback<ConfigWifiResp>() {
            @Override
            public void onError(Request request, Exception e) {
                configWifiPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Config_Wifi_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(ConfigWifiResp response) {
                if (response.getSuccess() == 0) {
                    configWifiPresenter.onRespSucess(response);
                } else {
                    configWifiPresenter.onRespFail("请求失败", HttpDefine.Config_Wifi_Resp, HttpDefine.Config_Wifi_Resp);
                }
            }
        },HttpDefine.Config_Wifi_Resp, configWifi);
    }
}
