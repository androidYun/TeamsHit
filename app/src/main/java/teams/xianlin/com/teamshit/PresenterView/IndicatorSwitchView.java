package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetGroupListResp;
import teams.xianlin.com.teamshit.NetWorkResp.IndicatorSwitchResp;

/**
 * Created by Administrator on 2016/8/5.
 */
public interface IndicatorSwitchView {
    void onLoadSucess(IndicatorSwitchResp indicatorSwitchResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showIndicatorSwitchProgress();
    void hideIndicatorSwitchProgress();
}
