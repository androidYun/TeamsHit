package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.NetWorkResp.EditTeamNameResp;
import teams.xianlin.com.teamshit.PrensenterModel.EditTeamNameModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.EditTeamNameView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/8.
 */
public class EditTeamNamePresenter extends BasePresenter<EditTeamNameView> implements IBasePsenter<EditTeamNameResp> {
    private EditTeamNameView editTeamNameView;

    private EditTeamNameModel editTeamNameModel;

    public EditTeamNamePresenter(EditTeamNameView editTeamNameView) {
        this.editTeamNameView = editTeamNameView;
        editTeamNameModel = new EditTeamNameModel(this);
    }


    public void loadData(Context mContext, String uuId, String NewDeviceName) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Edit_Team_Name_RESP, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(uuId)) {
            onRespFail("Uuid不能为空", HttpDefine.Edit_Team_Name_RESP, Constant.Param_error_Code);
            return;
        }
        if (StringUtils.isBlank(NewDeviceName)) {
            onRespFail("设备名不能为空", HttpDefine.Edit_Team_Name_RESP, Constant.Param_error_Code);
            return;
        }
        editTeamNameView.showEditTeamNameProgress();
        editTeamNameModel.loadData(uuId, NewDeviceName);
    }

    @Override
    public void onRespSucess(EditTeamNameResp editTeamNameResp) {
        editTeamNameView.hideEditTeamNameProgress();
        editTeamNameView.onLoadSucess(editTeamNameResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        editTeamNameView.hideEditTeamNameProgress();
        editTeamNameView.onLoadFail(creteErrorMsg(errorStr, RespCode,errorCode));
    }
}
