package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.NetWorkResp.CreateGroupResp;
import teams.xianlin.com.teamshit.PrensenterModel.CreateGroupModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.CreateGroupView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.Utils.TimeUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/9/6.
 */
public class CreateGroupPresenter extends BasePresenter<CreateGroupView> implements IBasePsenter<CreateGroupResp> {
    private CreateGroupView createGroupView;

    private CreateGroupModel createGroupModel;

    public CreateGroupPresenter(CreateGroupView createGroupView) {
        this.createGroupView = createGroupView;
        createGroupModel = new CreateGroupModel(this);
    }

    public void loadData(Context mContext, String GroupName, String PortraitUri, int GroupType, int VerificationType, String MembersId, String Introduce) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Edit_Team_Name_RESP, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(GroupName)) {
            onRespFail("群名不能为空", HttpDefine.Complete_User_Infor_Resp, Constant.Param_error_Code);
        }
        if (StringUtils.isBlank(Introduce)) {
            Introduce = "本群创建于" + TimeUtils.getInstance().getCuurentTime();
        }
        createGroupView.showCreateGroupProgress();
        createGroupModel.loadData(GroupName, PortraitUri, GroupType, VerificationType, MembersId,Introduce);
    }

    @Override
    public void onRespSucess(CreateGroupResp createGroupResp) {
        createGroupView.hideCreateGroupProgress();
        createGroupView.onLoadSucess(createGroupResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        createGroupView.hideCreateGroupProgress();
        createGroupView.onLoadFail(creteErrorMsg(errorStr, RespCode, errorCode));
    }
}
