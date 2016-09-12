package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.SearchFriendResp;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface SearchFriendView {
    void onLoadSucess(SearchFriendResp searchFriendResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showSearchFriendProgress();
    void hideSearchFriendProgress();
}
