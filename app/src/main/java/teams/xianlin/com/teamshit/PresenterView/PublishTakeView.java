package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.PublishTakeResp;

/**
 * Created by Administrator on 2016/8/29.
 */
public interface PublishTakeView {
    void onLoadSucess(PublishTakeResp publishTakeResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showPublishTakeProgress();
    void hidePublishTakeProgress();
}
