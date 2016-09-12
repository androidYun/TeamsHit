package teams.xianlin.com.teamshit.widget.Game;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.Adapter.Game.StartBoastGameAdapter;
import teams.xianlin.com.teamshit.Bean.DiceBean;
import teams.xianlin.com.teamshit.Interface.OnItemClickListener;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.widget.RecyclerView.DividerGridItemDecoration;

/**
 * Created by Administrator on 2016/9/10.
 */
public class BoastStartView extends RelativeLayout implements OnItemClickListener<DiceBean> {


    @Bind(R.id.rv_start)
    RecyclerView rvStart;
    @Bind(R.id.rv_result)
    RecyclerView rvResult;
    @Bind(R.id.layout_roll_dice)
    FrameLayout layoutRollDice;
    private Context mContext;

    private LinearLayoutManager linearLayoutManager;

    private StartBoastGameAdapter startBoastGameAdapter;//适配器

    private List<DiceBean> diceBeanList;

    public BoastStartView(Context context) {
        this(context, null);
    }

    public BoastStartView(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext = mContext;
        initialView();
        initialData();
        inflateView();
    }

    private void initialView() {
        diceBeanList = new ArrayList<>();
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.view_start_boast_layout, this, true);
        ButterKnife.bind(this, inflate);
        linearLayoutManager = new LinearLayoutManager(mContext);
        rvStart.addItemDecoration(new DividerGridItemDecoration(mContext));
        rvStart.setLayoutManager(linearLayoutManager);
    }

    private void initialData() {
        DiceBean diceBean = new DiceBean();
        diceBean.setDiceCount(5);
        diceBeanList.add(diceBean);
        diceBeanList.add(diceBean);
        diceBeanList.add(diceBean);
        diceBeanList.add(diceBean);
        diceBeanList.add(diceBean);
    }

    private void inflateView() {
        startBoastGameAdapter = new StartBoastGameAdapter(mContext, diceBeanList);
        startBoastGameAdapter.setOnItemClickListener(this);
        rvStart.setAdapter(startBoastGameAdapter);
    }

    public View getRollDiceView() {
        RollDiceView rollDiceView = new RollDiceView(mContext);
        return rollDiceView;
    }

    @Override
    public void onItemClick(View view, int position, DiceBean model) {
        layoutRollDice.removeAllViews();
        layoutRollDice.addView(getRollDiceView());
    }

    @Override
    public void onItemLongClick(View view, int position, DiceBean model) {

    }
}
