package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendsListResp;

/**
 * Created by Administrator on 2016/7/16.
 */
public interface GetFriendListView {
    void onLoadSucess(GetFriendsListResp loginResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showGetFriendsProgress();
    void hideGetFriendsProgress();
}
