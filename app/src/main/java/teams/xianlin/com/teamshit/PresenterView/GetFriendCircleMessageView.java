package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleMessageResp;

/**
 * Created by Administrator on 2016/9/1.
 */
public interface GetFriendCircleMessageView {
    void onLoadSucess(GetFriendCircleMessageResp getFriendCircleMessgeResp);


    void onLoadFail(ErrorMsg errorMsg);

    void showGetFriendCircleMessageProgress();

    void hideGetFriendCircleMessageProgress();
}
