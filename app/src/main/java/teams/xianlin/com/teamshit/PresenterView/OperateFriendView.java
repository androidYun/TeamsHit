package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.OperateFriendResp;

/**
 * Created by Administrator on 2016/7/19.
 */
public interface OperateFriendView {
    void onLoadSucess(OperateFriendResp operateFriendResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showOperateFriendProgress();
    void hideOperateFriendProgress();
}
