package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetUserInforResp;
import teams.xianlin.com.teamshit.NetWorkResp.LoginResp;

/**
 * Created by Administrator on 2016/8/13.
 */
public interface GetUserInfoView {
    void onLoadSucess(GetUserInforResp getUserInforResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showGetUserInforProgress();
    void hideGetUserInforProgress();
}
