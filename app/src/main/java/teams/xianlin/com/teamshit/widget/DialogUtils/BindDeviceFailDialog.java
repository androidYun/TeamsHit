package teams.xianlin.com.teamshit.widget.DialogUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import teams.xianlin.com.teamshit.Interface.SelectPhotoCallBack;
import teams.xianlin.com.teamshit.R;

/**
 * Created by Administrator on 2016/8/4.
 */
public class BindDeviceFailDialog extends Dialog implements View.OnClickListener {
    private TextView txt_prompt;

    private Button btn_confirm;

    private Context mConetxt;

    private View view;

    public BindDeviceFailDialog(Activity context) {
        super(context, R.style.select_photo_dialog_style);
        this.mConetxt = context;
        setSelectdialog(context);

    }


    private void setSelectdialog(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_bind_device_fail, null);
        txt_prompt = (TextView) view.findViewById(R.id.txt_prompt);
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        btn_confirm.setText("取消");
        btn_confirm.setOnClickListener(this);
        this.getWindow().setWindowAnimations(R.style.dialog_animation);  //添加动画
        super.setContentView(view);
    }

    public void showDialog(String deviceName) {
        txt_prompt.setText("" + deviceName);
        if (!this.isShowing()) {
            this.show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                this.dismiss();
                break;
        }
    }
}
