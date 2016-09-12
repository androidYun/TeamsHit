package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teams.xianlin.com.teamshit.BaseInfor.BackendLoginClass;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.Interface.BackendLoginCallBack;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.SPUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.db.DBManager;
import teams.xianlin.com.teamshit.db.Friend;

/**
 * Created by Administrator on 2016/7/6.
 */
public class LoginActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, BackendLoginCallBack {
    @Bind(R.id.edit_account)
    EditText editAccount;
    @Bind(R.id.txt_pwd)
    TextView txtPwd;
    @Bind(R.id.edit_pwd)
    EditText editPwd;
    @Bind(R.id.rbtn_show_pwd)
    CheckBox rbtnShowPwd;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.txt_resgister)
    TextView txtResgister;
    @Bind(R.id.txt_forget_pwd)
    TextView txtForgetPwd;
    private String mOldAccount;

    private String mOldPwd;

    private String mNewAccount;

    private String mNewPwd;

    private BackendLoginClass backendLoginClass;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
        inflateData();
    }

    @Override
    public void initialView() {
        btnConfirm.setOnClickListener(this);
        txtResgister.setOnClickListener(this);
        txtForgetPwd.setOnClickListener(this);
        rbtnShowPwd.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initialData() {
        backendLoginClass = new BackendLoginClass(LoginActivity.this, this);
        mOldAccount = (String) SPUtils.get(LoginActivity.this, Constant.User_Account, "");
        mOldPwd = (String) SPUtils.get(LoginActivity.this, Constant.User_Pwd, "");
    }

    @Override
    protected void inflateView() {
        btnConfirm.setText("登录");
        editAccount.setText(mOldAccount);
        editPwd.setText(mOldPwd);
    }

    public void inflateData() {

    }

    @OnClick({R.id.txt_resgister, R.id.txt_forget_pwd, R.id.btn_confirm})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.txt_resgister:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.txt_forget_pwd:
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mainIntent);
                break;
            case R.id.btn_confirm:
                mNewAccount = editAccount.getText().toString();
                mNewPwd = editPwd.getText().toString();
                backendLoginClass.startBackLogin(mNewAccount, mNewPwd);
                loadDialog.show();
                // loginPresenter.loadData(mNewAccount, mNewPwd);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            editPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            editPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            rbtnShowPwd.setChecked(false);
        }
    }

    @Override
    public void BackendLoginsuccess() {
        loadDialog.cancel();
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void BackendLoginFail(ErrorMsg errorMsg) {
        loadDialog.cancel();
        ToastUtil.show(LoginActivity.this, errorMsg.getErrorMsg() + "");
        if (errorMsg.getErrorCode() == Constant.Not_Complete_Infor_Code) {
            Intent completeUserIntent = new Intent(LoginActivity.this, CompleteUserInforActivity.class);
            startActivity(completeUserIntent);
            return;
        }

    }
}
