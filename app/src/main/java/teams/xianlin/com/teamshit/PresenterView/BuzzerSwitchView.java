package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.BuzzerSwitchResp;

/**
 * Created by Administrator on 2016/8/5.
 */
public interface BuzzerSwitchView {
    void onLoadSucess(BuzzerSwitchResp buzzerSwitchResp);

    void onLoadFail(ErrorMsg errorMsg);

    void showBuzzerSwitchProgress();

    void hideBuzzerSwitchProgress();
}
