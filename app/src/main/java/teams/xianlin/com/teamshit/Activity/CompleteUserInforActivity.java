package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.Bean.UserInforBean;
import teams.xianlin.com.teamshit.Interface.SelectPhotoCallBack;
import teams.xianlin.com.teamshit.Interface.SelectSexCallBack;
import teams.xianlin.com.teamshit.Interface.SelectorCityCallBack;
import teams.xianlin.com.teamshit.NetWorkResp.CompleteUserInfoResp;
import teams.xianlin.com.teamshit.NetWorkResp.UpdateImageResp;
import teams.xianlin.com.teamshit.Presenter.CompleUserInforPresenter;
import teams.xianlin.com.teamshit.Presenter.UpdateImagePresenter;
import teams.xianlin.com.teamshit.PresenterView.CompleUserInforView;
import teams.xianlin.com.teamshit.PresenterView.UpdateImgView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.PhotoPickManger;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.SPUtils;
import teams.xianlin.com.teamshit.Utils.TimeUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.httpService.OkHttpClientManager;
import teams.xianlin.com.teamshit.widget.DialogUtils.SelectPhotoDialog;
import teams.xianlin.com.teamshit.widget.DialogUtils.SelectSexDialog;
import teams.xianlin.com.teamshit.widget.DialogUtils.SelectorCityDialog;

/**
 * Created by Administrator on 2016/7/13.
 */
public class CompleteUserInforActivity extends BaseActivity implements SelectSexCallBack, SelectorCityCallBack, TimePickerView.OnTimeSelectListener, SelectPhotoCallBack, CompleUserInforView, UpdateImgView {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_finish)
    TextView txtFinish;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.navi_title)
    RelativeLayout naviTitle;
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.txt_nick_name_title)
    TextView txtNickNameTitle;
    @Bind(R.id.edit_nick_name)
    EditText editNickName;
    @Bind(R.id.txt_user_name_title)
    TextView txtUserNameTitle;
    @Bind(R.id.edit_user_name)
    EditText editUserName;
    @Bind(R.id.txt_sex)
    TextView txtSex;
    @Bind(R.id.txt_city)
    TextView txtCity;
    @Bind(R.id.txt_birthday)
    TextView txtBirthday;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.vMasker)
    View vMasker;
    public SelectSexDialog selectSexDialog;

    private TimePickerView pvTime;

    private SelectPhotoDialog selectDialog;

    private SelectorCityDialog selectorCityDialog;

    private PhotoPickManger photoPickManger;

    private CompleUserInforPresenter compleUserInforPresenter;

    private UpdateImagePresenter updateImagePresenter;

    private String mSex;//性别

    private String mTime;//生日时间

    private String headIcon;//头像

    private String mProvice;//省会

    private String mCity;//省会

    private String mDistrict;//地区


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_user_infor);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        vMasker = findViewById(R.id.vMasker);
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        imgHead.setOnClickListener(this);
        txtSex.setOnClickListener(this);
        txtCity.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        txtBirthday.setOnClickListener(this);
        pvTime.setOnTimeSelectListener(this);
    }

    @Override
    protected void initialData() {
        txtBack.setText("完善信息");
        btnConfirm.setText("完成");
    }


    @Override
    protected void inflateView() {
        selectSexDialog = new SelectSexDialog(CompleteUserInforActivity.this, this);
        selectorCityDialog = new SelectorCityDialog(mActivity, this);
        selectorCityDialog = new SelectorCityDialog(mActivity, this);
        compleUserInforPresenter = new CompleUserInforPresenter(this);
        updateImagePresenter = new UpdateImagePresenter(this);
        selectDialog = new SelectPhotoDialog(mActivity, this);
        photoPickManger = PhotoPickManger.getInStance();
        selectDialog.setonSelectCallBack(this);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img_head:
                selectDialog.show();
                break;
            case R.id.txt_sex:
                selectSexDialog.show();
                break;
            case R.id.txt_birthday:
                pvTime.show();
                break;
            case R.id.txt_city:
                selectorCityDialog.show();
                break;
            case R.id.btn_confirm:
                compleUserInforPresenter.loadData(headIcon, editNickName.getText().toString(), editUserName.getText().toString(), mSex, "河南省", "郑州市", mTime, CompleteUserInforActivity.this);
                break;
        }
    }


    @Override
    public void onSelelctSexSuccess(int result) {
        if (result == 1) {
            mSex = "男";
        } else if (result == 2) {
            mSex = "女";
            ToastUtil.show(CompleteUserInforActivity.this, "女");
        } else if (result == 3) {
            mSex = "保密";
            ToastUtil.show(CompleteUserInforActivity.this, "保密");
        }
        txtSex.setText(mSex);
    }

    @Override
    public void onTimeSelect(Date date) {
        mTime = TimeUtils.getInstance().getTime(date);
        txtBirthday.setText(mTime);
    }

    @Override
    public void ClickCamera() {
        GalleryFinal.openCamera(Constant.Camera_Code, TeamHitContext.getCropFunctionConfig(), mOnHanlderResultCallback);
        selectDialog.dismiss();
    }

    @Override
    public void ClickPhoto() {
        GalleryFinal.openGalleryMuti(Constant.Camera_Code, TeamHitContext.getCropFunctionConfig(), mOnHanlderResultCallback);
        selectDialog.cancel();
    }

    /**
     * 上传图片
     *
     * @param photoPath
     */
    public void updateImage(String photoPath) {
        try {
            OkHttpClientManager.postAsyn(Constant.UpImageUrl, new OkHttpClientManager.ResultCallback<UpdateImageResp>() {
                @Override
                public void onError(Request request, Exception e) {
                    loadDialog.cancel();
                    ToastUtil.show(CompleteUserInforActivity.this, "请求失败");
                }

                @Override
                public void onResponse(UpdateImageResp response) {
                    loadDialog.cancel();
                    headIcon = response.getImgPath();
                    LogUtil.d("头像" + headIcon);
                    ToastUtil.show(CompleteUserInforActivity.this, "上传成功");

                }
            }, new File(photoPath), "fileKey");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void ClickCancle() {
        selectDialog.dismiss();
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {

                photoPickManger.doProcessedPhotos(resultList, new PhotoPickManger.OnProcessedPhotos() {
                    @Override
                    public void onProcessed(List<PhotoInfo> list) {
                        if (list != null) {
                            Glide.with(CompleteUserInforActivity.this).load("file://" + list.get(0).getPhotoPath()).into(imgHead);
                            if (list != null && list.get(0) != null)
                                updateImagePresenter.loadData(mActivity, list.get(0).getPhotoPath(), 1);
                            loadDialog.show();
                        }

                    }
                });
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(CompleteUserInforActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            selectDialog.dismiss();
        }
    };

    @Override
    public void onLoadSucess(CompleteUserInfoResp completeUserInfoResp) {
        UserInforBean userInforBean = new UserInforBean(TeamHitContext.getInstance().getUserId(), editNickName.getText().toString(), headIcon, editUserName.getText().toString(), mSex, mProvice + mCity, mTime);
        TeamHitContext.getInstance().putUserInforBean(userInforBean);
        ToastUtil.show(CompleteUserInforActivity.this, "提交成功");
        Intent mainIntent = new Intent(mActivity, LoginActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        ToastUtil.show(CompleteUserInforActivity.this, errorMsg.getErrorMsg() + "");
        Intent loginIntent = new Intent(mActivity, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void showCompleUserInforProgress() {
        loadDialog.show();
    }

    @Override
    public void hideCompleUserInforProgress() {
        loadDialog.dismiss();
    }

    @Override
    public void onSelelctCitySuccess(String province, String city, String district) {
        mProvice = province;
        mCity = city;
        txtCity.setText("" + mProvice + " " + mCity);
    }

    @Override
    public void onLoadSucess(UpdateImageResp updateImageResp) {

    }

    @Override
    public void showUpdateImgProgress() {

    }

    @Override
    public void hideUpdateImgProgress() {

    }
}
