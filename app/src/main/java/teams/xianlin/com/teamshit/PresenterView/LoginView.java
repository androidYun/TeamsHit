package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.LoginResp;

/**
 * Created by Administrator on 2016/7/6.
 */
public interface LoginView {
    void onLoadSucess(LoginResp loginResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showLoginProgress();
    void hideLoginProgress();
}
