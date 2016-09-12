package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.DeleteFriendCircleMessageResp;

/**
 * Created by Administrator on 2016/9/1.
 */
public interface DeleteFriendCircleMessageView {

    void onLoadSucess(DeleteFriendCircleMessageResp deleteFriendCircleMessgaeResp);

    void onLoadFail(ErrorMsg errorMsg);

    void showDeleteFriendMessageProgress();

    void hideDeleteFriendMessageProgress();
}
