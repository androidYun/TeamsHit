package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.BindTeamHitResp;

/**
 * Created by Administrator on 2016/8/3.
 */
public interface BindTeamHitView {
    void onLoadSucess(BindTeamHitResp bindTeamHitResp);

    void onLoadFail(ErrorMsg errorMsg);

    void showBindTeamHitProgress();

    void hideBindTeamHitProgress();
}
