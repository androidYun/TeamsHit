package teams.xianlin.com.teamshit.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.Bean.UserInforBean;
import teams.xianlin.com.teamshit.Interface.EditNameCallBack;
import teams.xianlin.com.teamshit.Interface.SelectPhotoCallBack;
import teams.xianlin.com.teamshit.Interface.SelectSexCallBack;
import teams.xianlin.com.teamshit.Interface.SelectorCityCallBack;
import teams.xianlin.com.teamshit.NetWorkResp.CompleteUserInfoResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetUserDeatilInforResp;
import teams.xianlin.com.teamshit.NetWorkResp.UpdateImageResp;
import teams.xianlin.com.teamshit.Presenter.CompleUserInforPresenter;
import teams.xianlin.com.teamshit.Presenter.GetUserDetailInforPresenter;
import teams.xianlin.com.teamshit.Presenter.UpdateImagePresenter;
import teams.xianlin.com.teamshit.PresenterView.CompleUserInforView;
import teams.xianlin.com.teamshit.PresenterView.GetUserDetailInforView;
import teams.xianlin.com.teamshit.PresenterView.UpdateImgView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.ImageLoadUtils;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.PhotoPickManger;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.widget.DialogUtils.EditNameDialog;
import teams.xianlin.com.teamshit.widget.DialogUtils.SelectPhotoDialog;
import teams.xianlin.com.teamshit.widget.DialogUtils.SelectSexDialog;
import teams.xianlin.com.teamshit.widget.DialogUtils.SelectorCityDialog;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/8/15.
 */
public class UserInforActivity extends BaseActivity implements GetUserDetailInforView, EditNameCallBack, SelectorCityCallBack, UpdateImgView, CompleUserInforView, SelectPhotoCallBack, SelectSexCallBack {
    @Bind(R.id.navi_top)
    TitleNavi naviTop;
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.txt_sex)
    TextView txtSex;
    @Bind(R.id.txt_user_name)
    TextView txtUserName;
    @Bind(R.id.txt_nick_name)
    TextView txtNickName;
    @Bind(R.id.txt_city)
    TextView txtCity;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    private GetUserDetailInforPresenter getUserDetailInforPresenter;//获取基本资料

    private UpdateImagePresenter updateImagePresenter;//上传图片

    private CompleUserInforPresenter compleUserInforPresenter;

    private SelectPhotoDialog selectPhotoDialog;//选择图片

    private SelectSexDialog selectSexDialog;//选择性别

    private EditNameDialog editNameDialog;//修改昵称

    private SelectorCityDialog selectorCityDialog;//选择城市的

    private PhotoPickManger photoPickManger;//图片处理器

    private String mImgPath;//图片地址

    private String mProvice;//省会

    private String mCity;//省会

    private String mDistrict;//地区

    private UserInforBean mUserInforBean;//个人信息类

    private UpdateImageResp mUpdateImageResp;//完善信息类


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);
        ButterKnife.bind(this);
        initialData();
        inflateView();
    }

    @Override
    protected void initialData() {
        naviTop.setClickCallBack(this);
        getUserDetailInforPresenter = new GetUserDetailInforPresenter(this);
        compleUserInforPresenter = new CompleUserInforPresenter(this);
        updateImagePresenter = new UpdateImagePresenter(this);
        editNameDialog = new EditNameDialog(mActivity, this);
        selectorCityDialog = new SelectorCityDialog(mActivity, this);
        photoPickManger = PhotoPickManger.getInStance();
        selectPhotoDialog = new SelectPhotoDialog(mActivity, this);
        selectSexDialog = new SelectSexDialog(mActivity, this);
        getUserDetailInforPresenter.loadData(mActivity);//请求个人信息
        mUserInforBean = TeamHitContext.getInstance().getUserInforBean();
    }

    @Override
    protected void inflateView() {
        naviTop.setBackTitle("个人资料");
        btnConfirm.setText("完成");
        ImageLoadUtils.getInstance().with(mActivity, mUserInforBean.getPortraitUri(), R.drawable.default_head, imgHead);
        txtSex.setText("" + mUserInforBean.getGender());
        txtCity.setText("" + mUserInforBean.getCity());
        txtNickName.setText("" + mUserInforBean.getNickname());
        txtUserName.setText("" + mUserInforBean.getUserName());
    }

    @Override
    public void onLoadSucess(CompleteUserInfoResp completeUserInfoResp) {
        UserInfo userInfo = new UserInfo(String.valueOf(TeamHitContext.getInstance().getUserId()), txtNickName.getText().toString(), Uri.parse(mImgPath));
        RongIM.getInstance().setCurrentUserInfo(userInfo);
    }

    @Override
    public void showCompleUserInforProgress() {
        loadDialog.show();
    }

    @Override
    public void hideCompleUserInforProgress() {
        loadDialog.cancel();
    }

    @Override
    public void onLoadSucess(GetUserDeatilInforResp getUserDeatilInforResp) {
        GetUserDeatilInforResp.DetailUserInforBean detailInfor = getUserDeatilInforResp.getDetailInfor();
        if (detailInfor != null) {
            mUserInforBean = new UserInforBean(detailInfor.getUserId(), detailInfor.getNickName(), detailInfor.getPortraitUri(), detailInfor.getUserName(), detailInfor.getGender(), detailInfor.getCity(), detailInfor.getBirthDate());
            inflateView();
        }
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        ToastUtil.show(mActivity, errorMsg.getErrorMsg());
    }

    @Override
    public void showGetUserInforProgress() {
        loadDialog.show();
    }

    @Override
    public void hideGetUserInforProgress() {
        loadDialog.hide();
    }

    @Override
    public void onLoadSucess(UpdateImageResp updateImageResp) {
        mImgPath = updateImageResp.getImgPath();
    }

    @Override
    public void showUpdateImgProgress() {
        loadDialog.show();
    }

    @Override
    public void hideUpdateImgProgress() {
        loadDialog.hide();
    }

    @Override
    public void ClickCamera() {
        GalleryFinal.openCamera(Constant.Camera_Code, TeamHitContext.getCropFunctionConfig(), mOnHanlderResultCallback);
        selectPhotoDialog.cancel();
    }

    @Override
    public void ClickPhoto() {
        GalleryFinal.openGallerySingle(Constant.Photo_Gallery_Code, TeamHitContext.getCropFunctionConfig(), mOnHanlderResultCallback);
        selectPhotoDialog.cancel();
    }

    @Override
    public void ClickCancle() {
        selectPhotoDialog.cancel();
    }

    @Override
    public void onSelelctSexSuccess(int result) {
        txtSex.setText("" + result);
    }

    @Override
    public void onEditTeamNameSuccess(String result) {
        txtNickName.setText(result + "");
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(final int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                photoPickManger.doProcessedPhotos(resultList, new PhotoPickManger.OnProcessedPhotos() {
                    @Override
                    public void onProcessed(List<PhotoInfo> list) {
                        LogUtil.d("" + list.get(0).getPhotoPath() + "  " + reqeustCode);
                        if (list != null && list.size() > 0) {
                            if (Constant.Photo_Gallery_Code == reqeustCode) {
                                updateImagePresenter.loadData(mActivity, list.get(0).getPhotoPath(),1);
                            } else if (Constant.Camera_Code == reqeustCode) {
                                updateImagePresenter.loadData(mActivity, list.get(0).getPhotoPath(),1);
                            }
                            Glide.with(mActivity).load("file://" + list.get(0).getPhotoPath()).into(imgHead);
                        }
                    }
                });
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(mActivity, errorMsg, Toast.LENGTH_SHORT).show();
            selectPhotoDialog.dismiss();
        }
    };

    @OnClick({R.id.img_head, R.id.txt_sex, R.id.txt_city, R.id.txt_nick_name, R.id.btn_confirm})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img_head:
                selectPhotoDialog.show();
                break;
            case R.id.txt_sex:
                selectSexDialog.show();
                break;
            case R.id.txt_city:
                selectorCityDialog.show();
                break;
            case R.id.txt_nick_name:
                editNameDialog.show();
                break;
            case R.id.btn_confirm:
                if (StringUtils.isBlank(mImgPath)) {
                    mImgPath = mUpdateImageResp.getImgPath();
                }
                compleUserInforPresenter.loadData(mImgPath, txtNickName.getText().toString(), txtUserName.getText().toString(), txtSex.getText().toString(), mProvice, mCity, "", mActivity);
                break;
        }
    }

    @Override
    public void onSelelctCitySuccess(String province, String city, String district) {
        mProvice = province;
        mCity = city;
        mDistrict = district;
        txtCity.setText("" + mProvice + " " + mCity);
    }
}
