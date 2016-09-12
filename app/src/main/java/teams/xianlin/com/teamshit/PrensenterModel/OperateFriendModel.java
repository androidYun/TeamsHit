package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.LoginResp;
import teams.xianlin.com.teamshit.NetWorkResp.OperateFriendResp;
import teams.xianlin.com.teamshit.Presenter.OperateFriendPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/7/19.
 */
public class OperateFriendModel {
    private OperateFriendPresenter operateFriendPresenter;

    public OperateFriendModel(OperateFriendPresenter operateFriendPresenter) {
        this.operateFriendPresenter = operateFriendPresenter;
    }

    public void loadData(long TargetId, String LeaveMsg, long ApplyId, int Type) {
        Param targetIdParam = new Param("TargetId", TargetId);
        Param leaveMsgParam = new Param("LeaveMsg", LeaveMsg);
        Param applyIDParam = new Param("ApplyId", ApplyId);
        Param typeParam = new Param("Type", Type);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Opeartion_Friend_List_Url), new <LoginResp>OkHttpClientManager.ResultCallback<OperateFriendResp>() {
            @Override
            public void onError(Request request, Exception e) {
                operateFriendPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Opearte_Friend_RESP, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(OperateFriendResp operateFriendResp) {
                if (operateFriendResp.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    operateFriendPresenter.onRespSucess(operateFriendResp);
                } else {
                    operateFriendPresenter.onRespFail(operateFriendResp.getMessage(), HttpDefine.Opearte_Friend_RESP, operateFriendResp.getCode());
                }

            }
        }, HttpDefine.Opearte_Friend_RESP, targetIdParam, leaveMsgParam, applyIDParam, typeParam);
    }
}
