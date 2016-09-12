package teams.xianlin.com.teamshit.widget.Game;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teams.xianlin.com.teamshit.Adapter.Game.PrePareGameAdapter;
import teams.xianlin.com.teamshit.BaseInfor.HttpSocketDefine;
import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.Interface.OnItemClickListener;
import teams.xianlin.com.teamshit.NetWork.SocketPacket;
import teams.xianlin.com.teamshit.NetWorkResp.SocketResp.GetPrePareUserList;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.JsonUtils.JsonMananger;
import teams.xianlin.com.teamshit.widget.RecyclerView.SpaceItemDecoration;

/**
 * Created by Administrator on 2016/9/8.
 */
public class BoastPrepareView extends LinearLayout implements View.OnClickListener, OnItemClickListener<UserBean> {

    @Bind(R.id.rv_prepare)
    RecyclerView rvPrepare;
    @Bind(R.id.img_prepare)
    ImageView imgPrepare;
    @Bind(R.id.img_back)
    ImageView imgBack;
    private Context mContext;

    private GridLayoutManager gridLayoutManager;

    private PrePareGameAdapter prePareGameAdapter;

    private List<UserBean> mData;


    public BoastPrepareView(Context context, AttributeSet attrs, List<UserBean> mData) {
        super(context, attrs);
        this.mData = mData;
        this.mContext = context;
        initialView();
        initialData();
        inflateView();
    }

    private void initialView() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.view_prepare_boast_layout, this, true);
        ButterKnife.bind(this, inflate);
        gridLayoutManager = new GridLayoutManager(mContext, 3);
        int spacingInPixels = 8;
        rvPrepare.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        rvPrepare.setLayoutManager(gridLayoutManager);
        prePareGameAdapter = new PrePareGameAdapter(mContext, mData);
        prePareGameAdapter.setOnItemClickListener(this);
        rvPrepare.setAdapter(prePareGameAdapter);
    }

    private void initialData() {

    }

    private void inflateView() {
    }


    @OnClick({R.id.rv_prepare, R.id.img_prepare, R.id.img_back})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rv_prepare:

                break;
            case R.id.img_prepare:

                break;
            case R.id.img_back:

                break;
        }
    }

    @Override
    public void onItemClick(View view, int position, UserBean model) {
    }

    @Override
    public void onItemLongClick(View view, int position, UserBean model) {

    }

    public void loadData(String message) {
        GetPrePareUserList prePareUserList=null;
        try {
            prePareUserList = JsonMananger.jsonToBean(message, GetPrePareUserList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (prePareUserList != null && prePareUserList.getGameCommend() == HttpSocketDefine.Boast_Prepare_Command) {
            List<UserBean> userList = prePareUserList.getUser();
            if (userList != null && userList.size() > 0) {
                mData.clear();
                mData.addAll(userList);
                prePareGameAdapter.notifyDataSetChanged();
            }
        }
    }
}
