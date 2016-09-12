package teams.xianlin.com.teamshit.Adapter.Game;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import teams.xianlin.com.teamshit.Adapter.BaseRecyclerAdapter;
import teams.xianlin.com.teamshit.Adapter.BaseRecyclerViewHolder;
import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.widget.Game.PrePareHeadImg;

/**
 * Created by Administrator on 2016/9/8.
 */
public class PrePareGameAdapter extends BaseRecyclerAdapter<UserBean> {


    private Context mContext;

    private List<UserBean> mData;

    public PrePareGameAdapter(Context mContext, List<UserBean> datas) {
        super(mContext, datas);
        this.mContext = mContext;
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        PrePareHeadImg prePareHeadImg = new PrePareHeadImg(mContext, null);
        ViewHodler viewHodler = new ViewHodler(prePareHeadImg);
        return viewHodler;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final UserBean userBean, final int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        if (userBean != null) {
            viewHodler.imgHead.load(userBean.getPortraitUri());
        } else {
            viewHodler.imgHead.showDefaultHead(R.drawable.default_head);
        }
        viewHodler.imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view, position, userBean);
            }
        });
    }

    class ViewHodler extends BaseRecyclerViewHolder {
        PrePareHeadImg imgHead;

        public ViewHodler(PrePareHeadImg imgHead) {
            super(imgHead);
            this.imgHead = imgHead;
        }
    }
}
