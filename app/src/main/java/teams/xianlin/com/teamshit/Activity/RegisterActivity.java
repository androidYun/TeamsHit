package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.RegisterResp;
import teams.xianlin.com.teamshit.NetWorkResp.VerificationCodeResp;
import teams.xianlin.com.teamshit.Presenter.RegisterPresenter;
import teams.xianlin.com.teamshit.Presenter.VerificationPresenter;
import teams.xianlin.com.teamshit.PresenterView.RegisterView;
import teams.xianlin.com.teamshit.PresenterView.VerificationCodeView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.Encrypt;
import teams.xianlin.com.teamshit.Utils.SPUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.widget.TimeTextView;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/7/12.
 */
public class RegisterActivity extends BaseActivity implements VerificationCodeView, RegisterView {


    @Bind(R.id.navi_title)
    RelativeLayout naviTitle;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.txt_phone)
    TextView txtPhone;
    @Bind(R.id.edit_phone)
    EditText editPhone;
    @Bind(R.id.txt_code)
    TimeTextView txtCode;
    @Bind(R.id.txt_phone_code)
    TextView txtPhoneCode;
    @Bind(R.id.edit_code)
    EditText editCode;
    @Bind(R.id.txt_pwd)
    TextView txtPwd;
    @Bind(R.id.edit_pwd)
    EditText editPwd;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.navi_top)
    TitleNavi naviTop;
    private VerificationPresenter verificationPresenter;

    private RegisterPresenter registerPresenter;

    private String mVerificationCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        super.initialView();
        txtCode.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void initialData() {
        naviTop.setClickCallBack(this);
        verificationPresenter = new VerificationPresenter(this);
        registerPresenter = new RegisterPresenter(this);
    }

    @Override
    protected void inflateView() {
        btnConfirm.setText("注册");
        naviTop.setBackTitle("注册账号");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.txt_code:
                verificationPresenter.loadData(editPhone.getText().toString(), RegisterActivity.this);
                break;
            case R.id.btn_confirm:
                registerPresenter.loadData(editPhone.getText().toString(), editPwd.getText().toString(), Encrypt.MD5(editCode.getText().toString() + Constant.MD5_KEY), mVerificationCode, RegisterActivity.this);
                btnConfirm.setEnabled(false);
//                Intent completeUserInfoIntent = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(completeUserInfoIntent);
                break;
        }
    }

    @Override
    public void onLoadSucess(VerificationCodeResp verificationCodeResp) {
        mVerificationCode = verificationCodeResp.getVerifynode();
        txtCode.setStartTime();
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        if (errorMsg.getRespCode() == HttpDefine.Register_Resp) {
            handler.sendEmptyMessage(1);
        } else if (errorMsg.getRespCode() == HttpDefine.Verification_Code_Resp) {

        }
        ToastUtil.show(RegisterActivity.this, errorMsg.getErrorMsg());
    }

    @Override
    public void onLoadSucess(RegisterResp registerResp) {
        btnConfirm.setEnabled(true);
        SPUtils.put(RegisterActivity.this, Constant.User_Account, editPhone.getText().toString());
        SPUtils.put(RegisterActivity.this, Constant.User_Pwd, editPwd.getText().toString());
        Intent completeUserInfoIntent = new Intent(mActivity, CompleteUserInforActivity.class);
        startActivity(completeUserInfoIntent);
        finish();

    }

    @Override
    public void showRegisterProgress() {
     //   loadDialog.show();
    }

    @Override
    public void hideRegsiterProgress() {
        loadDialog.hide();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    btnConfirm.setEnabled(true);
                    break;
            }
        }
    };
}
