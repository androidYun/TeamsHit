package teams.xianlin.com.teamshit.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import io.rong.eventbus.EventBus;
import teams.xianlin.com.teamshit.Adapter.ChoosePhotoListAdapter;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.Bean.UserInforBean;
import teams.xianlin.com.teamshit.EventBus.PublishFriendCircleEvent;
import teams.xianlin.com.teamshit.Interface.SelectPhotoCallBack;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleListResp;
import teams.xianlin.com.teamshit.NetWorkResp.PublishTakeResp;
import teams.xianlin.com.teamshit.NetWorkResp.UpdateImageResp;
import teams.xianlin.com.teamshit.Presenter.PublishTakePresenter;
import teams.xianlin.com.teamshit.Presenter.UpdateImagePresenter;
import teams.xianlin.com.teamshit.PresenterView.PublishTakeView;
import teams.xianlin.com.teamshit.PresenterView.UpdateImgView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.PhotoPickManger;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.widget.DialogUtils.SelectPhotoDialog;
import teams.xianlin.com.teamshit.widget.NoScrollGridview;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/7/8.
 */
public class PublishFriendCircleActivity extends BaseActivity implements AdapterView.OnItemClickListener, SelectPhotoCallBack, UpdateImgView, PublishTakeView {

    @Bind(R.id.navi_friend)
    TitleNavi naviFriend;
    @Bind(R.id.edit_take_about)
    EditText editTakeAbout;
    @Bind(R.id.gvi_photo)
    NoScrollGridview gviPhoto;
    @Bind(R.id.txt_look_type)
    TextView txtLookType;
    @Bind(R.id.rlative_permission)
    RelativeLayout rlativePermission;

    private PhotoPickManger photoPickManger;//图片管理器

    private ChoosePhotoListAdapter choosePhotoListAdapter;

    private ArrayList<PhotoInfo> fileList;//获取的图片

    private List<String> updateList;//上传图片等额列表

    private SelectPhotoDialog selectDialog;

    private UpdateImagePresenter updateImagePresenter;

    private PublishTakePresenter publishTakePresenter;

    private String mPhotoList = null;//图片数量


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_circle);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        naviFriend.setClickCallBack(this);
        photoPickManger = new PhotoPickManger();
        /**
         * 初始化网络请求
         */
        updateImagePresenter = new UpdateImagePresenter(this);
        publishTakePresenter = new PublishTakePresenter(this);

        selectDialog = new SelectPhotoDialog(mActivity, this);
        gviPhoto.setAdapter(choosePhotoListAdapter);
        gviPhoto.setOnItemClickListener(this);
        selectDialog.setonSelectCallBack(this);

    }

    @Override
    protected void initialData() {
        fileList = new ArrayList<PhotoInfo>();
        updateList = new ArrayList<>();
        choosePhotoListAdapter = new ChoosePhotoListAdapter(this, fileList);
        gviPhoto.setAdapter(choosePhotoListAdapter);
    }

    @Override
    protected void inflateView() {
        naviFriend.setBackTitleGone();
        naviFriend.setFinishVisible();
        naviFriend.setFinish("发表");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rlative_permission:

                break;


        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i >= fileList.size()) {//添加图片
            selectDialog.show();
        } else {
            GalleryFinal.openCamera(1001, TeamHitContext.getFunctionConfig(), mOnHanlderResultCallback);
        }
    }

    @Override
    public void onRightClick() {
        List<String> photoPathList = new ArrayList<>();
        for (PhotoInfo photoInfo :
                fileList) {
            photoPathList.add(photoInfo.getPhotoPath());
        }
        if (photoPathList != null && photoPathList.size() != 0) {//有图片就先上传图片，、
            updateImagePresenter.loadData(mActivity, photoPathList, 2);
        } else {//没图片就直接上传说说
            publishTakePresenter.loadData(mActivity, editTakeAbout.getText().toString(), "");
        }

    }

    @Override
    public void onBackClick() {
        super.onBackClick();

    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {

                photoPickManger.doProcessedPhotos(resultList, new PhotoPickManger.OnProcessedPhotos() {
                    @Override
                    public void onProcessed(List<PhotoInfo> list) {
                        fileList.addAll(list);
                        choosePhotoListAdapter.notifyDataSetChanged();
                    }
                });

            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(PublishFriendCircleActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void ClickCamera() {
        GalleryFinal.openCamera(1001, TeamHitContext.getFunctionConfig(), mOnHanlderResultCallback);
        selectDialog.cancel();
    }

    @Override
    public void ClickPhoto() {
        GalleryFinal.openGalleryMuti(1001, TeamHitContext.getFunctionConfig(), mOnHanlderResultCallback);
        selectDialog.cancel();
    }

    @Override
    public void ClickCancle() {
        selectDialog.cancel();
    }

    @Override
    public void onLoadSucess(UpdateImageResp updateImageResp) {
        publishTakePresenter.loadData(mActivity, editTakeAbout.getText().toString(), updateImageResp.getImgPath());
        mPhotoList = updateImageResp.getImgPath();//赋值给一个变量返回给说说

    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        if (errorMsg.getErrorCode() == Constant.Update_Img_error) {//图片上传错误也要提交说说
            publishTakePresenter.loadData(mActivity, editTakeAbout.getText().toString(), "");
        }
        ToastUtil.show(mActivity, errorMsg.getErrorMsg() + "");
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
    public void onLoadSucess(PublishTakeResp publishTakeResp) {
        fileList.clear();
        UserBean userBean = getUserBean();
        GetFriendCircleListResp getFriendCircleListResp = new GetFriendCircleListResp();
        GetFriendCircleListResp.FriendCircleItem friendCircleItem = getFriendCircleListResp.new FriendCircleItem();
        friendCircleItem.setCommentLists(null);//说说评论和点赞都为空
        friendCircleItem.setFavoriteLists(null);
        friendCircleItem.setTakeContent(editTakeAbout.getText().toString());
        friendCircleItem.setUser(userBean);
        friendCircleItem.setPhotoLists(mPhotoList);
        friendCircleItem.setTakeId(publishTakeResp.getTakeId());
        friendCircleItem.setCreateTime(publishTakeResp.getCreateTime());
        EventBus.getDefault().post(new PublishFriendCircleEvent(friendCircleItem));
        finish();
    }

    @Override
    public void showPublishTakeProgress() {
        loadDialog.show();
    }

    @Override
    public void hidePublishTakeProgress() {
        loadDialog.hide();
    }

}
