package teams.xianlin.com.teamshit.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/9/5.
 */
public class ConfigWifiFailActivity extends BaseActivity {

    @Bind(R.id.navi_top)
    TitleNavi naviTop;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_wifi_fail);
        ButterKnife.bind(this);
        initialData();
        inflateView();
    }

    @Override
    protected void initialData() {

    }

    @Override
    protected void inflateView() {
        naviTop.setBackTitle("配置设备");
        naviTop.setClickCallBack(this);
    }

    @OnClick({R.id.btn_confirm})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_confirm:
                finish();
                break;
        }
    }
}
