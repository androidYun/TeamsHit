package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.EditTeamNameResp;

/**
 * Created by Administrator on 2016/8/8.
 */
public interface EditTeamNameView {
    void onLoadSucess(EditTeamNameResp editTeamNameResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showEditTeamNameProgress();
    void hideEditTeamNameProgress();
}
