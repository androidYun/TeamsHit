package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.OperateFriendResp;
import teams.xianlin.com.teamshit.PrensenterModel.OperateFriendModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.OperateFriendView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/7/19.
 */
public class OperateFriendPresenter extends BasePresenter<OperateFriendView> implements IBasePsenter<OperateFriendResp> {
    private OperateFriendView operateFriendView;

    private OperateFriendModel operateFriendModel;

    public OperateFriendPresenter(OperateFriendView operateFriendView) {
        this.operateFriendView = operateFriendView;
        operateFriendModel = new OperateFriendModel(this);
        attchView(operateFriendView);
    }

    public void loadData(long TargetId, String LeaveMsg, long ApplyId, int Type, Context context) {//  Type 1添加好友2、同意添加3、拒绝添加4、加入黑名单5、
        if (!NetUtils.getinStance().isNetworkAvailable(context)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Opearte_Friend_RESP, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(String.valueOf(TargetId))) {
            onRespFail("好友id不能为空", HttpDefine.Register_Resp, Constant.Param_error_Code);
            return;
        }
//        if (StringUtils.isBlank(ApplyId)) {//ApplyId只有同意好友 给拒绝好友的时候用的上
//            onRespFail("发起申请编号不能为空", HttpDefine.Opearte_Friend_RESP, Constant.Param_error_Code);
//            return;
//        }

        if (StringUtils.isBlank(Type)) {
            onRespFail("请验证码", HttpDefine.Register_Resp, Constant.Param_error_Code);
            return;
        }
        operateFriendModel.loadData(TargetId, LeaveMsg, ApplyId, Type);
    }

    @Override
    public void onRespSucess(OperateFriendResp operateFriendResp) {
        operateFriendView.onLoadSucess(operateFriendResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setErrorCode(errorCode);
        errorMsg.setRespCode(RespCode);
        operateFriendView.onLoadFail(errorMsg);
    }
}
