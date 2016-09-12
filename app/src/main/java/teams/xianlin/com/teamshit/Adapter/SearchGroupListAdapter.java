package teams.xianlin.com.teamshit.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.NetWorkResp.SearchGroupListResp;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.TeamsHitApplication;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.ImageLoadUtils;

/**
 * Created by Administrator on 2016/8/10.
 */
public class SearchGroupListAdapter extends BaseRecyclerAdapter<SearchGroupListResp.GroupList> {

    private Context mConetxt;

    public SearchGroupListAdapter(Context context, List<SearchGroupListResp.GroupList> datas) {
        super(context, datas);
        this.mConetxt = context;
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View inflateView = inflateView(R.layout.list_item_search_group, parent);
        ViewHodler viewHodler = new ViewHodler(inflateView);
        return viewHodler;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, SearchGroupListResp.GroupList groupList, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        ImageLoadUtils.getInstance().cachaewith(mContext, groupList.getGroupIconUrl(), R.drawable.default_head, ((ViewHodler) holder).imgHead);
        viewHodler.txtGroupName.setText(groupList.getGroupName());
        viewHodler.txtGroupPeople.setText(groupList.getGroupPeople());
        viewHodler.txtIntro.setText(groupList.getGroupIntro());
    }

    class ViewHodler extends BaseRecyclerViewHolder {
        @Bind(R.id.img_head)
        ImageView imgHead;
        @Bind(R.id.txt_group_name)
        TextView txtGroupName;
        @Bind(R.id.txt_group_people)
        TextView txtGroupPeople;
        @Bind(R.id.txt_intro)
        TextView txtIntro;

        public ViewHodler(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
