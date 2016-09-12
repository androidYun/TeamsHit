package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.NetWorkResp.GetGroupDetailResp;
import teams.xianlin.com.teamshit.PrensenterModel.GetGroupDetailModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.GetGroupDetailView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/9/7.
 */
public class GetGroupDetailPresenter extends BasePresenter<GetGroupDetailView> implements IBasePsenter<GetGroupDetailResp> {

    private GetGroupDetailView getGroupDetailView;

    private GetGroupDetailModel getGroupDetailModel;

    public GetGroupDetailPresenter(GetGroupDetailView getGroupDetailView) {
        this.getGroupDetailView = getGroupDetailView;
        getGroupDetailModel = new GetGroupDetailModel(this);
    }

    public void loadData(Context mContext, String GroupId) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Get_Group_Detail_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(GroupId)) {
            onRespFail("群Id不能为空", HttpDefine.Get_Group_Detail_Resp, Constant.Param_error_Code);
            return;
        }
        getGroupDetailView.showGetGroupDetailProgress();
        getGroupDetailModel.loadData(GroupId);
    }

    @Override
    public void onRespSucess(GetGroupDetailResp getGroupDetailResp) {
        getGroupDetailView.hideGetGroupDetailProgress();
        getGroupDetailView.onLoadSucess(getGroupDetailResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        getGroupDetailView.hideGetGroupDetailProgress();
        getGroupDetailView.onLoadFail(creteErrorMsg(errorStr, RespCode, errorCode));
    }
}
