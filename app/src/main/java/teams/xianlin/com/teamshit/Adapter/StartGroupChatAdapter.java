package teams.xianlin.com.teamshit.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.Bean.SelectorGroupChatFriendBean;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.ImageLoadUtils;
import teams.xianlin.com.teamshit.Utils.RongGenerate;
import teams.xianlin.com.teamshit.widget.SelectableRoundedImageView;

/**
 * Created by Administrator on 2016/8/24.
 */
public class StartGroupChatAdapter extends HolderAdapter<SelectorGroupChatFriendBean, StartGroupChatAdapter.ViewHolder> implements SectionIndexer {


    private Context mContext;

    private List<SelectorGroupChatFriendBean> listData;

    public StartGroupChatAdapter(Context context, List<SelectorGroupChatFriendBean> listData) {
        super(context, listData);
        this.mContext = context;
        this.listData = listData;
    }

    @Override
    public View buildConvertView(LayoutInflater layoutInflater, SelectorGroupChatFriendBean friend, int position) {
        return inflate(R.layout.list_item_start_group_chat);
    }

    @Override
    public ViewHolder buildHolder(View convertView, SelectorGroupChatFriendBean friend, int position) {
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void bindViewDatas(final ViewHolder holder, final SelectorGroupChatFriendBean friend, final int position) {
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.txtCatalog.setVisibility(View.VISIBLE);
            holder.txtCatalog.setText(friend.getLetters());
        } else {
            holder.txtCatalog.setVisibility(View.GONE);
        }
        holder.txtFriendname.setText(friend.getName());
        if (TextUtils.isEmpty(friend.getPortraitUri())) {
            String s = RongGenerate.generateDefaultAvatar(friend.getName(), friend.getUserId());//如果没有头像那就创造一个头像
            ImageLoadUtils.getInstance().with(context, s, holder.imgHead);
        } else {
            ImageLoadUtils.getInstance().with(context, listData.get(position).getPortraitUri(), holder.imgHead);
        }
        holder.cbkSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(holder.cbkSelect, position, friend);
            }
        });
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = listData.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        if (listData.get(position) != null) {
            return listData.get(position).getLetters().charAt(0);
        }
        return 0;
    }

    /**
     * 传入新的数据 刷新UI的方法
     */
    public void updateListView(List<SelectorGroupChatFriendBean> list) {
        listData.clear();
        listData.addAll(list);
        this.notifyDataSetChanged();
    }

    public void updateProgressPartly(ListView listView, int position) {
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int lastVisiblePosition = listView.getLastVisiblePosition();
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            View view = listView.getChildAt(position - firstVisiblePosition);
            getView(position, view, listView);
        }
    }

    class ViewHolder {
        @Bind(R.id.txt_catalog)
        TextView txtCatalog;
        @Bind(R.id.img_head)
        SelectableRoundedImageView imgHead;
        @Bind(R.id.txt_friendname)
        TextView txtFriendname;
        @Bind(R.id.cbk_select)
        CheckBox cbkSelect;
        @Bind(R.id.dis_frienditem)
        LinearLayout disFrienditem;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
