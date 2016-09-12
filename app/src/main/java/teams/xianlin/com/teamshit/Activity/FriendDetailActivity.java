package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.EventBus.EditDisplayNameEvent;
import teams.xianlin.com.teamshit.EventBus.OperatorFriendEvent;
import teams.xianlin.com.teamshit.NetWorkResp.SearchFriendResp;
import teams.xianlin.com.teamshit.Presenter.SearchFriendPresenter;
import teams.xianlin.com.teamshit.PresenterView.SearchFriendView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.ImageLoadUtils;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.db.DBManager;
import teams.xianlin.com.teamshit.db.Friend;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/8/9.
 * 填充好友详情
 * 跳转到好友详情 需要传递两个参数 一个是 类型 type  为1 就传递一个好友类   2  就传递一个好友Id
 */
public class FriendDetailActivity extends BaseActivity implements SearchFriendView {
    @Bind(R.id.navi_friend_defail)
    TitleNavi naviFriendDefail;
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.txt_nick_name_title)
    TextView txtNickNameTitle;
    @Bind(R.id.txt_user_name_title)
    TextView txtUserNameTitle;
    @Bind(R.id.txt_remark)
    TextView txtRemark;
    @Bind(R.id.txt_phone)
    TextView txtPhone;
    @Bind(R.id.txt_address)
    TextView txtAddress;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.img_one)
    ImageView imgOne;
    @Bind(R.id.img_two)
    ImageView imgTwo;
    @Bind(R.id.img_three)
    ImageView imgThree;
    @Bind(R.id.img_four)
    ImageView imgFour;
    @Bind(R.id.txt_title_address)
    TextView txtTitleAddress;
    @Bind(R.id.layout_friend_circle)
    LinearLayout layoutFriendCircle;

    private SearchFriendPresenter searchFriendPresenter;//实体类

    private String mTargetId;

    private int mType;//跳转过来的类型  1是传递一个详情类，如果传递的为2 则是一个UserId  用请求详情类

    private SearchFriendResp mSearchFriendResp;//好友详情实体类

    private String mDisPlayName;//昵称显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        super.initialView();
    }

    @Override
    protected void initialData() {
        searchFriendPresenter = new SearchFriendPresenter(this);
        Bundle bundle = getIntent().getExtras();
        mType = bundle.getInt(Constant.Friend_Detail_Type);
        if (mType == 1) {
            mSearchFriendResp = (SearchFriendResp) bundle.getSerializable(Constant.Friend_Deatil_Bean);
        } else if (mType == 2) {
            mTargetId = bundle.getString(Constant.Target_Id);
            searchFriendPresenter.loadData(mActivity, mTargetId);
        }
        naviFriendDefail.setClickCallBack(this);
    }

    @Override
    protected void inflateView() {
        if (mSearchFriendResp == null) {
            return;
        }
        if (mSearchFriendResp.getIconUrl() != null) {
            ImageLoadUtils.getInstance().with(mActivity, mSearchFriendResp.getIconUrl(), R.drawable.default_head, imgHead);
        }
        if (!StringUtils.isBlank(mSearchFriendResp.getNickName())) {
            txtNickNameTitle.setText("昵称:" + mSearchFriendResp.getNickName());
        }
        txtUserNameTitle.setText("对对号:" + mSearchFriendResp.getUserId());
        if (!StringUtils.isBlank(mSearchFriendResp.getDisplayName())) {
            mDisPlayName = mSearchFriendResp.getDisplayName();
            txtRemark.setText(mSearchFriendResp.getDisplayName());
        }
        if (!StringUtils.isBlank(mSearchFriendResp.getPhone())) {
            txtPhone.setText(mSearchFriendResp.getPhone());
        }
        txtAddress.setText(mSearchFriendResp.getAddress());//填充地址

        List<SearchFriendResp.GalleryUrl> searchFriendRespList = mSearchFriendResp.getGalleryList();

        if (searchFriendRespList != null && searchFriendRespList.size() != 0) {//填充图片集  因为最多就四张图片所以都不用适配器了
            layoutFriendCircle.setVisibility(View.VISIBLE);
            List<SearchFriendResp.GalleryUrl> galleryList = mSearchFriendResp.getGalleryList();
            int size = galleryList.size();
            for (int i = 0; i < size; i++) {
                String imgUrl = galleryList.get(i).getImgUrl();
                if (i == 0) {
                    imgOne.setVisibility(View.VISIBLE);
                    ImageLoadUtils.getInstance().with(mActivity, imgUrl, imgOne);
                } else if (i == 1) {
                    imgTwo.setVisibility(View.VISIBLE);
                    ImageLoadUtils.getInstance().with(mActivity, imgUrl, imgTwo);
                } else if (i == 2) {
                    imgThree.setVisibility(View.VISIBLE);
                    ImageLoadUtils.getInstance().with(mActivity, imgUrl, imgThree);
                } else if (i == 3) {
                    imgFour.setVisibility(View.VISIBLE);
                    ImageLoadUtils.getInstance().with(mActivity, imgUrl, imgFour);
                }
            }
        }
        if (mSearchFriendResp.isFriend()) {//是朋友
            naviFriendDefail.setFinishImg(R.drawable.three_dot);
            btnConfirm.setText("发信息");
        } else {
            btnConfirm.setText("添加通讯录");
        }
        naviFriendDefail.setBackTitle("详细资料");
    }

    @OnClick({R.id.btn_confirm, R.id.txt_remark, R.id.layout_friend_circle})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (mSearchFriendResp.isFriend()) {//是朋友
                    if (RongIM.getInstance() == null) {
                        ToastUtil.show(mActivity, "没有连接服务器");
                        return;
                    }
                    RongIM.getInstance().startPrivateChat(mActivity, String.valueOf(mSearchFriendResp.getUserId()), mSearchFriendResp.getNickName());
                } else {
                    if (mSearchFriendResp.getPhone().equals(TeamHitContext.getInstance().getmAccount())) {
                        ToastUtil.show(mActivity, "自己不能添加自己为好友");
                        return;
                    }
                    Intent addFriendIntent = new Intent(mActivity, AddFriendsActivity.class);
                    addFriendIntent.putExtra(Constant.User_Id, mSearchFriendResp.getUserId());
                    startActivity(addFriendIntent);
                }
                break;
            case R.id.txt_remark:


                break;
            case R.id.layout_friend_circle:


                break;
        }
    }

    @Override
    public void onLoadSucess(SearchFriendResp searchFriendResp) {
        mSearchFriendResp = searchFriendResp;
        inflateView();
        if (searchFriendResp.isFriend()) {
            DBManager.getInstance(mActivity).getDaoSession().getFriendDao().insertOrReplace(new Friend(String.valueOf(searchFriendResp.getUserId()), searchFriendResp.getNickName(), searchFriendResp.getIconUrl(), searchFriendResp.getDisplayName(), null, null));
        }
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        ToastUtil.show(mActivity, errorMsg.getErrorMsg());
        mVaryViewHelper.showEmptyView();
    }

    @Override
    public void onRightImgClick() {
        super.onRightImgClick();
        if (mSearchFriendResp == null) {
            return;
        }
        Intent friendDataintent = new Intent(mActivity, SetFriendDataActivity.class);
        friendDataintent.putExtra(Constant.Target_Id, mSearchFriendResp.getUserId());
        friendDataintent.putExtra(Constant.DisPlay_Name, mSearchFriendResp.getDisplayName());
        startActivity(friendDataintent);
    }

    @Override
    public void showSearchFriendProgress() {
        loadDialog.show();
    }

    @Override
    public void hideSearchFriendProgress() {
        loadDialog.hide();
    }

    public void onEventMainThread(OperatorFriendEvent event) {
        this.finish();
    }


    public void onEventMainThread(EditDisplayNameEvent event) {//修改昵称
        if (!StringUtils.isBlank(event.getDisplayName())) {
            mDisPlayName = event.getDisplayName();
            txtRemark.setText(mSearchFriendResp.getDisplayName());
        }
    }
}
