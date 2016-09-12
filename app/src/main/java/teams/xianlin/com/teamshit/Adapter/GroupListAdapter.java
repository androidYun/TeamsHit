package teams.xianlin.com.teamshit.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.ImageLoadUtils;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.RongGenerate;
import teams.xianlin.com.teamshit.db.Groups;

/**
 * Created by Administrator on 2016/9/8.
 */
public class GroupListAdapter extends BaseRecyclerAdapter<Groups> implements SectionIndexer {
    private final static int Boarst_Game = 1;

    private final static int Twenty_One_Game = 2;


    private Context mContext;

    private List<Groups> mDatas;

    public GroupListAdapter(Context mContext, List<Groups> mDatas) {
        super(mContext, mDatas);
        this.mContext = mContext;
        this.mDatas = mDatas;
    }


    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View view = inflateView(R.layout.list_item_group_list_layout, parent);
        ViewHodler viewHodler = new ViewHodler(view);
        return viewHodler;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final Groups groups, final int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            viewHodler.catalog.setVisibility(View.VISIBLE);
            String GameTye = "";
            switch (groups.getGroupType()) {
                case Boarst_Game:
                    GameTye = "吹牛";
                    break;
                case Twenty_One_Game:
                    GameTye = "21点";
                    break;
            }
            viewHodler.catalog.setText("" + GameTye);
        } else {
            viewHodler.catalog.setVisibility(View.GONE);
        }
        viewHodler.txtIntro.setText(groups.getIntroduce());
        if (TextUtils.isEmpty(groups.getPortraitUri())) {
            String s = RongGenerate.generateDefaultAvatar(groups.getName(), groups.getGroupId());//如果没有头像那就创造一个头像
            ImageLoadUtils.getInstance().with(mContext, s, viewHodler.imgHead);
        } else {
            ImageLoadUtils.getInstance().with(mContext, groups.getPortraitUri(), viewHodler.imgHead);
        }
        viewHodler.txtGroupPeople.setText("" + groups.getNumber());
        viewHodler.txtGroupName.setText("" + groups.getName());
        viewHodler.layoutGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view, position, groups);
            }
        });

    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getItemCount(); i++) {
            int groupId = mDatas.get(i).getGroupType();
            if (groupId == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return mDatas.get(position).getGroupType();
    }


    class ViewHodler extends BaseRecyclerViewHolder {
        @Bind(R.id.catalog)
        TextView catalog;
        @Bind(R.id.img_head)
        ImageView imgHead;
        @Bind(R.id.txt_group_name)
        TextView txtGroupName;
        @Bind(R.id.txt_group_people)
        TextView txtGroupPeople;
        @Bind(R.id.txt_intro)
        TextView txtIntro;
        @Bind(R.id.layout_group)
        LinearLayout layoutGroup;

        public ViewHodler(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
