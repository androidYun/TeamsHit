package teams.xianlin.com.teamshit.Presenter;

import android.content.Context;

import java.util.List;

import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.NetWorkResp.UpdateImageResp;
import teams.xianlin.com.teamshit.PrensenterModel.UpdateImgeModel;
import teams.xianlin.com.teamshit.Presenter.Base.BasePresenter;
import teams.xianlin.com.teamshit.Presenter.Base.IBasePsenter;
import teams.xianlin.com.teamshit.PresenterView.UpdateImgView;
import teams.xianlin.com.teamshit.httpService.NetUtils;

/**
 * Created by Administrator on 2016/8/16.
 */
public class UpdateImagePresenter extends BasePresenter<UpdateImgView> implements IBasePsenter<UpdateImageResp> {
    private UpdateImgView updateImgView;

    private UpdateImgeModel updateImgeModel;

    public UpdateImagePresenter(UpdateImgView updateImgView) {
        this.updateImgView = updateImgView;
        updateImgeModel = new UpdateImgeModel(this);
    }

    public void loadData(Context mContext, String imgPath, int saveType) {//saveType图片上传类型 上传头像是1   发表说说是2
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Update_Image_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        updateImgView.showUpdateImgProgress();
        updateImgeModel.loadData(imgPath, saveType);
    }

    public void loadData(Context mContext, List<String> imgPath, int saveType) {//saveType图片上传类型 上传头像是1   发表说说是2
        if (!NetUtils.getinStance().isNetworkAvailable(mContext)) {
            onRespFail(Constant.Not_NetWork_error, HttpDefine.Update_Image_Resp, Constant.Not_NetWork_error_Code);
            return;
        }
        updateImgView.showUpdateImgProgress();
        updateImgeModel.loadDatas(imgPath, saveType);
    }

    @Override
    public void onRespSucess(UpdateImageResp updateImageResp) {
        updateImgView.hideUpdateImgProgress();
        updateImgView.onLoadSucess(updateImageResp);
    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {
        updateImgView.hideUpdateImgProgress();
        updateImgView.onLoadFail(creteErrorMsg(errorStr, RespCode, errorCode));
    }
}
