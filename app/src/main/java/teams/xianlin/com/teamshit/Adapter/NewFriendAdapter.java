package teams.xianlin.com.teamshit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.NetWorkResp.GetNewFriendListResp;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.ImageLoadUtils;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;

/**
 * Created by Administrator on 2016/7/28.
 */
public class NewFriendAdapter extends HolderAdapter<GetNewFriendListResp.Friend, NewFriendAdapter.ViewHolder> {


    private Context mContext;

    public NewFriendAdapter(Context context, List<GetNewFriendListResp.Friend> listData) {
        super(context, listData);
        this.mContext = context;
    }

    @Override
    public View buildConvertView(LayoutInflater layoutInflater, GetNewFriendListResp.Friend friend, int position) {
        return inflate(R.layout.list_item_new_friend);
    }

    @Override

    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public ViewHolder buildHolder(View convertView, GetNewFriendListResp.Friend friend, int position) {
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void bindViewDatas(final ViewHolder holder, final GetNewFriendListResp.Friend friend, final int position) {
        ImageLoadUtils.getInstance().with(mContext, friend.getPortraitUri(), R.drawable.default_head, holder.imgHead);
        holder.txtNickname.setText("" + friend.getNickname());
        setMessgae(friend, holder);
        setbtnConfirm(friend, holder);
        if (friend.getStatus() == 2) {
            holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.btnConfirm, position, friend);
                }
            });
        }
        holder.itemLayoutFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(holder.itemLayoutFriend, position, friend);
            }
        });
    }

    public void setbtnConfirm(GetNewFriendListResp.Friend friend, ViewHolder holder) {
        int mStatus = friend.getStatus();//好友状态
        if (mStatus == 1) {
            holder.btnConfirm.setBackgroundResource(R.drawable.shape_already_add_friend);
            holder.btnConfirm.setTextColor(mContext.getResources().getColor(R.color.alway_text_black));
            holder.btnConfirm.setText("已添加");
        } else if (mStatus == 2) {
            holder.btnConfirm.setBackgroundResource(R.drawable.shape_receive_new_friend);
            holder.btnConfirm.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.btnConfirm.setText("接收");
        } else if (mStatus == 3) {
            holder.btnConfirm.setBackgroundResource(R.drawable.shape_already_add_friend);
            holder.btnConfirm.setTextColor(mContext.getResources().getColor(R.color.alway_text_black));
            holder.btnConfirm.setText("等待验证");
        }
    }

    /**
     * 设置Messgae信息
     *
     * @param friend
     * @param holder
     */
    public void setMessgae(GetNewFriendListResp.Friend friend, ViewHolder holder) {
        if (!StringUtils.isBlank(friend.getMessage())) {
            holder.txtContent.setText("" + friend.getMessage());
            int mStatus = friend.getStatus();//好友状态
            if (mStatus == 3) {//如果状态为3  是你添加  对方为好友
                holder.txtContent.setText("你请求添加" + friend.getNickname() + "为好友");
            }
        } else {
            int mStatus = friend.getStatus();//好友状态
            if (mStatus == 1) {
                holder.txtContent.setText("我是" + friend.getNickname());
            } else if (mStatus == 2) {
                holder.txtContent.setText("对方添加你为好友");
            } else if (mStatus == 3) {
                holder.txtContent.setText("你请求添加" + friend.getNickname() + "为好友");
            }
        }
    }

    class ViewHolder {
        @Bind(R.id.img_head)
        ImageView imgHead;
        @Bind(R.id.txt_nickname)
        TextView txtNickname;
        @Bind(R.id.txt_content)
        TextView txtContent;
        @Bind(R.id.btn_confirm)
        TextView btnConfirm;
        @Bind(R.id.item_layout_friend)
        RelativeLayout itemLayoutFriend;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void updateProgressPartly(ListView listView, int position) {
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int lastVisiblePosition = listView.getLastVisiblePosition();
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            View view = listView.getChildAt(position - firstVisiblePosition);
            getView(position, view, listView);
        }
    }


}
