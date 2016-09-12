package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.rong.eventbus.EventBus;
import teams.xianlin.com.teamshit.Adapter.FriendCircleAdapter;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.CommentItemBean;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.Bean.FavoriteListBean;
import teams.xianlin.com.teamshit.Bean.ToReplyUserBean;
import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.EventBus.FriendCircleMessageEvent;
import teams.xianlin.com.teamshit.EventBus.PublishFriendCircleEvent;
import teams.xianlin.com.teamshit.Interface.OnItemClickListener;
import teams.xianlin.com.teamshit.Interface.OnMoreListener;
import teams.xianlin.com.teamshit.NetWorkResp.CancleThumbsUpResp;
import teams.xianlin.com.teamshit.NetWorkResp.DeleteCommentResp;
import teams.xianlin.com.teamshit.NetWorkResp.DeleteTakeResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleListResp;
import teams.xianlin.com.teamshit.NetWorkResp.PublishCommentResp;
import teams.xianlin.com.teamshit.NetWorkResp.ThumbsUpResp;
import teams.xianlin.com.teamshit.Presenter.GetFriendCircleListPresenter;
import teams.xianlin.com.teamshit.Presenter.OperateFriendCirclePresenter;
import teams.xianlin.com.teamshit.PresenterView.GetFriendCircleListView;
import teams.xianlin.com.teamshit.PresenterView.OperateFriendCircleView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.SysUtils;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.db.DBManager;
import teams.xianlin.com.teamshit.db.MessageUser;
import teams.xianlin.com.teamshit.widget.FriendCircle.CommentListView;
import teams.xianlin.com.teamshit.widget.FriendCircle.SuperRecyclerView;
import teams.xianlin.com.teamshit.widget.RecyclerView.DividerGridItemDecoration;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/8/25.
 */
public class FriendCircleActivity extends BaseActivity implements GetFriendCircleListView, OperateFriendCircleView, OnItemClickListener<GetFriendCircleListResp.FriendCircleItem> {
    private final static int Head_Item_Position = 0;//头部位置

    private TitleNavi titleNavi;

    private RelativeLayout bodyLayout;

    private SuperRecyclerView recyclerView;

    private LinearLayout editTextBodyLl;//评论框

    private EditText circleEt;

    private Button sendIv;

    private GetFriendCircleListPresenter getFriendCircleListPresenter;

    private OperateFriendCirclePresenter operateFriendCirclePresenter;

    private FriendCircleAdapter friendCircleAdapter;

    private LinearLayoutManager layoutManager;//布局管理

    private List<GetFriendCircleListResp.FriendCircleItem> mList;

    private int mCurPage = 1;//当前个数

    private int mCurCount = 10;//当前页数

    private int mAllCurPage;//总页数

    private int mAllCount;//总个数

    private int mFavoritePosition;//点赞的位置

    private GetFriendCircleListResp.FriendCircleItem mFavoritePositionFriendCircleItem;//点赞的那个Item

    private GetFriendCircleListResp.FriendCircleItem mCommentFriendCircleItem;//评论那个Item

    private GetFriendCircleListResp.FriendCircleItem mDeleteCommentFriendCircleItem;//删除评论那个Item

    private GetFriendCircleListResp.FriendCircleItem mDeleteTakeFriendCircleItem;//删除评论那个Item

    private int mCommentPosition;//评论在说说的位置

    private int mDeleteCommentPosition;//删除评论的位置

    private int mDeleteCommentFriendItemPosition;//删除评论在说说中的位置

    private int mCommentFriendItemPosition;//这个说说在所有说说的位置

    private int mDeleteTakeFriendItemPosition;//删除评论在说说中的位置

    private int selectCircleItemH;//Item宽度

    private int selectCommentItemOffset;//评论Ieam的宽度

    private int currentKeyboardH;

    private int screenHeight;

    private int editTextBodyHeight;

    private String mCommentType = "";//评论类型


    private FriendCircleAdapter.CommentData commentData;//评论参数数据

    private CommentItemBean commentItemBean = null;//评论Id

    private String mCommentContent;//评论内容
    private int Update_Type = 1;//1是刷新  2  是加载

    private long mCount;//新信息数量

    private UserBean mNewMessageUserBean;//最新消息的UserBean


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_circle_layout);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        titleNavi = (TitleNavi) findViewById(R.id.navi_friend);
        bodyLayout = (RelativeLayout) findViewById(R.id.bodyLayout);
        recyclerView = (SuperRecyclerView) findViewById(R.id.recyclerView);
        editTextBodyLl = (LinearLayout) findViewById(R.id.editTextBodyLl);
        circleEt = (EditText) findViewById(R.id.circleEt);
        sendIv = (Button) findViewById(R.id.sendIv);
    }

    @Override
    protected void initialData() {
        initialUbRead();//初始记录
        mList = new ArrayList<>();
        getFriendCircleListPresenter = new GetFriendCircleListPresenter(this);
        operateFriendCirclePresenter = new OperateFriendCirclePresenter(this);
        layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(mActivity));
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        getFriendCircleListPresenter.loadData(mActivity, mCurPage, mCurCount);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editTextBodyLl.getVisibility() == View.VISIBLE) {

                    return true;
                }
                return false;
            }
        });
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//刷新请求
            @Override
            public void onRefresh() {//刷新
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCurPage = 1;
                        Update_Type = 1;
                        getFriendCircleListPresenter.loadData(mActivity, mCurPage, mCurCount);
                        recyclerView.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        recyclerView.setupMoreListener(new OnMoreListener() {//上拉加载
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurPage <= mAllCurPage) {
                            Update_Type = 2;
                            getFriendCircleListPresenter.loadData(mActivity, mCurPage, mCurCount);
                        } else {

                        }
                        recyclerView.setLoadingMore(false);
                    }
                }, 1000);

            }
        }, 1);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Glide.with(mActivity).resumeRequests();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(mActivity).pauseRequests();
                }
            }
        });
        friendCircleAdapter = new FriendCircleAdapter(this, operateFriendCirclePresenter, mList);//创建适配器
        friendCircleAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(friendCircleAdapter);
        sendIv.setOnClickListener(this);
        setViewTreeObserver();
    }

    @Override
    protected void inflateView() {
        titleNavi.setBackTitle("朋友圈");
        titleNavi.setFinish("发表说说");
        titleNavi.setClickCallBack(this);
        friendCircleAdapter.notifyDataSetChanged();
    }

    public void showEditTextBodyVisible(int visibility, int position, String CommnentType) {
        editTextBodyLl.setVisibility(visibility);
        measureCircleItemHighAndCommentItemOffset(position, CommnentType);
        if (View.VISIBLE == visibility) {
            editTextBodyLl.requestFocus();
            //弹出键盘
            SysUtils.getInatcne().showSoftInput(mActivity, circleEt);
        } else if (View.GONE == visibility) {
            //隐藏键盘
            SysUtils.getInatcne().hideSoftInput(mActivity, circleEt);
        }
    }

    public void initialUbRead() {
        mCount = DBManager.getInstance(mActivity).getDaoSession().getMessageUserDao().count();
        if (mCount > 0) {
            MessageUser messageUser = DBManager.getInstance(mActivity).getDaoSession().getMessageUserDao().loadByRowId(mCount - 1);
            if (messageUser != null) {
                mNewMessageUserBean = new UserBean();
                mNewMessageUserBean.setUserId(Long.valueOf(messageUser.getUserId()));
                mNewMessageUserBean.setPortraitUri(messageUser.getPortraitUri());
                mNewMessageUserBean.setDisplayName(messageUser.getDisplayName());
            }
        }
    }

    private void setViewTreeObserver() {
        bodyLayout = (RelativeLayout) findViewById(R.id.bodyLayout);
        final ViewTreeObserver swipeRefreshLayoutVTO = bodyLayout.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                bodyLayout.getWindowVisibleDisplayFrame(r);
                int statusBarH = getStatusBarHeight();//状态栏高度
                int screenH = bodyLayout.getRootView().getHeight();
                if (r.top != statusBarH) {
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);
//                Log.d("Main", "screenH＝ " + screenH + " &keyboardH = " + keyboardH + " &r.bottom=" + r.bottom + " &top=" + r.top + " &statusBarH=" + statusBarH);
                if (keyboardH == currentKeyboardH) {//有变化时才处理，否则会陷入死循环
                    return;
                }

                currentKeyboardH = keyboardH;
                screenHeight = screenH;//应用屏幕的高度
                editTextBodyHeight = editTextBodyLl.getHeight();
                if (keyboardH < 150) {//说明是隐藏键盘的情况
                    showEditTextBodyVisible(View.GONE, 0, null);
                    return;
                }
                //偏移listview
                if (layoutManager != null && commentData != null) {
                    layoutManager.scrollToPositionWithOffset(commentData.getPosition(), getListviewOffset(commentData.getCommentType()));
                }
            }
        });
    }

    /**
     * 测量偏移量
     *
     * @param mCommentType
     * @return
     */
    private int getListviewOffset(String mCommentType) {
        if (StringUtils.isBlank(mCommentType))
            return 0;
        //这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
        //int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight;
        int listviewOffset = screenHeight - selectCircleItemH - currentKeyboardH - editTextBodyHeight;
        if (mCommentType.equals(Constant.Comment_Reply)) {
            //回复评论的情况
            listviewOffset = listviewOffset + selectCommentItemOffset;
        }
        return listviewOffset;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void measureCircleItemHighAndCommentItemOffset(int position, String CommnentType) {
        if (StringUtils.isBlank(CommnentType)) {
            return;
        }
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        //只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem = layoutManager.getChildAt(position + friendCircleAdapter.HEADVIEW_SIZE - firstPosition);
        if (selectCircleItem != null) {
            selectCircleItemH = selectCircleItem.getHeight();
        }
        if (CommnentType.equals(Constant.Comment_Reply) && selectCircleItem != null) {
            //回复评论的情况
            CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
            if (commentLv != null) {
                //找到要回复的评论view,计算出该view距离所属动态底部的距离
                View selectCommentItem = commentLv.getChildAt(position);
                if (selectCommentItem != null) {
                    //选择的commentItem距选择的CircleItem底部的距离
                    selectCommentItemOffset = 0;
                    View parentView = selectCommentItem;
                    do {
                        int subItemBottom = parentView.getBottom();
                        parentView = (View) parentView.getParent();
                        if (parentView != null) {
                            selectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                        }
                    } while (parentView != null && parentView != selectCircleItem);
                }
            }
        }
    }

    @Override
    public void onRightClick() {
        Intent publishIntent = new Intent(mActivity, PublishFriendCircleActivity.class);
        startActivity(publishIntent);
    }

    @Override
    public void onLoadSucess(GetFriendCircleListResp getFriendCircleListResp) {
        List<GetFriendCircleListResp.FriendCircleItem> friendCircleList = getFriendCircleListResp.getFriendCircleList();
        if (friendCircleList != null && friendCircleList.size() != 0) {
            if (Update_Type == 1) {
                mList.clear();
                GetFriendCircleListResp.FriendCircleItem friendCircleItem = getFriendCircleListResp.new FriendCircleItem();
                friendCircleItem.setUser(mNewMessageUserBean);
                friendCircleItem.setMessageNumber((int) mCount);
                mList.add(friendCircleItem);
                mList.addAll(friendCircleList);
            } else if (Update_Type == 2) {
                mList.addAll(friendCircleList);
            }
            friendCircleAdapter.notifyDataSetChanged();
        }
        mCurPage++;
        mAllCurPage = getFriendCircleListResp.getAllCur();
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        if (errorMsg.getErrorCode() == HttpDefine.Get_Friend_Circle_List_Resp) {
            recyclerView.removeMoreListener();
            recyclerView.hideMoreProgress();
        }

    }

    @Override
    public void showGetFriendCircleListProgress() {
        loadDialog.show();
    }

    @Override
    public void hideGetFriendCircleListProgress() {
        loadDialog.hide();
    }

    @Override
    public void onLoadSucess(ThumbsUpResp thumbsUpResp) {
        UserBean userBean = getUserBean();
        FavoriteListBean favoriteListBean = new FavoriteListBean();
        favoriteListBean.setUser(userBean);
        favoriteListBean.setFavoriteId(thumbsUpResp.getFavoriteId());
        if (mList != null && mList.get(mFavoritePosition) != null && mList.get(mFavoritePosition).getFavoriteLists() != null) {
            mList.get(mFavoritePosition).getFavoriteLists().add(favoriteListBean);
            friendCircleAdapter.notifyItemChanged(mFavoritePosition);
        }
    }

    @Override
    public void onLoadSucess(CancleThumbsUpResp cancleThumbsUpResp) {
        UserBean userBean = getUserBean();
        FavoriteListBean favoriteListBean = null;
        if (mFavoritePositionFriendCircleItem != null) {
            favoriteListBean = mFavoritePositionFriendCircleItem.getFavoriteBeanForFavoriteId(String.valueOf(userBean.getUserId()));
        }
        if (favoriteListBean != null && mFavoritePositionFriendCircleItem != null && mFavoritePositionFriendCircleItem.getFavoriteLists() != null) {
            mFavoritePositionFriendCircleItem.getFavoriteLists().remove(favoriteListBean);
            friendCircleAdapter.notifyItemChanged(mFavoritePosition);
        }
    }

    @Override
    public void onLoadSucess(PublishCommentResp publishCommentResp) {
        ToReplyUserBean toReplyUser = null;
        UserBean userBean = getUserBean();
        long commentId = publishCommentResp.getCommentId();
        if (mCommentType.equals(Constant.Comment)) {
            commentItemBean = new CommentItemBean();
            commentItemBean.setUser(userBean);
            commentItemBean.setCommentId(commentId);
            commentItemBean.setContent(mCommentContent);
            if (mList.get(mCommentFriendItemPosition).getCommentLists() == null) {//如果评论为空的话 ，就创建一个评论列表
                List<CommentItemBean> commentLists = new ArrayList<>();
                commentLists.add(commentItemBean);
                mList.get(mCommentFriendItemPosition).setCommentLists(commentLists);//添加评论列表
            } else {
                mList.get(mCommentFriendItemPosition).getCommentLists().add(commentItemBean);
            }

            friendCircleAdapter.notifyItemChanged(mCommentFriendItemPosition);
        } else if (mCommentType.equals(Constant.Comment_Reply)) {
            if (commentItemBean != null) {//点击那个人的评论把他当成回复人
                toReplyUser = new ToReplyUserBean();
                UserBean user = commentItemBean.getUser();
                if (user != null) {
                    toReplyUser.setDisplayName(user.getDisplayName());
                    toReplyUser.setPortraitUri(user.getPortraitUri());
                    toReplyUser.setUserId(user.getUserId());
                }
            }
            commentItemBean.setToReplyUser(toReplyUser);
            commentItemBean.setUser(userBean);
            commentItemBean.setCommentId(commentId);
            commentItemBean.setContent(mCommentContent);
            mList.get(mCommentFriendItemPosition).getCommentLists().add(commentItemBean);
            friendCircleAdapter.notifyItemChanged(mCommentFriendItemPosition);
        }

    }

    @Override
    public void onLoadSucess(DeleteTakeResp deleteTakeResp) {
        if (mList != null && mList.size() >= mDeleteCommentFriendItemPosition) {
            mList.remove(mDeleteCommentFriendItemPosition);
        }
        friendCircleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadSucess(DeleteCommentResp deleteCommentResp) {
        CommentItemBean mDeleteCommentItemBean = null;
        if (mDeleteCommentFriendCircleItem != null && mDeleteCommentFriendCircleItem.getCommentLists() != null && mDeleteCommentFriendCircleItem.getCommentLists().size() != 0) {
            mDeleteCommentItemBean = mDeleteCommentFriendCircleItem.getCommentLists().get(mDeleteCommentPosition);
        }
        if (mList != null && mList.size() != 0 && mList.get(mDeleteCommentFriendItemPosition) != null && mList.get(mDeleteCommentFriendItemPosition).getCommentLists() != null && mList.get(mDeleteCommentFriendItemPosition).getCommentLists().size() != 0 && mDeleteCommentItemBean != null) {
            mList.get(mDeleteCommentFriendItemPosition).getCommentLists().remove(mDeleteCommentItemBean);
            friendCircleAdapter.notifyItemChanged(mDeleteCommentFriendItemPosition);
        }

    }

    @Override
    public void showRegisterProgress() {
        loadDialog.show();
    }

    @Override
    public void hideRegsiterProgress() {
        loadDialog.hide();
    }

    public void onEventMainThread(PublishFriendCircleEvent event) {//更新说说列表
        GetFriendCircleListResp.FriendCircleItem friendCircleItem = event.getFriendCircleItem();
        if (friendCircleItem != null) {
            mList.add(1, friendCircleItem);
            friendCircleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(View view, int position, GetFriendCircleListResp.FriendCircleItem model) {
        LogUtil.d("" + model + "ff" + view.getId() + position
        );
        String UserId = null;
        String TakeId = null;
        if (model == null || model.getUser() == null) {
            return;
        }
        UserId = String.valueOf(model.getUser().getUserId());
        TakeId = String.valueOf(model.getTakeId());
        switch (view.getId()) {
            case R.id.digBtn:
                mFavoritePosition = position;
                mFavoritePositionFriendCircleItem = model;//点赞的Item
                String tag = (String) view.getTag();//获取是赞还是取消点赞
                if (tag.equals(Constant.Favorite)) {
                    operateFriendCirclePresenter.ThumbsUpData(mActivity, UserId, TakeId);
                } else if (tag.equals(Constant.Cancle_Favorite)) {
                    operateFriendCirclePresenter.cancleThumbsUpData(mActivity, UserId, TakeId);
                }
                break;
            case R.id.commentBtn://直接评论
            case R.id.commentList://回复评论
                mCommentFriendCircleItem = model;//评论赋值
                commentData = (FriendCircleAdapter.CommentData) view.getTag();
                mCommentType = commentData.getCommentType();
                mCommentFriendItemPosition = commentData.getPosition();
                mCommentPosition = position;
                showEditTextBodyVisible(View.VISIBLE, mCommentPosition, mCommentType);
                break;
            case R.id.deleteTv://删除评论
                mDeleteCommentPosition = (int) view.getTag();//评论的位置
                mDeleteCommentFriendItemPosition = position;//这个是说说的位置
                mDeleteCommentFriendCircleItem = model;
                String mTakeId = "";
                String mCommentId = "";
                if (mCommentFriendCircleItem != null && mCommentFriendCircleItem.getCommentLists() != null) {
                    mTakeId = String.valueOf(mCommentFriendCircleItem.getTakeId());
                    CommentItemBean commentItemBean = mCommentFriendCircleItem.getCommentLists().get(mCommentPosition);
                    if (commentItemBean != null) {
                        mCommentId = String.valueOf(commentItemBean.getCommentId());
                    }
                }
                operateFriendCirclePresenter.deleteCommentData(mActivity, mCommentId, mTakeId);
                break;
            case R.id.deleteBtn://删除说说
                mDeleteTakeFriendCircleItem = model;
                mDeleteTakeFriendItemPosition = position;
                UserBean userBean = getUserBean();
                if (userBean != null) {
                    operateFriendCirclePresenter.deleteTakeData(mActivity, String.valueOf(userBean.getUserId()), TakeId);
                }
                break;
            case R.id.layout:
                DBManager.getInstance(mActivity).getDaoSession().getMessageUserDao().deleteAll();
                Intent FriendCircleMessageIntent = new Intent(mActivity, FriendCircleMessageActivity.class);
                startActivity(FriendCircleMessageIntent);
                EventBus.getDefault().post(new FriendCircleMessageEvent());
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position, GetFriendCircleListResp.FriendCircleItem model) {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.sendIv:
                mCommentContent = circleEt.getText().toString();//赋值评论内容，添加评论用得到
                if (mCommentFriendCircleItem == null && mCommentFriendCircleItem.getUser() == null) {
                    return;
                }
                String takeUserId = String.valueOf(mCommentFriendCircleItem.getUser().getUserId());
                String TakeId = String.valueOf(mCommentFriendCircleItem.getTakeId());
                if (mCommentType.equals(Constant.Comment)) {
                    operateFriendCirclePresenter.publishCommentData(mActivity, "0", TakeId, takeUserId, "", 2, mCommentContent);
                } else if (mCommentType.equals(Constant.Comment_Reply)) {
                    String TargetId = "";
                    List<CommentItemBean> commentLists = mCommentFriendCircleItem.getCommentLists();
                    if (commentLists != null && commentLists.size() != 0) {
                        commentItemBean = commentLists.get(mCommentPosition);
                        UserBean toReplyUser = commentItemBean.getUser();
                        if (toReplyUser != null) {//回复那个人的IId不能为空
                            TargetId = String.valueOf(toReplyUser.getUserId());
                        }
                    }
                    operateFriendCirclePresenter.publishCommentData(mActivity, TargetId, TakeId, takeUserId, String.valueOf(commentItemBean.getCommentId()), 1, mCommentContent);
                }
                showEditTextBodyVisible(View.GONE, mCommentPosition, mCommentType);
                circleEt.setText("");
                if (commentItemBean != null) {
                    LogUtil.d("开心啊" + commentItemBean.getUser());
                }
                LogUtil.d("开心ddd啊" + commentItemBean);
                break;
        }
    }

    public void onEventMainThread(FriendCircleMessageEvent event) {//更新列表
        LogUtil.d("dfff" + mCount + "      " + mNewMessageUserBean.toString());
        initialUbRead();
        mList.get(Head_Item_Position).setMessageNumber((int) mCount);
        mList.get(Head_Item_Position).setUser(mNewMessageUserBean);
        friendCircleAdapter.notifyItemChanged(Head_Item_Position);
    }
}
