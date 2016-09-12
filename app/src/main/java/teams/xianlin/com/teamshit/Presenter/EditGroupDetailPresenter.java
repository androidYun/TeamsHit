package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.Activity.GroupDetailActivity;
import teams.xianlin.com.teamshit.NetWorkResp.BackGroupResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditGamePeopleResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditGroupNameResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditGroupTypeResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditGroupVerifitionResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditLeastCoinResp;
import teams.xianlin.com.teamshit.Presenter.Base.IBaseGroupDetailPresenter;
import teams.xianlin.com.teamshit.PresenterView.EditGroupDetailView;

/**
 * Created by Administrator on 2016/9/12.
 */

public class EditGroupDetailPresenter extends GroupDetailActivity implements IBaseGroupDetailPresenter {

    private EditGroupDetailView editGroupDetailView;

    public EditGroupDetailPresenter(EditGroupDetailView editGroupDetailView) {
        this.editGroupDetailView = editGroupDetailView;
    }

    private void editGroupName(Context mContext, String groupName) {

    }

    private void editGroupType(Context mContext,int GroupType) {

    }

    private void editGroupVerifition(Context mContext,int GroupVerifition) {

    }

    private void editGamePeople(Context mContext,int GamePeople) {

    }

    private void editLeastCoin(Context mContext ,int leastCoin) {

    }

    private void backGroup(Context mContext) {

    }

    @Override
    public void onRespSucess(EditGroupNameResp editGroupNameResp) {

    }

    @Override
    public void onRespSucess(EditGroupTypeResp editGroupTypeResp) {

    }

    @Override
    public void onRespSucess(EditGroupVerifitionResp editGroupVerifitionResp) {

    }

    @Override
    public void onRespSucess(EditGamePeopleResp editGamePeopleResp) {

    }

    @Override
    public void onRespSucess(EditLeastCoinResp editLeastCoinResp) {

    }

    @Override
    public void onRespSucess(BackGroupResp backGroupResp) {

    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {

    }
}
