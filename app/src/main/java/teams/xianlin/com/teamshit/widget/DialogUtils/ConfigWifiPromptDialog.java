package teams.xianlin.com.teamshit.widget.DialogUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;

/**
 * Created by Administrator on 2016/8/4.
 */
public class ConfigWifiPromptDialog extends Dialog implements View.OnClickListener {
    private TextView txt_prompt;

    private Button btn_confirm;

    private ImageView img_prompt;

    private Context mConetxt;

    private View view;
    private AnimationDrawable animation;

    public ConfigWifiPromptDialog(Activity context) {
        super(context, R.style.select_photo_dialog_style);
        this.mConetxt = context;
        setSelectdialog(context);
        initialAnimal();
    }


    private void setSelectdialog(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_prompt_operator_team_hit, null);
        txt_prompt = (TextView) view.findViewById(R.id.txt_prompt);
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        img_prompt = (ImageView) view.findViewById(R.id.img_prompt);
        btn_confirm.setText("取消");
        btn_confirm.setOnClickListener(this);
        this.getWindow().setWindowAnimations(R.style.dialog_animation);  //添加动画
        super.setContentView(view);
    }

    private void initialAnimal() {
        animation = (AnimationDrawable) img_prompt.getBackground();
        animation.start();
    }

    public void showDialog() {
        if (!this.isShowing()) {
            this.show();
        }
    }

    public void disMiss() {
        animation.stop();
        this.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                disMiss();
                break;
        }
    }
}
