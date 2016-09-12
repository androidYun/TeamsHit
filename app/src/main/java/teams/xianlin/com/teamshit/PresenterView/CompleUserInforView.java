package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.CompleteUserInfoResp;

/**
 * Created by Administrator on 2016/7/14.
 */
public interface CompleUserInforView {
    void onLoadSucess(CompleteUserInfoResp completeUserInfoResp);

    void onLoadFail(ErrorMsg errorMsg);

    void showCompleUserInforProgress();

    void hideCompleUserInforProgress();
}
