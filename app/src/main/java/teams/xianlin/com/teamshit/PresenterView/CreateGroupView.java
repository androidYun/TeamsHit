package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.CreateGroupResp;

/**
 * Created by Administrator on 2016/9/6.
 */
public interface CreateGroupView {
    void onLoadSucess(CreateGroupResp createGroupResp);

    void onLoadFail(ErrorMsg errorMsg);

    void showCreateGroupProgress();

    void hideCreateGroupProgress();
}
