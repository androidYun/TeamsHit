package teams.xianlin.com.teamshit.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.Adapter.FriendCircleMessageAdapter;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.Interface.OnMoreListener;
import teams.xianlin.com.teamshit.NetWorkResp.DeleteFriendCircleMessageResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleMessageResp;
import teams.xianlin.com.teamshit.Presenter.DeleteFriendCircleMessagePresenter;
import teams.xianlin.com.teamshit.Presenter.GetFriendCircleMessagePresenter;
import teams.xianlin.com.teamshit.PresenterView.DeleteFriendCircleMessageView;
import teams.xianlin.com.teamshit.PresenterView.GetFriendCircleMessageView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.widget.FriendCircle.SuperRecyclerView;
import teams.xianlin.com.teamshit.widget.RecyclerView.DividerGridItemDecoration;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/9/1.
 */
public class FriendCircleMessageActivity extends BaseActivity implements GetFriendCircleMessageView, DeleteFriendCircleMessageView {
    @Bind(R.id.navi_top)
    TitleNavi naviTop;
    @Bind(R.id.recyclerView)
    SuperRecyclerView recyclerView;
    private GetFriendCircleMessagePresenter getFriendCircleMessagePresenter;

    private DeleteFriendCircleMessagePresenter deleteFriendCircleMessagePresenter;

    private FriendCircleMessageAdapter friendCircleMessageAdapter;

    private long mMessageId = 0;

    private int mCurPage = 0;//当前页个数

    private int mCurCount = 10;//当前页数

    private int mAllCurPage;//总页数

    private int mAllCount;//总个数

    private List<GetFriendCircleMessageResp.FriendCircleMessageItem> mList;

    private LinearLayoutManager layoutManager;//线性管理

    private int Update_Type = 1;//1是刷新  2  是加载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_circle_message_layout);
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
        naviTop.setClickCallBack(this);
        getFriendCircleMessagePresenter = new GetFriendCircleMessagePresenter(this);
        deleteFriendCircleMessagePresenter = new DeleteFriendCircleMessagePresenter(this);
        mList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(mActivity));
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        getFriendCircleMessagePresenter.loadData(mActivity, mCurPage, mCurCount, mMessageId);
    }

    @Override
    protected void inflateView() {
        naviTop.setBackTitle("消息");
        naviTop.setFinish("清空");
        recyclerView.setupMoreListener(new OnMoreListener() {//上拉加载
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurPage <= mAllCurPage) {
                            getFriendCircleMessagePresenter.loadData(mActivity, mCurPage, mCurCount, mMessageId);
                            Update_Type = 2;
                        } else {

                        }
                        recyclerView.setLoadingMore(false);
                    }
                }, 1000);

            }
        }, 1);
    }

    @Override
    public void onRightClick() {
        super.onRightClick();
        deleteFriendCircleMessagePresenter.loadData(mActivity);
    }

    @Override
    public void onLoadSucess(DeleteFriendCircleMessageResp deleteFriendCircleMessgaeResp) {
        mList.clear();
        mVaryViewHelper.showEmptyView();
    }

    @Override
    public void showDeleteFriendMessageProgress() {
        loadDialog.show();
    }

    @Override
    public void hideDeleteFriendMessageProgress() {
        loadDialog.hide();
    }

    @Override
    public void onLoadSucess(GetFriendCircleMessageResp getFriendCircleMessgeResp) {
        mAllCurPage = getFriendCircleMessgeResp.getAllCur();
        mAllCount = getFriendCircleMessgeResp.getAllCount();
        List<GetFriendCircleMessageResp.FriendCircleMessageItem> friendCircleMessageList = getFriendCircleMessgeResp.getFriendCircleMessageList();
        mList.addAll(friendCircleMessageList);
        if (friendCircleMessageAdapter == null) {
            friendCircleMessageAdapter = new FriendCircleMessageAdapter(mActivity, mList);
            recyclerView.setAdapter(friendCircleMessageAdapter);
        } else {
            friendCircleMessageAdapter.notifyDataSetChanged();
        }
        mCurPage++;
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        if (errorMsg.getRespCode() == HttpDefine.Get_Friend_Circle_Message_Resp) {
            mVaryViewHelper.showEmptyView();
        }

    }

    @Override
    public void showGetFriendCircleMessageProgress() {
        loadDialog.show();
    }

    @Override
    public void hideGetFriendCircleMessageProgress() {
        loadDialog.hide();
    }
}
