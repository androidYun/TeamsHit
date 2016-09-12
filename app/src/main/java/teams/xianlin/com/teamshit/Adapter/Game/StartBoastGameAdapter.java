package teams.xianlin.com.teamshit.Adapter.Game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import teams.xianlin.com.teamshit.Adapter.BaseRecyclerAdapter;
import teams.xianlin.com.teamshit.Adapter.BaseRecyclerViewHolder;
import teams.xianlin.com.teamshit.Bean.DiceBean;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.widget.Game.FiveDiceView;

/**
 * Created by Administrator on 2016/9/10.
 */
public class StartBoastGameAdapter extends BaseRecyclerAdapter<DiceBean> {

    private Context mContext;

    private List<DiceBean> mDatas;

    public StartBoastGameAdapter(Context mContext, List<DiceBean> mDatas) {
        super(mContext, mDatas);
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View view = inflateView(R.layout.list_item_start_boast_game_dice_layout, parent);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final DiceBean diceBean, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Glide.with(mContext).load("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo_top_ca79a146.png").diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.imgHead);
        viewHolder.txtName.setText("李桂云");
        viewHolder.fiveDiceView.setList(mDatas);
        viewHolder.imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view, position, diceBean);
            }
        });
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        ImageView imgHead;

        TextView txtName;

        View viewPrompt;

        FiveDiceView fiveDiceView;

        View viewStart;

        public ViewHolder(View view) {
            super(view);
            imgHead = (ImageView) view.findViewById(R.id.img_head);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            viewPrompt = view.findViewById(R.id.view_prompt);
            viewStart = view.findViewById(R.id.view_start);
            fiveDiceView = (FiveDiceView) view.findViewById(R.id.five_dice_view);
        }
    }
}
