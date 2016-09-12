package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.Interface.SelectPhotoCallBack;
import teams.xianlin.com.teamshit.Interface.SelectorSingleCallBack;
import teams.xianlin.com.teamshit.NetWorkResp.CreateGroupResp;
import teams.xianlin.com.teamshit.NetWorkResp.UpdateImageResp;
import teams.xianlin.com.teamshit.Presenter.CreateGroupPresenter;
import teams.xianlin.com.teamshit.Presenter.UpdateImagePresenter;
import teams.xianlin.com.teamshit.PresenterView.CreateGroupView;
import teams.xianlin.com.teamshit.PresenterView.UpdateImgView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.GameDataUtils;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.PhotoPickManger;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.widget.DialogUtils.SelectPhotoDialog;
import teams.xianlin.com.teamshit.widget.DialogUtils.SelectorSingleItemDialog;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/9/6.
 */
public class CreateGroupActivity extends BaseActivity implements SelectorSingleCallBack, SelectPhotoCallBack, UpdateImgView, CreateGroupView {
    private final static int Selector_Game = 1;

    private final static int Selctor_Verifition = 2;

    @Bind(R.id.navi_top)
    TitleNavi naviTop;
    @Bind(R.id.edit_group_name)
    EditText editGroupName;
    @Bind(R.id.edit_group_introduce)
    EditText editGroupIntroduce;
    @Bind(R.id.layout_game_type)
    RelativeLayout layoutGameType;
    @Bind(R.id.group_group_verifition)
    RelativeLayout groupGroupVerifition;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.txt_game_type)
    TextView txtGameType;
    @Bind(R.id.txt_verifition_type)
    TextView txtVerifitionType;
    @Bind(R.id.img_add_head)
    ImageView imgAddHead;
    @Bind(R.id.img_group_verifition_next)
    ImageView imgGroupVerifitionNext;

    private SelectorSingleItemDialog selectorGameType;

    private SelectorSingleItemDialog selectorVerivition;

    private SelectPhotoDialog selectPhotoDialog;

    private PhotoPickManger photoPickManger;

    private UpdateImagePresenter updateImagePresenter;

    private CreateGroupPresenter createGroupPresenter;

    private int selectorType;//选择类型  1  是选择  游戏  2  是选择群组权限

    private String[] mGames;//游戏类型数组

    private String[] mVerifitions;//验证类型

    private int mGameType = 1;//游戏类型  默认是 21点  1 是21点  2  是吹牛

    private int mGroupVerifitionType = 1;  //默认为 允许任何人加入   1 是允许任何人加入  2 不允许任何人加入

    private String mHeadUrl;

    private String mMemberIds;//好友Id字符串

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);
        initialData();
        inflateView();
    }

    @Override
    protected void initialData() {
        mMemberIds = getIntent().getStringExtra(Constant.MemberIds);
        mGames = GameDataUtils.getInstance().getGameType();
        mVerifitions = GameDataUtils.getInstance().getGroupVerifition();
        updateImagePresenter = new UpdateImagePresenter(this);
        createGroupPresenter = new CreateGroupPresenter(this);
        photoPickManger = PhotoPickManger.getInStance();
        selectorGameType = new SelectorSingleItemDialog(mActivity, Selector_Game, this, mGames);
        selectorVerivition = new SelectorSingleItemDialog(mActivity, Selctor_Verifition, this, mVerifitions);
        selectPhotoDialog = new SelectPhotoDialog(mActivity, this);
        selectorGameType.setTitle("游戏模式");
        selectorVerivition.setTitle("群组验证");
    }

    @Override
    protected void inflateView() {
        naviTop.setBackTitle("创建群组");
        naviTop.setClickCallBack(this);
        btnConfirm.setText("确定");
    }

    @OnClick({R.id.btn_confirm, R.id.group_group_verifition, R.id.layout_game_type, R.id.img_add_head})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_confirm:
                createGroupPresenter.loadData(mActivity, editGroupName.getText().toString(), mHeadUrl, mGameType, mGroupVerifitionType, mMemberIds, editGroupIntroduce.getText().toString());
                break;
            case R.id.group_group_verifition:
                selectorVerivition.show();
                break;
            case R.id.layout_game_type:
                selectorGameType.show();
                break;
            case R.id.img_add_head:
                selectPhotoDialog.show();
                break;
        }
    }

    @Override
    public void SelectorSingleOnSuccess(int selectorType, int position, String content) {
        switch (selectorType) {
            case Selctor_Verifition:
                mGameType += position;//第一个位置肯定是0   mGameType  类型用1  和2 表示
                txtVerifitionType.setText("" + content);
                break;
            case Selector_Game:
                mGroupVerifitionType += position;
                txtGameType.setText("" + content);
                break;
        }
    }

    @Override
    public void ClickCamera() {
        GalleryFinal.openCamera(Constant.Camera_Code, TeamHitContext.getCropFunctionConfig(), mOnHanlderResultCallback);
        selectPhotoDialog.dismiss();
    }

    @Override
    public void ClickPhoto() {
        GalleryFinal.openGalleryMuti(Constant.Camera_Code, TeamHitContext.getCropFunctionConfig(), mOnHanlderResultCallback);
        selectPhotoDialog.cancel();
    }

    @Override
    public void ClickCancle() {
        selectPhotoDialog.cancel();
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {

                photoPickManger.doProcessedPhotos(resultList, new PhotoPickManger.OnProcessedPhotos() {
                    @Override
                    public void onProcessed(List<PhotoInfo> list) {
                        if (list != null) {
                            Glide.with(mActivity).load("file://" + list.get(0).getPhotoPath()).into(imgAddHead);
                            if (list != null && list.get(0) != null) {
                                updateImagePresenter.loadData(mActivity, list.get(0).getPhotoPath(), 1);
                            }
                        }
                    }
                });
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    @Override
    public void onLoadSucess(UpdateImageResp updateImageResp) {
        mHeadUrl = updateImageResp.getImgPath();
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {

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
    public void onLoadSucess(CreateGroupResp createGroupResp) {
        String GroupId = String.valueOf(createGroupResp.getGroupId());
        Intent chatRoomIntent = new Intent(CreateGroupActivity.this, ChatRoomActivity.class);
        chatRoomIntent.putExtra(Constant.Target_Id, GroupId);
        startActivity(chatRoomIntent);
    }

    @Override
    public void showCreateGroupProgress() {
        loadDialog.show();
    }

    @Override
    public void hideCreateGroupProgress() {
        loadDialog.hide();
    }
}
