package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.SearchFriendResp;
import teams.xianlin.com.teamshit.NetWorkResp.SearchGroupListResp;
import teams.xianlin.com.teamshit.Presenter.OperateFriendPresenter;
import teams.xianlin.com.teamshit.Presenter.SearchFriendPresenter;
import teams.xianlin.com.teamshit.Presenter.SearchGroupListPresenter;
import teams.xianlin.com.teamshit.PresenterView.SearchFriendView;
import teams.xianlin.com.teamshit.PresenterView.SearchGroupListView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.TextUtils.SpanableUtils;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/7/19.
 * 搜索好友
 */
public class SearchFriendActivity extends BaseActivity implements SearchFriendView, SearchGroupListView {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.edit_search)
    EditText editSearch;
    @Bind(R.id.navi_title)
    RelativeLayout naviTitle;

    TextView txtSearchGroup;

    TextView txtSearchAccount;

    View serachBefore;

    TextView view_no_friend;//没有好友的界面

    private View inflateView;//加载搜索后的布局

    private OperateFriendPresenter operateFriendPresenter;//好友操作

    private SearchFriendPresenter searchFriendPresenter;//请求搜索好友

    private SearchGroupListPresenter searchGroupListPresenter;//搜索群组列表

    private String mAccount;//好友手机号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);
        ButterKnife.bind(this);
        initialView();
        initialData();
    }

    @Override
    public void initialView() {
        view_no_friend = (TextView) findViewById(R.id.view_no_friend);
        inflateView = ((ViewStub) findViewById(R.id.serach_after)).inflate();
        txtSearchAccount = (TextView) inflateView.findViewById(R.id.txt_search_account);
        txtSearchGroup = (TextView) inflateView.findViewById(R.id.txt_search_group);
        serachBefore = findViewById(R.id.serach_before);
        txtSearchAccount.setOnClickListener(this);
        txtSearchGroup.setOnClickListener(this);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mAccount = editable.toString();
                serachBefore.setVisibility(View.GONE);
                view_no_friend.setVisibility(View.GONE);
                inflateView.setVisibility(View.VISIBLE);
                SpannableString spanAccount = SpanableUtils.getInstance().setColorSpanStyle("账号查找 " + editable.toString(), getResources().getColor(R.color.loght_pink_color), 4, 5 + editable.length());
                SpannableString spanGroup = SpanableUtils.getInstance().setColorSpanStyle("搜搜 " + editable.toString(), getResources().getColor(R.color.loght_pink_color), 2, 3 + editable.length());
                txtSearchAccount.setText(spanAccount);
                txtSearchGroup.setText(spanGroup);
            }
        });
    }

    @Override
    protected void initialData() {
        searchFriendPresenter = new SearchFriendPresenter(this);
        searchGroupListPresenter = new SearchGroupListPresenter(this);
    }

    @Override
    protected void inflateView() {

    }

    @OnClick({R.id.img_back})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.txt_search_account:
                searchFriendPresenter.loadData(mActivity, mAccount);
                break;
            case R.id.txt_search_group:
                searchGroupListPresenter.loadData(mActivity, mAccount);
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public void onLoadSucess(SearchFriendResp searchFriendResp) {
        if (searchFriendResp == null || searchFriendResp.getUserId() == 0) {
            view_no_friend.setVisibility(View.VISIBLE);
            return;
        }
        Intent friendDeatilIntent = new Intent(mActivity, FriendDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.Friend_Detail_Type, 1);//用于判断 进去 好朋友详情的类型  1 是传递的是 一个详情类  2 是一个UserID
        bundle.putSerializable(Constant.Friend_Deatil_Bean, searchFriendResp);
        friendDeatilIntent.putExtras(bundle);
        startActivity(friendDeatilIntent);
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        view_no_friend.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchFriendProgress() {
        loadDialog.show();
    }

    @Override
    public void hideSearchFriendProgress() {
        loadDialog.hide();
    }

    @Override
    public void onLoadSucess(SearchGroupListResp searchGroupListResp) {
        List<SearchGroupListResp.GroupList> groupList = searchGroupListResp.getGroupList();

        if (groupList == null || groupList.size() == 0) {
            view_no_friend.setVisibility(View.VISIBLE);
            return;
        }
        Intent groupListIntent = new Intent(mActivity, SearchGroupListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.Group_List_Bean, searchGroupListResp);
        groupListIntent.putExtras(bundle);
        startActivity(groupListIntent);
    }

    @Override
    public void showSearchGroupListProgress() {
        loadDialog.show();
    }

    @Override
    public void hideSearchGroupListProgress() {
        loadDialog.hide();
    }
}
