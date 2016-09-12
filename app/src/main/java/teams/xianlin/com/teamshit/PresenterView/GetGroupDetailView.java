package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetGroupDetailResp;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface GetGroupDetailView {
    void onLoadSucess(GetGroupDetailResp getGroupDetailResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showGetGroupDetailProgress();
    void hideGetGroupDetailProgress();
}
