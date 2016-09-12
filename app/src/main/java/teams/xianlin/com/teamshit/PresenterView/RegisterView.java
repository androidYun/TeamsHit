package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.RegisterResp;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface RegisterView {
    void onLoadSucess(RegisterResp registerResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showRegisterProgress();
    void hideRegsiterProgress();
}
