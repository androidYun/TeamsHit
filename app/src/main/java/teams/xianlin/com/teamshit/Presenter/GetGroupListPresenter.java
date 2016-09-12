package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.GetGroupListResp;
import teams.xianlin.com.teamshit.PrensenterModel.GetGroupListModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.GetGroupListView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/7/16.
 */
public class GetGroupListPresenter extends BasePresenter<GetGroupListView> implements IBasePsenter<GetGroupListResp> {
    private GetGroupListView getGroupListView;

    private GetGroupListModel getGroupListModel;

    public GetGroupListPresenter(GetGroupListView getGroupListView) {
        this.getGroupListView = getGroupListView;
        getGroupListModel = new GetGroupListModel(this);
        attchView(getGroupListView);
    }

    public void loadData(String token, Context mContext) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.GET_Grop_LIST_RESP, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(token)) {
            onRespFail("Token不能为空，请重新登录", HttpDefine.GET_Grop_LIST_RESP, Constant.Param_error_Code);
            return;
        }
        getGroupListModel.loadData(token);
    }

    @Override
    public void onRespSucess(GetGroupListResp getFriendsListResp) {
        getGroupListView.onLoadSucess(getFriendsListResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setErrorMsg(errorStr);
        errorMsg.setErrorCode(errorCode);
        errorMsg.setRespCode(RespCode);
        getGroupListView.onLoadFail(errorMsg);
    }
}
