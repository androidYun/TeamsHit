package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.NetWorkResp.SetUserPermissionResp;
import teams.xianlin.com.teamshit.PrensenterModel.SetUserPermissionModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.SetUserPermissionView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/19.
 */
public class SetUserPermissionPresenter extends BasePresenter<SetUserPermissionView> implements IBasePsenter<SetUserPermissionResp> {

    private SetUserPermissionView setUserPermissionView;

    private SetUserPermissionModel setUserPermissionModel;

    public SetUserPermissionPresenter(SetUserPermissionView setUserPermissionView) {
        this.setUserPermissionView = setUserPermissionView;
        setUserPermissionModel = new SetUserPermissionModel(this);
        attchView(setUserPermissionView);
    }

    public void loadData(Context context, long TargetId, int Permission) {
        if (!NetUtils.getinStance().isNetworkAvailable(context)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Set_User_Permission_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(String.valueOf(TargetId))) {
            onRespFail("请求出错", HttpDefine.Set_User_Permission_Resp, Constant.Param_error_Code);
            return;
        }
        setUserPermissionView.showSetUserPermissionProgress();
        setUserPermissionModel.loadData(TargetId, Permission);
    }

    @Override
    public void onRespSucess(SetUserPermissionResp setUserPermissionResp) {
        setUserPermissionView.hideSetUserPermissionProgress();
        setUserPermissionView.onLoadSucess(setUserPermissionResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        setUserPermissionView.hideSetUserPermissionProgress();
        setUserPermissionView.onLoadFail(creteErrorMsg(errorStr, RespCode, errorCode));
    }
}
