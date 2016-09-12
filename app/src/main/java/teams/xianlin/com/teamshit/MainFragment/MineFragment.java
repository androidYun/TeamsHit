package teams.xianlin.com.teamshit.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teams.xianlin.com.teamshit.Activity.BindTeamHitActivity;
import teams.xianlin.com.teamshit.Activity.LoginActivity;
import teams.xianlin.com.teamshit.Activity.UserInforActivity;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;

/**
 * Created by Administrator on 2016/7/15.
 */
public class MineFragment extends BaseFragment {

    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.img_next)
    ImageView imgNext;
    @Bind(R.id.text_name)
    TextView textName;
    @Bind(R.id.img_team_price)
    ImageView imgTeamPrice;
    @Bind(R.id.text_price_title)
    TextView textPriceTitle;
    @Bind(R.id.text_price)
    TextView textPrice;
    @Bind(R.id.img_team_mall)
    ImageView imgTeamMall;
    @Bind(R.id.text_mall)
    TextView textMall;
    @Bind(R.id.text_defail)
    TextView textDefail;
    @Bind(R.id.linear_device)
    LinearLayout linearDevice;
    @Bind(R.id.linear_exit)
    LinearLayout linearExit;
    @Bind(R.id.layout_user)
    RelativeLayout layoutUser;
    private View mineView;


    public static MineFragment getInstance() {
        return new MineFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mineView = inflater.inflate(R.layout.fragment_mine_layout, null, false);
        ButterKnife.bind(this, mineView);
        return mineView;
    }

    @Override
    protected void initialView() {

    }

    @Override
    protected void initialData() {

    }

    @Override
    protected void inflateView() {

    }

    @OnClick({R.id.linear_device, R.id.linear_exit, R.id.layout_user})
    @Override
    public void onClick(View view) {
        LogUtil.d("看见了" + view.getId() + "    "+R.id.img_next);
        super.onClick(view);
        switch (view.getId()) {
            case R.id.linear_device:
                Intent deviceIntent = new Intent(mActivity, BindTeamHitActivity.class);
                startActivity(deviceIntent);
                break;
            case R.id.linear_exit:
                Intent loginIntent = new Intent(mActivity, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.layout_user:
                Intent userInforIntent = new Intent(mActivity, UserInforActivity.class);
                startActivity(userInforIntent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
