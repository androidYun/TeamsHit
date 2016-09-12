package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.SubConversationListFragment;
import teams.xianlin.com.teamshit.Adapter.SubConversationListAdapterEx;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/8/11.
 */
public class SubConversationListActivity extends BaseActivity {
    @Bind(R.id.navi_top)
    TitleNavi naviTop;

    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("博哥最丑");
        setContentView(R.layout.activity_sub_conversation);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();

    }

    @Override
    public void initialView() {
        SubConversationListFragment fragment = new SubConversationListFragment();
        fragment.setAdapter(new SubConversationListAdapterEx(RongContext.getInstance()));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();
        naviTop.setClickCallBack(this);
    }

    @Override
    protected void initialData() {
        Intent intent = getIntent();
        if (intent.getData() == null) {
            return;
        }
        //聚合会话参数
        mType = intent.getData().getQueryParameter("type");
    }

    @Override
    protected void inflateView() {
        if (mType == null)
            return;
        if (mType.equals("group")) {
            naviTop.setBackTitle(getResources().getString(R.string.de_actionbar_sub_group));
        } else if (mType.equals("private")) {
            naviTop.setBackTitle(getResources().getString(R.string.de_actionbar_sub_private));
        } else if (mType.equals("discussion")) {
            naviTop.setBackTitle(getResources().getString(R.string.de_actionbar_sub_discussion));
        } else if (mType.equals("system")) {
            naviTop.setBackTitle(getResources().getString(R.string.de_actionbar_sub_system));
        } else {
            naviTop.setBackTitle(getResources().getString(R.string.de_actionbar_sub_defult));
        }
    }
}
