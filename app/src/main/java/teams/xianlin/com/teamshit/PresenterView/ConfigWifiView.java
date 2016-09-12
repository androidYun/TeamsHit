package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.CompleteUserInfoResp;
import teams.xianlin.com.teamshit.NetWorkResp.ConfigWifiResp;

/**
 * Created by Administrator on 2016/7/29.
 */
public interface ConfigWifiView {
    void onLoadSucess(ConfigWifiResp configWifiResp);

    void onLoadFail(ErrorMsg errorMsg);

    void showConfigWifiProgress();

    void hideConfigWifiProgress();
}
