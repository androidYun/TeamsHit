package teams.xianlin.com.teamshit.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleMessageResp;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.widget.FriendCircle.GlideCircleTransform;

/**
 * Created by Administrator on 2016/9/1.
 */
public class FriendCircleMessageAdapter extends BaseRecyclerAdapter<GetFriendCircleMessageResp.FriendCircleMessageItem> {
    private List<GetFriendCircleMessageResp.FriendCircleMessageItem> mList;

    private Context mContext;

    public FriendCircleMessageAdapter(Context context, List<GetFriendCircleMessageResp.FriendCircleMessageItem> datas) {
        super(context, datas);
        this.mList = datas;
        this.mContext = context;
    }


    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder viewHolder = null;
        View view = inflateView(R.layout.list_item_friend_circle_infor_comment_layout, parent);
        viewHolder = new CommentViewHolder(view);
        return viewHolder;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, GetFriendCircleMessageResp.FriendCircleMessageItem friendCircleMessageItem, int position) {
        String contentUrl = "";
        String commentContent = "";
        String takeContent = "";
        UserBean userBean = null;
        int isFavoritAndComment = 1;

        CommentViewHolder viewHolder = (CommentViewHolder) holder;
        if (friendCircleMessageItem != null) {
            contentUrl = friendCircleMessageItem.getTakePhoto();
            commentContent = friendCircleMessageItem.getCommentContent();
            takeContent = friendCircleMessageItem.getTakeContent();
            isFavoritAndComment = friendCircleMessageItem.getIsFavoriteAndComenmt();
            userBean = friendCircleMessageItem.getUser();
        }
        if (userBean != null && !StringUtils.isBlank(userBean.getPortraitUri())) {
            userBean = friendCircleMessageItem.getUser();
            String portraitUri = userBean.getPortraitUri();
            String displayName = userBean.getDisplayName();
            Glide.with(mContext).load(portraitUri).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(mContext)).into(viewHolder.imgHead);
            viewHolder.txtName.setText(displayName + "");
        }
        if (!StringUtils.isBlank(friendCircleMessageItem.getTakePhoto())) {
            viewHolder.imgRightConetnt.setVisibility(View.VISIBLE);
            viewHolder.txtRightConetnt.setVisibility(View.GONE);
            Glide.with(mContext).load(contentUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(mContext)).into(viewHolder.imgRightConetnt);
        } else {
            viewHolder.imgRightConetnt.setVisibility(View.GONE);
            viewHolder.txtRightConetnt.setVisibility(View.VISIBLE);
            viewHolder.txtRightConetnt.setText("" + takeContent);
        }
        if (isFavoritAndComment == 1) {
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_favorite);
/// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            viewHolder.txtComment.setCompoundDrawables(drawable, null, null, null);
            viewHolder.txtComment.setText("");
        } else if (isFavoritAndComment == 2) {
            viewHolder.txtComment.setCompoundDrawables(null, null, null, null);
            viewHolder.txtComment.setText(commentContent + "");
        }
    }
}


class CommentViewHolder extends BaseRecyclerViewHolder {
    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.txt_name)
    TextView txtName;
    @Bind(R.id.txt_comment)
    TextView txtComment;
    @Bind(R.id.txt_time)
    TextView txtTime;
    @Bind(R.id.txt_right_conetnt)
    TextView txtRightConetnt;
    @Bind(R.id.img_right_conetnt)
    ImageView imgRightConetnt;

    public CommentViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
