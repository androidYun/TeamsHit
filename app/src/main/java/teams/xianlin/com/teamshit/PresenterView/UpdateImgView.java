package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.SearchGroupListResp;
import teams.xianlin.com.teamshit.NetWorkResp.UpdateImageResp;

/**
 * Created by Administrator on 2016/8/16.
 */
public interface UpdateImgView {
    void onLoadSucess(UpdateImageResp updateImageResp);
    void onLoadFail(ErrorMsg errorMsg);
    void showUpdateImgProgress();
    void hideUpdateImgProgress();
}
