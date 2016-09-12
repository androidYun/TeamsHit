package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.SearchGroupListResp;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface SearchGroupListView {
    void onLoadSucess(SearchGroupListResp searchGroupListResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showSearchGroupListProgress();
    void hideSearchGroupListProgress();
}
