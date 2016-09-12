package teams.xianlin.com.teamshit.PrensenterModel;

import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import teams.xianlin.com.teamshit.Activity.CompleteUserInforActivity;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.Param;
import teams.xianlin.com.teamshit.NetWorkResp.UpdateImageResp;
import teams.xianlin.com.teamshit.Presenter.UpdateImagePresenter;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;

/**
 * Created by Administrator on 2016/8/16.
 */
public class UpdateImgeModel {

    UpdateImagePresenter updateImagePresenter;

    public UpdateImgeModel(UpdateImagePresenter updateImagePresenter) {
        this.updateImagePresenter = updateImagePresenter;
    }

    public void loadData(String imgPath, int savetype) {
        String url = Constant.UpImageUrl + savetype;
        try {
            OkHttpClientManager.postAsyn(url, new OkHttpClientManager.ResultCallback<UpdateImageResp>() {
                @Override
                public void onError(Request request, Exception e) {
                    updateImagePresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Update_Image_Resp, Constant.Update_Img_error);

                }

                @Override
                public void onResponse(UpdateImageResp response) {
                    if (response.getCommand() == HttpDefine.Update_Image_Resp) {
                        updateImagePresenter.onRespSucess(response);
                    } else {
                        updateImagePresenter.onRespFail(response.getMessage(), HttpDefine.Update_Image_Resp, response.getCode());
                    }
                }
            }, new File(imgPath), "fileKey");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDatas(List<String> imgPaths, int savetype) {
        int size = imgPaths.size();
        File[] files = new File[size];
        String[] fileKeyList = new String[size];
        Param param = new Param("File", "fileKey");
        File file;
        if (imgPaths != null && imgPaths.size() > 0) {
            for (int i = 0; i < size; i++) {
                file = new File(imgPaths.get(i));
                files[i] = file;
                fileKeyList[i] = file.getAbsolutePath();
            }
        }
        String url = Constant.UpImageUrl + savetype;
        try {
            OkHttpClientManager.postAsyn(url, new OkHttpClientManager.ResultCallback<UpdateImageResp>() {
                @Override
                public void onError(Request request, Exception e) {
                    updateImagePresenter.onRespFail(Constant.Load_NetWork_error, HttpDefine.Update_Image_Resp, Constant.Update_Img_error);

                }

                @Override
                public void onResponse(UpdateImageResp response) {
                    if (response.getCommand() == HttpDefine.Update_Image_Resp) {
                        updateImagePresenter.onRespSucess(response);
                    } else {
                        updateImagePresenter.onRespFail(response.getMessage(), HttpDefine.Update_Image_Resp, response.getCode());
                    }
                }
            }, files, fileKeyList, param);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
