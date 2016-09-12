package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.BindTeamHitResp;
import teams.xianlin.com.teamshit.PrensenterModel.BindTeamHitModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.BindTeamHitView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/3.
 */
public class BindTeamHitPresenter extends BasePresenter<BindTeamHitView> implements IBasePsenter<BindTeamHitResp> {

    private BindTeamHitView bindTeamHitView;

    private BindTeamHitModel bindTeamHitModel;

    public BindTeamHitPresenter(BindTeamHitView bindTeamHitView) {
        this.bindTeamHitView = bindTeamHitView;
        bindTeamHitModel = new BindTeamHitModel(this);
        attchView(bindTeamHitView);
    }

    public void LoadData(String uuid, int userFor, Context context) {
        if (!NetUtils.getinStance().isNetworkAvailable(context)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Bind_Team_Hit_RESP, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(uuid)) {
            onRespFail("扫描错误，请从新扫描", HttpDefine.Bind_Team_Hit_RESP, Constant.Param_error_Code);
        }
        bindTeamHitView.showBindTeamHitProgress();
        bindTeamHitModel.loadData(uuid, userFor);
    }

    @Override
    public void onRespSucess(BindTeamHitResp bindTeamHitResp) {
        bindTeamHitView.hideBindTeamHitProgress();
        bindTeamHitView.onLoadSucess(bindTeamHitResp);

    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        bindTeamHitView.hideBindTeamHitProgress();
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setRespCode(RespCode);
        bindTeamHitView.onLoadFail(errorMsg);
    }
}
