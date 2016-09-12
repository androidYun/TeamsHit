package teams.xianlin.com.teamshit.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.Interface.EditNameCallBack;
import teams.xianlin.com.teamshit.Interface.SelectorSingleCallBack;
import teams.xianlin.com.teamshit.NetWorkResp.BackGroupResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditGamePeopleResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditGroupNameResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditGroupTypeResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditGroupVerifitionResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditLeastCoinResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetGroupDetailResp;
import teams.xianlin.com.teamshit.Presenter.GetGroupDetailPresenter;
import teams.xianlin.com.teamshit.Presenter.EditGroupDetailPresenter;
import teams.xianlin.com.teamshit.PresenterView.EditGroupDetailView;
import teams.xianlin.com.teamshit.PresenterView.GetGroupDetailView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.GameDataUtils;
import teams.xianlin.com.teamshit.Utils.ListUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.widget.DialogUtils.EditNameDialog;
import teams.xianlin.com.teamshit.widget.DialogUtils.SelectorSingleItemDialog;
import teams.xianlin.com.teamshit.widget.FriendCircle.MultiImageView;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/9/9.
 */
public class GroupDetailActivity extends BaseActivity implements GetGroupDetailView, EditGroupDetailView, SelectorSingleCallBack, EditNameCallBack {
    private final static int GameType = 1;

    private final static int GamePeople = 2;

    private final static int GameRole = 3;

    private final static int GameCoin = 4;

    private final static int GroupVerifition = 5;

    @Bind(R.id.navi_top)
    TitleNavi naviTop;
    @Bind(R.id.txt_group_people)
    TextView txtGroupPeople;
    @Bind(R.id.txt_group_id)
    TextView txtGroupId;
    @Bind(R.id.layout_group_number)
    RelativeLayout layoutGroupNumber;
    @Bind(R.id.txt_group_type)
    TextView txtGroupType;
    @Bind(R.id.layout_group_type)
    RelativeLayout layoutGroupType;
    @Bind(R.id.txt_game_people)
    TextView txtGamePeople;
    @Bind(R.id.layout_game_number)
    RelativeLayout layoutGameNumber;
    @Bind(R.id.layout_rule)
    RelativeLayout layoutRule;
    @Bind(R.id.cbx_message_disturb)
    CheckBox cbxMessageDisturb;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.multiImagView)
    MultiImageView multiImagView;
    @Bind(R.id.txt_coin_number)
    TextView txtCoinNumber;
    @Bind(R.id.layout_coin_number)
    RelativeLayout layoutCoinNumber;
    @Bind(R.id.txt_group_name)
    TextView txtGroupName;
    @Bind(R.id.layout_group_name)
    RelativeLayout layoutGroupName;
    @Bind(R.id.img_group_type)
    ImageView imgGroupType;
    @Bind(R.id.img_group_people)
    ImageView imgGroupPeople;
    @Bind(R.id.img_coin_number)
    ImageView imgCoinNumber;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.layout_group_verifition)
    RelativeLayout layoutGroupVerifition;
    @Bind(R.id.txt_group_verifition)
    TextView txtGroupVerifition;

    private SelectorSingleItemDialog GameTypeDialog;

    private SelectorSingleItemDialog GamePeopleDialog;

    private SelectorSingleItemDialog GameRoleDialog;

    private SelectorSingleItemDialog GameCoinDialog;

    private SelectorSingleItemDialog GameVerifitionDialog;

    private EditNameDialog GameEditNameDialog;

    private String[] mGameType;

    private String[] mGamePeople;

    private String[] mGameRole;

    private String[] mGameCoin;

    private String[] mGameVerifition;


    private GetGroupDetailPresenter getGroupDetailPresenter;

    private EditGroupDetailPresenter editGroupDetailPresenter;

    private String mTargetId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        ButterKnife.bind(this);
        initialData();
        inflateView();
    }

    @Override
    protected void initialData() {
        mGameType = GameDataUtils.getInstance().getGameType();
        mGamePeople = GameDataUtils.getInstance().getGamePeople();
        mGameRole = GameDataUtils.getInstance().getGameRole();
        mGameCoin = GameDataUtils.getInstance().getGameCoin();
        mGameVerifition = GameDataUtils.getInstance().getGroupVerifition();
        GameTypeDialog = new SelectorSingleItemDialog(mActivity, GameType, this, mGameType);

        GamePeopleDialog = new SelectorSingleItemDialog(mActivity, GamePeople, this, mGamePeople);

        GameRoleDialog = new SelectorSingleItemDialog(mActivity, GameRole, this, mGameRole);

        GameCoinDialog = new SelectorSingleItemDialog(mActivity, GameCoin, this, mGameCoin);

        GameVerifitionDialog = new SelectorSingleItemDialog(mActivity, GroupVerifition, this, mGameVerifition);

        GameEditNameDialog = new EditNameDialog(mActivity, this);

        naviTop.setClickCallBack(this);
        mTargetId = getIntent().getStringExtra(Constant.GroupId);
        getGroupDetailPresenter = new GetGroupDetailPresenter(this);
        getGroupDetailPresenter.loadData(mActivity, mTargetId);
    }

    @Override
    protected void inflateView() {
        GameTypeDialog.setTitle("游戏类型");
        GamePeopleDialog.setTitle("游戏人数");
        GameRoleDialog.setTitle("游戏规则");
        GameCoinDialog.setTitle("游戏最少金币");
        GameVerifitionDialog.setTitle("群组验证");
    }

    @Override
    public void onLoadSucess(GetGroupDetailResp getGroupDetailResp) {
        txtGroupPeople.setText("全体群成员" + getGroupDetailResp.getNumber());
        txtGroupId.setText("" + getGroupDetailResp.getGroupId());
        txtGamePeople.setText("" + getGroupDetailResp.getGamePeople());
        txtCoinNumber.setText("" + getGroupDetailResp.getLeastCoins());
        naviTop.setBackTitle("" + getGroupDetailResp.getGroupName());
        if (getGroupDetailResp.getGroupType() == 1) {
            txtGroupType.setText(mGameType[0]);
        } else if (getGroupDetailResp.getGroupType() == 2) {
            txtGroupType.setText(mGameType[1]);
        }
        if (getGroupDetailResp.getVerificationType() == 1) {//游戏验证类型
            txtGroupVerifition.setText(mGameVerifition[0] + "");
        } else {
            txtGroupVerifition.setText(mGameVerifition[1] + "");
        }
        List<UserBean> friendList = getGroupDetailResp.getFriendList();
        if (friendList != null) {
            List<String> stringList = ListUtils.getInstance().getListStrForListObject(friendList);
            stringList.add("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo_top_ca79a146.png");
            stringList.add("");
            multiImagView.setMAX_PER_ROW_COUNT(stringList.size());
            multiImagView.setList(stringList, multiImagView.MultiImageViewGroupDetail);//1位图片显示类型
        }
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        ToastUtil.show(mActivity, errorMsg.getErrorMsg() + "");
    }

    @Override
    public void showGetGroupDetailProgress() {
        loadDialog.show();
    }

    @Override
    public void hideGetGroupDetailProgress() {
        loadDialog.hide();
    }

    @OnClick({R.id.layout_group_type, R.id.layout_game_number, R.id.layout_group_name, R.id.layout_rule, R.id.layout_coin_number})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.layout_group_type:
                GameTypeDialog.show();
                break;
            case R.id.layout_game_number:
                GamePeopleDialog.show();
                break;
            case R.id.layout_group_name:
                GameEditNameDialog.show();
                break;
            case R.id.layout_rule:
                GameRoleDialog.show();
                break;
            case R.id.layout_coin_number:
                GameCoinDialog.show();
                break;
            case R.id.layout_group_verifition:
                GameVerifitionDialog.show();
                break;
        }

    }

    @Override
    public void SelectorSingleOnSuccess(int selectorType, int position, String content) {
        switch (selectorType) {
            case GameType:

                break;
            case GamePeople:

                break;
            case GameRole:

                break;
            case GameCoin:

                break;
        }
    }

    @Override
    public void onEditTeamNameSuccess(String result) {

    }

    @Override
    public void onRespSucess(EditGroupNameResp editGroupNameResp) {

    }

    @Override
    public void onRespSucess(EditGroupTypeResp editGroupTypeResp) {

    }

    @Override
    public void onRespSucess(EditGroupVerifitionResp editGroupVerifitionResp) {

    }

    @Override
    public void onRespSucess(EditGamePeopleResp editGamePeopleResp) {

    }

    @Override
    public void onRespSucess(EditLeastCoinResp editLeastCoinResp) {

    }

    @Override
    public void onRespSucess(BackGroupResp backGroupResp) {

    }

    @Override
    public void onRespFail(String errorStr, int RespCode, int errorCode) {

    }
}
