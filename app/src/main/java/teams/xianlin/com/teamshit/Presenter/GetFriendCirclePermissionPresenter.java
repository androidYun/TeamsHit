package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCirclePermissionResp;
import teams.xianlin.com.teamshit.PrensenterModel.GetFriendCirclePermissionModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.GetFriendCirclePermissionView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/19.
 */
public class GetFriendCirclePermissionPresenter extends BasePresenter<GetFriendCirclePermissionView> implements IBasePsenter<GetFriendCirclePermissionResp> {

    private GetFriendCirclePermissionView getFriendCirclePermissionView;

    private GetFriendCirclePermissionModel getFriendCirclePermissionModel;

    public GetFriendCirclePermissionPresenter(GetFriendCirclePermissionView getFriendCirclePermissionView) {
        this.getFriendCirclePermissionView = getFriendCirclePermissionView;
        getFriendCirclePermissionModel = new GetFriendCirclePermissionModel(this);
        attchView(getFriendCirclePermissionView);
    }

    public void loadData(Context context, long targetId) {
        if (!NetUtils.getinStance().isNetworkAvailable(context)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Get_Friend_Circle_Permission_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(String.valueOf(targetId))) {
            onRespFail("请求出错", HttpDefine.Get_Friend_Circle_Permission_Resp, Constant.Param_error_Code);
            return;
        }
        getFriendCirclePermissionView.showFriendCirclePermissionProgress();
        getFriendCirclePermissionModel.loadData(targetId);
    }

    @Override
    public void onRespSucess(GetFriendCirclePermissionResp getFriendCirclePermissionResp) {
        getFriendCirclePermissionView.hideFriendCirclePermissionProgress();
        getFriendCirclePermissionView.onLoadSucess(getFriendCirclePermissionResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        getFriendCirclePermissionView.hideFriendCirclePermissionProgress();
        getFriendCirclePermissionView.onLoadFail(creteErrorMsg(errorStr, RespCode, errorCode));
    }
}
