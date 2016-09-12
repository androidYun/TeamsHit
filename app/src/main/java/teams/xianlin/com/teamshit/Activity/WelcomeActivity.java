package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teams.xianlin.com.teamshit.BaseInfor.BackendLoginClass;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.Interface.BackendLoginCallBack;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ToastUtil;

/**
 * 欢迎界面
 * Created by Administrator on 2016/7/16.
 */
public class WelcomeActivity extends BaseActivity implements BackendLoginCallBack {
    @Bind(R.id.txt_login)
    TextView txtLogin;
    @Bind(R.id.txt_main)
    TextView txtMain;

    private BackendLoginClass backendLoginClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_layout);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
        backendLoginClass = new BackendLoginClass(WelcomeActivity.this, this);
        backendLoginClass.startBackLogin();
    }

    @Override
    public void initialView() {

    }

    @Override
    protected void initialData() {

    }

    @Override
    protected void inflateView() {

    }

    @OnClick({R.id.txt_login, R.id.txt_main})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.txt_login:
                Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                break;

            case R.id.txt_main:
                Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                break;
        }
    }

    @Override
    public void BackendLoginsuccess() {
        Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void BackendLoginFail(ErrorMsg errorMsg) {
        ToastUtil.show(WelcomeActivity.this, errorMsg.getErrorMsg());
        if (errorMsg.getErrorCode() == Constant.Not_Complete_Infor_Code) {
            Intent completeUserIntent = new Intent(WelcomeActivity.this, CompleteUserInforActivity.class);
            startActivity(completeUserIntent);
            return;
        }
        Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
