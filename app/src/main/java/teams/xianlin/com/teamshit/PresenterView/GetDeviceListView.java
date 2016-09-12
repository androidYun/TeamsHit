package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetDeviceResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendsListResp;

/**
 * Created by Administrator on 2016/8/2.
 */
public interface GetDeviceListView {
    void onLoadSucess(GetDeviceResp getDeviceResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showGetDevicesProgress();
    void hideGetDevicesProgress();
}
