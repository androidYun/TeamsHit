package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.NetWorkResp.PublishTakeResp;
import teams.xianlin.com.teamshit.PrensenterModel.PublishTakeModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.PublishTakeView;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/29.
 */
public class PublishTakePresenter extends BasePresenter<PublishTakeView> implements IBasePsenter<PublishTakeResp> {

    private PublishTakeView publishTakeView;

    private PublishTakeModel publishTakeModel;

    public PublishTakePresenter(PublishTakeView publishTakeView) {
        this.publishTakeView = publishTakeView;
        publishTakeModel = new PublishTakeModel(this);
    }

    public void loadData(Context mContext, String TakeContent, String PhotoLists) {
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Publish_Friend_Circle_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        if (StringUtils.isBlank(TakeContent)) {
            onRespFail("请输入内容", HttpDefine.Register_Resp, Constant.Param_error_Code);
            return;
        }
        publishTakeView.showPublishTakeProgress();
        publishTakeModel.loadData(PhotoLists, TakeContent);
    }

    @Override
    public void onRespSucess(PublishTakeResp publishTakeResp) {
        publishTakeView.hidePublishTakeProgress();
        publishTakeView.onLoadSucess(publishTakeResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        publishTakeView.hidePublishTakeProgress();
        publishTakeView.onLoadFail(creteErrorMsg(errorStr, RespCode, errorCode));
    }
}
