package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.eventbus.EventBus;
import teams.xianlin.com.teamshit.Adapter.NewFriendAdapter;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.EventBus.OperatorFriendEvent;
import teams.xianlin.com.teamshit.Interface.OnItemClickListener;
import teams.xianlin.com.teamshit.NetWorkResp.GetNewFriendListResp;
import teams.xianlin.com.teamshit.NetWorkResp.OperateFriendResp;
import teams.xianlin.com.teamshit.Presenter.GetNewFriendListPresenter;
import teams.xianlin.com.teamshit.Presenter.OperateFriendPresenter;
import teams.xianlin.com.teamshit.PresenterView.GetNewFriendListView;
import teams.xianlin.com.teamshit.PresenterView.OperateFriendView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.db.DBManager;
import teams.xianlin.com.teamshit.db.Friend;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/7/28.
 */
public class NewFriendActivity extends BaseActivity implements OnItemClickListener<GetNewFriendListResp.Friend>, OperateFriendView, GetNewFriendListView {
    @Bind(R.id.lvi_new_friend)//好友列表
            ListView lviNewFriend;
    @Bind(R.id.navi_top)//导航栏
            TitleNavi naviTop;
    private NewFriendAdapter newFriendAdapter;//适配器

    private OperateFriendPresenter operateFriendPresenter;//操作好友

    private GetNewFriendListPresenter getNewFriendListPresenter;//获取好友列表

    private List<GetNewFriendListResp.Friend> listData;//获取好友列表集合

    private int mPosition;//操作好友的位置

    private GetNewFriendListResp.Friend mFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
        getNewFriendListPresenter.loadData(mActivity);
    }

    @Override
    public void initialView() {
        super.initialView();
        naviTop.setClickCallBack(this);
        operateFriendPresenter = new OperateFriendPresenter(this);
        getNewFriendListPresenter = new GetNewFriendListPresenter(this);
    }

    @Override
    protected void initialData() {
        listData = new ArrayList<>();
    }

    @Override
    protected void inflateView() {
        naviTop.setBackTitle("新的朋友");
    }

    @Override
    public void onItemClick(View view, int position, GetNewFriendListResp.Friend friend) {
        mFriend = friend;
        switch (view.getId()) {
            case R.id.btn_confirm:
                mPosition = position;
                operateFriendPresenter.loadData(friend.getUserId(), friend.getMessage(), friend.getApplyId(), 2, mActivity);
                break;
            case R.id.item_layout_friend:
                mPosition = position;
                Intent detailIntent = new Intent(mActivity, FriendDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.Friend_Detail_Type, 2);
                bundle.putString(Constant.Target_Id, String.valueOf(mFriend.getUserId()));
                detailIntent.putExtras(bundle);
                startActivity(detailIntent);
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position, GetNewFriendListResp.Friend model) {

    }

    @Override
    public void onLoadSucess(OperateFriendResp operateFriendResp) {
        listData.get(mPosition).setStatus(1);
        newFriendAdapter.updateProgressPartly(lviNewFriend, mPosition);
        DBManager.getInstance(mActivity).getDaoSession().getFriendDao().insertOrReplace(new Friend(
                String.valueOf(mFriend.getUserId()),
                mFriend.getNickname(),
                mFriend.getPortraitUri(),
                mFriend.getNickname(),
                null,
                null
        ));
        EventBus.getDefault().post(new OperatorFriendEvent(1, mFriend.getUserId()));
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        if (errorMsg.getRespCode() == HttpDefine.Get_New_Friend_List_Resp) {
            mVaryViewHelper.showEmptyView();
        }
    }

    @Override
    public void showOperateFriendProgress() {
        loadDialog.show();
    }

    @Override
    public void hideOperateFriendProgress() {
        loadDialog.hide();
    }

    @Override
    public void onLoadSucess(GetNewFriendListResp getnewFriendListResp) {
        List<GetNewFriendListResp.Friend> friendList = getnewFriendListResp.getFriendList();
        if (friendList == null || friendList.size() == 0) {
            mVaryViewHelper.showEmptyView();
            return;
        }
        listData.clear();
        listData.addAll(friendList);
        if (newFriendAdapter == null) {
            newFriendAdapter = new NewFriendAdapter(NewFriendActivity.this, listData);
            lviNewFriend.setAdapter(newFriendAdapter);
            newFriendAdapter.setOnItemClickListener(this);
        } else {
            newFriendAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showGetNewFriendListProgress() {
        loadDialog.show();
    }

    @Override
    public void hideGetNewFriendListProgress() {
        loadDialog.hide();
    }

}
