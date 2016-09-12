package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.BindTeamHitResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleListResp;

/**
 * Created by Administrator on 2016/8/26.
 */
public interface GetFriendCircleListView {
    void onLoadSucess(GetFriendCircleListResp getFriendCircleListResp);

    void onLoadFail(ErrorMsg errorMsg);

    void showGetFriendCircleListProgress();

    void hideGetFriendCircleListProgress();
}
