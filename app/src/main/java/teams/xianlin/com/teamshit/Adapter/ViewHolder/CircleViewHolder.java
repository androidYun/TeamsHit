package teams.xianlin.com.teamshit.Adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import teams.xianlin.com.teamshit.Adapter.BaseRecyclerViewHolder;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.widget.FriendCircle.CommentListView;
import teams.xianlin.com.teamshit.widget.FriendCircle.ExpandTextView;
import teams.xianlin.com.teamshit.widget.FriendCircle.PraiseListView;
import teams.xianlin.com.teamshit.widget.Popwindow.SnsPopupWindow;

/**
 * Created by Administrator on 2016/8/26.
 */
public abstract class CircleViewHolder extends BaseRecyclerViewHolder {
    public ImageView headIv;
    public TextView nameTv;
    public TextView urlTipTv;
    /**
     * 动态的内容
     */
    public ExpandTextView contentTv;
    public TextView timeTv;
    public TextView deleteBtn;
    public ImageView snsBtn;
    /**
     * 点赞列表
     */
    public PraiseListView praiseListView;

    public LinearLayout digCommentBody;
    public View digLine;

    /**
     * 评论列表
     */
    public CommentListView commentList;
    // ===========================
    public SnsPopupWindow snsPopupWindow;

    public CircleViewHolder(View itemView, int viewType) {
        super(itemView);
        ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewStub);

        initSubView(viewType, viewStub);

        headIv = (ImageView) itemView.findViewById(R.id.headIv);
        nameTv = (TextView) itemView.findViewById(R.id.nameTv);
        digLine = itemView.findViewById(R.id.lin_dig);

        contentTv = (ExpandTextView) itemView.findViewById(R.id.contentTv);
        urlTipTv = (TextView) itemView.findViewById(R.id.urlTipTv);
        timeTv = (TextView) itemView.findViewById(R.id.timeTv);
        deleteBtn = (TextView) itemView.findViewById(R.id.deleteBtn);
        snsBtn = (ImageView) itemView.findViewById(R.id.snsBtn);
        praiseListView = (PraiseListView) itemView.findViewById(R.id.praiseListView);

        digCommentBody = (LinearLayout) itemView.findViewById(R.id.digCommentBody);
        commentList = (CommentListView) itemView.findViewById(R.id.commentList);

        snsPopupWindow = new SnsPopupWindow(itemView.getContext());
    }

    public abstract void initSubView(int viewType, ViewStub viewStub);

}
