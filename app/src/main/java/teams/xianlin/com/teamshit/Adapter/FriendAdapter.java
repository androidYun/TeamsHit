package teams.xianlin.com.teamshit.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.PinYing.Friend;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.ImageLoadUtils;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.RongGenerate;
import teams.xianlin.com.teamshit.widget.SelectableRoundedImageView;

/**
 * Created by Administrator on 2016/8/15.
 */
public class FriendAdapter extends HolderAdapter<Friend, FriendAdapter.ViewHolder> implements SectionIndexer {


    private Context mContext;

    private List<Friend> list;

    public FriendAdapter(Context context, List<Friend> listData) {
        super(context, listData);
        this.mContext = context;
        this.list = listData;
    }

    @Override
    public View buildConvertView(LayoutInflater layoutInflater, Friend friend, int position) {
        return inflate(R.layout.list_item_frirnd_layout);
    }

    @Override
    public ViewHolder buildHolder(View convertView, Friend friend, int position) {
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void bindViewDatas(ViewHolder viewHolder, Friend friend, int position) {
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.catalog.setVisibility(View.VISIBLE);
            viewHolder.catalog.setText(friend.getLetters());
        } else {
            viewHolder.catalog.setVisibility(View.GONE);
        }
        viewHolder.txtFriendName.setText(friend.getName());
        if (TextUtils.isEmpty(friend.getPortraitUri())) {
            String s = RongGenerate.generateDefaultAvatar(friend.getName(), friend.getUserId());//如果没有头像那就创造一个头像
            ImageLoadUtils.getInstance().with(context, s, viewHolder.imgFriendHead);
        } else {
            ImageLoadUtils.getInstance().with(context, list.get(position).getPortraitUri(), viewHolder.imgFriendHead);
        }
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getLetters().charAt(0);
    }

    /**
     * 传入新的数据 刷新UI的方法
     */
    public void updateListView(List<Friend> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    final static class ViewHolder {
        @Bind(R.id.catalog)
        TextView catalog;
        @Bind(R.id.img_friend_head)
        SelectableRoundedImageView imgFriendHead;
        @Bind(R.id.txt_friend_name)
        TextView txtFriendName;
        @Bind(R.id.item_friend)
        LinearLayout itemFriend;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
