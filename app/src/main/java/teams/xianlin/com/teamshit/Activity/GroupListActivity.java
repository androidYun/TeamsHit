package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.Adapter.GroupListAdapter;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.Interface.OnItemClickListener;
import teams.xianlin.com.teamshit.NetWorkResp.GetGroupListResp;
import teams.xianlin.com.teamshit.PinYing.GroupGameComParator;
import teams.xianlin.com.teamshit.Presenter.GetGroupListPresenter;
import teams.xianlin.com.teamshit.PresenterView.GetGroupListView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.db.DBManager;
import teams.xianlin.com.teamshit.db.Groups;
import teams.xianlin.com.teamshit.widget.RecyclerView.DividerGridItemDecoration;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/9/7.
 */
public class GroupListActivity extends BaseActivity implements GetGroupListView, OnItemClickListener<Groups> {

    @Bind(R.id.navi_top)
    TitleNavi naviTop;
    @Bind(R.id.rvi_group)
    RecyclerView rviGroup;

    LinearLayoutManager layoutManager;
    private List<Groups> groupsList;

    private GroupListAdapter groupListAdapter;

    private GroupGameComParator groupGameComParator;

    private GetGroupListPresenter getGroupListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list_layout);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {

    }

    @Override
    protected void initialData() {
        getGroupListPresenter = new GetGroupListPresenter(this);
        layoutManager = new LinearLayoutManager(mActivity);
        rviGroup.setLayoutManager(layoutManager);
        rviGroup.addItemDecoration(new DividerGridItemDecoration(mActivity));
        groupGameComParator = GroupGameComParator.getInstance();
    }

    @Override
    protected void inflateView() {
        naviTop.setClickCallBack(this);
        naviTop.setBackTitle("群组列表");
        groupsList = DBManager.getInstance(mActivity).getDaoSession().getGroupsDao().loadAll();
        Collections.sort(groupsList, groupGameComParator);
        groupListAdapter = new GroupListAdapter(mActivity, groupsList);
        groupListAdapter.setOnItemClickListener(this);
        rviGroup.setAdapter(groupListAdapter);
    }

    @Override
    public void onLoadSucess(GetGroupListResp getGroupListResp) {
        if (getGroupListResp != null && getGroupListResp.getGroupList() != null && getGroupListResp.getGroupList().size() > 0) {//插入数据
            List<GetGroupListResp.Group> groupList = getGroupListResp.getGroupList();
            for (GetGroupListResp.Group group : groupList) {
                DBManager.getInstance(mActivity).getDaoSession().getGroupsDao().insertOrReplace(
                        new Groups(group.getGroupId(), group.getGroupName(), group.getPortraitUri(), group.getRole(), group.getGroupType(), group.getCreatorTime(), group.getIntroduce(), group.getNumber()));
                groupsList = DBManager.getInstance(mActivity).getDaoSession().getGroupsDao().loadAll();
                Collections.sort(groupsList, groupGameComParator);
                groupListAdapter = new GroupListAdapter(mActivity, groupsList);
            }
        }
        if (groupListAdapter != null) {
            groupListAdapter = new GroupListAdapter(mActivity, groupsList);
            rviGroup.setAdapter(groupListAdapter);
        } else {
            groupListAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {

    }

    @Override
    public void showGeGroupssProgress() {

    }

    @Override
    public void hideGetGroupsProgress() {

    }

    @Override
    public void onItemClick(View view, int position, Groups model) {
        Intent startChatroomIntent = new Intent(GroupListActivity.this, ChatRoomActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.GroupBean, model);
        startChatroomIntent.putExtras(bundle);
        startActivity(startChatroomIntent);
    }

    @Override
    public void onItemLongClick(View view, int position, Groups model) {

    }
}
