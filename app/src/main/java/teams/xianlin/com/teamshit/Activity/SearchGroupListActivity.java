package teams.xianlin.com.teamshit.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.Adapter.SearchGroupListAdapter;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.Interface.OnItemClickListener;
import teams.xianlin.com.teamshit.NetWorkResp.SearchGroupListResp;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.widget.RecyclerView.DividerGridItemDecoration;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/8/10.
 * 搜索群组列表
 */
public class SearchGroupListActivity extends BaseActivity implements OnItemClickListener<SearchGroupListResp.GroupList> {

    @Bind(R.id.navi_top)
    TitleNavi naviTop;
    @Bind(R.id.rvi_group)
    RecyclerView rviGroup;

    private SearchGroupListResp mSearchGroupListResp;

    private SearchGroupListAdapter mSearchGroupListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group_list);
        ButterKnife.bind(this);
        initialData();
        inflateView();
    }

    @Override
    protected void initialData() {
        mSearchGroupListResp = (SearchGroupListResp) getIntent().getExtras().getSerializable(Constant.Group_List_Bean);
        rviGroup.setLayoutManager(new LinearLayoutManager(this));
        rviGroup.addItemDecoration(new DividerGridItemDecoration(this));
    }

    @Override
    protected void inflateView() {
        naviTop.setClickCallBack(this);
        naviTop.setBackTitle("查找结果");
        if (mSearchGroupListAdapter == null) {
            mSearchGroupListAdapter = new SearchGroupListAdapter(mActivity, mSearchGroupListResp.getGroupList());
            mSearchGroupListAdapter.setOnItemClickListener(this);
            rviGroup.setAdapter(mSearchGroupListAdapter);
        }
    }

    @Override
    public void onItemClick(View view, int position, SearchGroupListResp.GroupList model) {

    }

    @Override
    public void onItemLongClick(View view, int position, SearchGroupListResp.GroupList model) {

    }
}
