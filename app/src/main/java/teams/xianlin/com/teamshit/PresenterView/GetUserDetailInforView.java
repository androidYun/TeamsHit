package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetUserDeatilInforResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetUserInforResp;

/**
 * Created by Administrator on 2016/8/15.
 */
public interface GetUserDetailInforView {
    void onLoadSucess(GetUserDeatilInforResp getUserDeatilInforResp);

    void onLoadFail(ErrorMsg errorMsg);

    void showGetUserInforProgress();

    void hideGetUserInforProgress();
}
