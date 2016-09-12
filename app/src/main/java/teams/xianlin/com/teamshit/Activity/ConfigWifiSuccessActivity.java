package teams.xianlin.com.teamshit.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/9/5.
 */
public class ConfigWifiSuccessActivity extends BaseActivity {
    @Bind(R.id.navi_top)
    TitleNavi naviTop;
    @Bind(R.id.img_title)
    ImageView imgTitle;
    @Bind(R.id.img_content)
    ImageView imgContent;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_wifi_success);
        ButterKnife.bind(this);
        initialData();
    }

    @Override
    protected void initialData() {
        naviTop.setBackTitle("配置设备");
        naviTop.setClickCallBack(this);
        btnConfirm.setText("确定");

    }

    @Override
    protected void inflateView() {

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
