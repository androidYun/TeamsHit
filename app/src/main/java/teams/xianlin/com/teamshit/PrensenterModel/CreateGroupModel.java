package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.BaseInfor.HttpSeviceClass;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.CreateGroupResp;
import teams.xianlin.com.teamshit.Presenter.CreateGroupPresenter;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/9/6.
 */
public class CreateGroupModel {

    private CreateGroupPresenter createGroupPresenter;

    public CreateGroupModel(CreateGroupPresenter createGroupPresenter) {
        this.createGroupPresenter = createGroupPresenter;
    }

    public void loadData(String GroupName, String PortraitUri, int GroupType, int VerificationType, String MembersId, String Introduce) {
        Param GroupNameParam = new Param("GroupName", GroupName);
        Param PortraitUriParam = new Param("PortraitUri", PortraitUri);
        Param GroupTypeParam = new Param("GroupType", GroupType);
        Param VerificationTypeParam = new Param("VerificationType", VerificationType);
        Param MembersIdParam = new Param("MembersId", MembersId);
        Param IntroduceParam = new Param("Introduce", Introduce);
        OkHttpClientManager.getInstance().postAsyn(TeamHitContext.getInstance().getTokenUrl(HttpSeviceClass.Create_Group_Url), new OkHttpClientManager.ResultCallback<CreateGroupResp>() {
            @Override
            public void onError(Request request, Exception e) {
                createGroupPresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Create_Group_Resp, Constant.Param_error_Code);
            }

            @Override
            public void onResponse(CreateGroupResp response) {
                if (response.getCode() == HttpDefine.RESPONSE_SUCCESS) {
                    createGroupPresenter.onRespSucess(response);
                } else {
                    createGroupPresenter.onRespFail(response.getMessage(), HttpDefine.Create_Group_Resp, response.getCode());
                }
            }
        }, HttpDefine.Create_Group_Resp, GroupNameParam, PortraitUriParam, GroupTypeParam, VerificationTypeParam, MembersIdParam, IntroduceParam);
    }
}
