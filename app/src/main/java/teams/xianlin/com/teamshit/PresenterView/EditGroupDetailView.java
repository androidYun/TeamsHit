package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.NetWorkResp.BackGroupResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditGamePeopleResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditGroupNameResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditGroupTypeResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditGroupVerifitionResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditLeastCoinResp;

/**
 * Created by Administrator on 2016/9/12.
 */
public interface EditGroupDetailView {
    void onRespSucess(EditGroupNameResp editGroupNameResp);

    void onRespSucess(EditGroupTypeResp editGroupTypeResp);

    void onRespSucess(EditGroupVerifitionResp editGroupVerifitionResp);

    void onRespSucess(EditGamePeopleResp editGamePeopleResp);

    void onRespSucess(EditLeastCoinResp editLeastCoinResp);

    void onRespSucess(BackGroupResp backGroupResp);

    void onRespFail(String errorStr, int RespCode, int errorCode);
}
