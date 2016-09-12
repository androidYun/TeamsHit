package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCirclePermissionResp;
import teams.xianlin.com.teamshit.NetWorkResp.SetDisPlayNameResp;

/**
 * Created by Administrator on 2016/8/19.
 */
public interface GetFriendCirclePermissionView {
    void onLoadSucess(GetFriendCirclePermissionResp  getFriendCirclePermissionResp);

    void onLoadFail(ErrorMsg errorMsg);

    void showFriendCirclePermissionProgress();

    void hideFriendCirclePermissionProgress();
}
