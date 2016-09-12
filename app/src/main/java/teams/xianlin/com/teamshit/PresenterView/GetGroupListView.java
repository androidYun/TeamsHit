package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetGroupListResp;

/**
 * Created by Administrator on 2016/7/16.
 */
public interface GetGroupListView {
    void onLoadSucess(GetGroupListResp getGroupListResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showGeGroupssProgress();
    void hideGetGroupsProgress();
}
