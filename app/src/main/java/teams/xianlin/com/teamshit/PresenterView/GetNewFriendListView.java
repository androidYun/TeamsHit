package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetNewFriendListResp;

/**
 * Created by Administrator on 2016/8/12.
 */
public interface GetNewFriendListView {
    void onLoadSucess(GetNewFriendListResp getnewFriendListResp);

    void onLoadFail(ErrorMsg errorMsg);

    void showGetNewFriendListProgress();

    void hideGetNewFriendListProgress();
}
