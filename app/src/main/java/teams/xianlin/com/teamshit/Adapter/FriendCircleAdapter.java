package teams.xianlin.com.teamshit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.Activity.ImagePagerActivity;
import teams.xianlin.com.teamshit.Adapter.ViewHolder.CircleViewHolder;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.CommentItemBean;
import teams.xianlin.com.teamshit.Bean.FavoriteListBean;
import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleListResp;
import teams.xianlin.com.teamshit.Presenter.OperateFriendCirclePresenter;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.TeamsHitApplication;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.ImageLoadUtils;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.Utils.TimeUtils;
import teams.xianlin.com.teamshit.Utils.UrlUtils;
import teams.xianlin.com.teamshit.widget.DialogUtils.CommentDialog;
import teams.xianlin.com.teamshit.widget.FriendCircle.CommentListView;
import teams.xianlin.com.teamshit.widget.FriendCircle.GlideCircleTransform;
import teams.xianlin.com.teamshit.widget.FriendCircle.MultiImageView;
import teams.xianlin.com.teamshit.widget.FriendCircle.PraiseListView;
import teams.xianlin.com.teamshit.widget.Popwindow.SnsPopupWindow;

/**
 * Created by Administrator on 2016/8/26.
 */
public class FriendCircleAdapter extends BaseRecyclerAdapter<GetFriendCircleListResp.FriendCircleItem> {
    public final static int TYPE_HEAD = 0;


    public final static int TYPE_CONTENT = 1;


    public static final int HEADVIEW_SIZE = 1;


    private Context mContext;

    private OperateFriendCirclePresenter operateFriendCirclePresenter;

    public FriendCircleAdapter(Context context, OperateFriendCirclePresenter operateFriendCirclePresenter, List<GetFriendCircleListResp.FriendCircleItem> datas) {
        super(context, datas);
        this.operateFriendCirclePresenter = operateFriendCirclePresenter;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder viewHolder = null;
        if (viewType == TYPE_HEAD) {
            View headView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_friend_circle_hend_layout, parent, false);
            viewHolder = new HeaderViewHolder(headView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_friend_circle, parent, false);
            viewHolder = new ImageViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final GetFriendCircleListResp.FriendCircleItem friendCircleItem, final int position) {
        UserBean mCurUserBean = null;
        final String mCurUserId;
        if (mCurUserBean == null) {
            mCurUserBean = TeamHitContext.getInstance().getUserBean();
        }
        mCurUserId = String.valueOf(mCurUserBean.getUserId());//自己的id
        if (getItemViewType(position) == TYPE_HEAD) {//显示头部
            String mCurUserPortraitUri = mCurUserBean.getPortraitUri();
            String mCurUserName = mCurUserBean.getDisplayName();
            final HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            if (!StringUtils.isBlank(mCurUserPortraitUri)) {
                ImageLoadUtils.getInstance().with(mContext, mCurUserPortraitUri, viewHolder.imgHead);
            }
            viewHolder.txtName.setText("" + mCurUserName);
            if (friendCircleItem == null || friendCircleItem.getMessageNumber() == 0) {
                viewHolder.layout.setVisibility(View.GONE);
            } else {
                UserBean userBean = friendCircleItem.getUser();
                int messageNumber = friendCircleItem.getMessageNumber();
                if (userBean != null && !StringUtils.isBlank(userBean.getPortraitUri())) {
                    ImageLoadUtils.getInstance().with(mContext, userBean.getPortraitUri(), viewHolder.imgMessageHead);
                }
                viewHolder.txtNumberMessage.setText("" + messageNumber + "条新消息");
                viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(viewHolder.layout, position, friendCircleItem);
                    }
                });

            }
        } else {//显示消息体
            final String mTakeId = String.valueOf(friendCircleItem.getTakeId());
            final ImageViewHolder viewHolder = (ImageViewHolder) holder;
            final int circlePosition = position - HEADVIEW_SIZE;
            if (friendCircleItem == null) {
                return;
            }
            final String circleId = String.valueOf(friendCircleItem.getTakeId());//获取说说Id

            String name = friendCircleItem.getUser().getDisplayName();//获取自己的名字

            String headImg = friendCircleItem.getUser().getPortraitUri();//获取头像

            final String content = friendCircleItem.getTakeContent();

            String mTargetId = String.valueOf(friendCircleItem.getUser().getUserId());//发表说说用户的Id

            String createTime = TimeUtils.getInstance().convertTimeToFormat(friendCircleItem.getCreateTime());

            final List<FavoriteListBean> favortDatas = friendCircleItem.getFavoriteLists();

            final List<CommentItemBean> commentsDatas = friendCircleItem.getCommentLists();

            boolean hasFavort = friendCircleItem.hasFavort();

            boolean hasComment = friendCircleItem.hasComment();

            Glide.with(mContext).load(headImg).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(mContext)).into(viewHolder.headIv);
            viewHolder.nameTv.setText(name);
            viewHolder.timeTv.setText(createTime);

            if (!TextUtils.isEmpty(content)) {//判断内容时候为空
                viewHolder.contentTv.setText(UrlUtils.formatUrlString(content));
            }
            viewHolder.contentTv.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);

            if (mCurUserId.equals(mTargetId)) {//如果是自己可以显示删除按钮
                viewHolder.deleteBtn.setVisibility(View.VISIBLE);
            } else {
                viewHolder.deleteBtn.setVisibility(View.GONE);
            }
            viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//删除说说逻辑处理
                    //删除
                    if (operateFriendCirclePresenter != null) {
                        mOnItemClickListener.onItemClick(viewHolder.deleteBtn, position, friendCircleItem);
                    }
                    //处理删除逻辑
                }
            });
            if (hasFavort || hasComment) {
                if (hasFavort) {//处理点赞列表
                    viewHolder.praiseListView.setOnItemClickListener(new PraiseListView.OnItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            String userName = favortDatas.get(position).getUser().getDisplayName();
                            String userId = String.valueOf(favortDatas.get(position).getUser().getUserId());
                            Toast.makeText(TeamsHitApplication.getContext(), userName + " &id = " + userId, Toast.LENGTH_SHORT).show();
                        }
                    });
                    viewHolder.praiseListView.setDatas(favortDatas);
                    viewHolder.praiseListView.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.praiseListView.setVisibility(View.GONE);
                }
                if (hasComment) {//处理评论列表
                    viewHolder.commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                        @Override
                        public void onItemClick(int commentPosition) {
                            CommentItemBean commentItem = commentsDatas.get(commentPosition);
                            String commentUserId = "";
                            if (commentItem != null && commentItem.getUser() != null) {
                                commentUserId = String.valueOf(commentItem.getUser().getUserId());
                            }
                            if (mCurUserId.equals(commentUserId)) {//判断时候是自己的说说  是自己的说说的话 就可以删除
                                CommentDialog dialog = new CommentDialog(mContext, mOnItemClickListener, friendCircleItem, position, commentPosition);//复制或者删除自己的评论
                                dialog.show();
                            } else {//回复别人的评论
                                //回复请求
                                CommentData commentData = new CommentData(Constant.Comment_Reply, position);
                                viewHolder.commentList.setTag(commentData);
                                mOnItemClickListener.onItemClick(viewHolder.commentList, commentPosition, friendCircleItem);
                            }
                        }
                    });
                    viewHolder.commentList.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(int commentPosition) {
                            //长按进行复制或者删除
                            CommentItemBean commentItem = commentsDatas.get(commentPosition);
                            CommentDialog dialog = new CommentDialog(mContext, mOnItemClickListener, friendCircleItem, position, commentPosition);
                            dialog.show();
                        }
                    });
                    viewHolder.commentList.setDatas(commentsDatas);
                    viewHolder.commentList.setVisibility(View.VISIBLE);

                } else {
                    viewHolder.commentList.setVisibility(View.GONE);
                }
                viewHolder.digCommentBody.setVisibility(View.VISIBLE);
            } else {
                viewHolder.digCommentBody.setVisibility(View.GONE);
            }

            viewHolder.digLine.setVisibility(hasFavort && hasComment ? View.VISIBLE : View.GONE);

            final SnsPopupWindow snsPopupWindow = viewHolder.snsPopupWindow;
            //判断是否已点赞
            String curUserFavortId = friendCircleItem.getCurUserFavortId(mCurUserId);
            if (!TextUtils.isEmpty(curUserFavortId)) {
                snsPopupWindow.getmActionItems().get(0).mTitle = "取消";
            } else {
                snsPopupWindow.getmActionItems().get(0).mTitle = "赞";
            }
            snsPopupWindow.update();
            snsPopupWindow.setmItemClickListener(new PopupItemClickListener(position, friendCircleItem, mTakeId));
            viewHolder.snsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //弹出popupwindow
                    snsPopupWindow.showPopupWindow(view);
                }
            });

            viewHolder.urlTipTv.setVisibility(View.GONE);

            if (viewHolder instanceof ImageViewHolder) {
                final List<String> photos = new ArrayList<>();
                String photoStr = friendCircleItem.getPhotoLists();
                if (StringUtils.isBlank(photoStr)) {
                    return;
                }
                String[] split = photoStr.split(",");
                Collections.addAll(photos, split);

                if (photos != null && photos.size() > 0) {
                    ((ImageViewHolder) holder).multiImageView.setVisibility(View.VISIBLE);
                    ((ImageViewHolder) holder).multiImageView.setList(photos);
                    ((ImageViewHolder) holder).multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            //imagesize是作为loading时的图片size
                            ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                            ImagePagerActivity.startImagePagerActivity(mContext, photos, position, imageSize);
                        }
                    });
                } else {
                    ((ImageViewHolder) holder).multiImageView.setVisibility(View.GONE);
                }
            }
        }
    }

    private class PopupItemClickListener implements SnsPopupWindow.OnItemClickListener {
        private String mTakeId;
        //动态在列表中的位置
        private int mCirclePosition;//说说的位置

        private long mLasttime = 0;

        private GetFriendCircleListResp.FriendCircleItem friendCircleItem;

        private String mUserId = String.valueOf(TeamHitContext.getInstance().getUserId());

        public PopupItemClickListener(int circlePosition, GetFriendCircleListResp.FriendCircleItem friendCircleItem, String TakeId) {
            this.mTakeId = TakeId;
            this.mCirclePosition = circlePosition;
            this.friendCircleItem = friendCircleItem;
        }

        @Override
        public void onItemClick(View view, SnsPopupWindow.ActionItem actionitem, int position) {
            switch (position) {
                case 0://点赞、取消点赞
                    if (System.currentTimeMillis() - mLasttime < 700)//防止快速点击操作
                        return;
                    mLasttime = System.currentTimeMillis();
                    /**
                     * 点赞功能
                     */
                    if ("赞".equals(actionitem.mTitle.toString())) {
                        view.setTag(Constant.Favorite);
                    } else {//取消点赞
                        view.setTag(Constant.Cancle_Favorite);
                    }
                    mOnItemClickListener.onItemClick(view, mCirclePosition, friendCircleItem);
                    break;
                case 1://发布评论
                    /**
                     * 发表评论
                     */
                    CommentData commentData = new CommentData(Constant.Comment, mCirclePosition);//这个表示说说的诶之
                    view.setTag(commentData);
                    mOnItemClickListener.onItemClick(view, 0, friendCircleItem);//position表示评论的位置
                    break;
                default:
                    break;
            }
        }
    }

    class ImageViewHolder extends CircleViewHolder {
        /**
         * 图片
         */
        public MultiImageView multiImageView;

        public ImageViewHolder(View itemView) {
            super(itemView, 1);
        }

        @Override
        public void initSubView(int viewType, ViewStub viewStub) {
            if (viewStub == null) {
                throw new IllegalArgumentException("viewStub is null...");
            }
            viewStub.setLayoutResource(R.layout.viewstub_imgbody);
            View subView = viewStub.inflate();
            MultiImageView multiImageView = (MultiImageView) subView.findViewById(R.id.multiImagView);
            if (multiImageView != null) {
                this.multiImageView = multiImageView;
            }
        }
    }

    class HeaderViewHolder extends BaseRecyclerViewHolder {

        ImageView imgHead;

        TextView txtName;

        private LinearLayout layout;

        private ImageView imgMessageHead;

        private TextView txtNumberMessage;


        public HeaderViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            imgHead = (ImageView) itemView.findViewById(R.id.img_head);
            txtNumberMessage = (TextView) itemView.findViewById(R.id.txt_number_message);
            imgMessageHead = (ImageView) itemView.findViewById(R.id.img_message_head);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);

        }
    }


    public class CommentData {
        String CommentType;

        int position;

        public CommentData(String commentType, int position) {
            CommentType = commentType;
            this.position = position;
        }

        public String getCommentType() {
            return CommentType;
        }

        public void setCommentType(String commentType) {
            CommentType = commentType;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
