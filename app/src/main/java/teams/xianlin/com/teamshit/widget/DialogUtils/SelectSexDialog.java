package teams.xianlin.com.teamshit.widget.DialogUtils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import teams.xianlin.com.teamshit.Interface.SelectSexCallBack;
import teams.xianlin.com.teamshit.R;

/**
 * Created by Administrator on 2016/7/13.
 */
public class SelectSexDialog extends Dialog implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private Context mContext;
    private View view;

    private RadioGroup rgroup_selelct_sex;

    private TextView txt_selelct_cancle;

    private TextView txt_select_confirm;

    private RadioButton rbtn_boy;

    private RadioButton rbtn_gril;

    private RadioButton rbtn_secrecy;

    private int result = 1;//1是男孩  2 是女孩 3  是保密

    private SelectSexCallBack selectSexCallBack;//结果回调接口

    public SelectSexDialog(Context mContext, SelectSexCallBack selectSexCallBack) {
        super(mContext, R.style.select_sex_dialog_style);
        this.mContext = mContext;
        this.selectSexCallBack = selectSexCallBack;
        setSelectSexDialog(mContext);
    }

    public void setSelectSexDialog(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_select_sex_layout, null);
        rgroup_selelct_sex = (RadioGroup) view.findViewById(R.id.rgroup_selelct_sex);
        rbtn_boy = (RadioButton) view.findViewById(R.id.rbtn_boy);
        rbtn_gril = (RadioButton) view.findViewById(R.id.rbtn_gril);
        rbtn_secrecy = (RadioButton) view.findViewById(R.id.rbtn_secrecy);
        txt_selelct_cancle = (TextView) view.findViewById(R.id.txt_selelct_cancle);
        txt_select_confirm = (TextView) view.findViewById(R.id.txt_select_confirm);
        rgroup_selelct_sex.setOnCheckedChangeListener(this);
        txt_select_confirm.setOnClickListener(this);
        txt_selelct_cancle.setOnClickListener(this);
        super.setContentView(view);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rbtn_boy:
                rbtn_boy.setTextColor(mContext.getResources().getColor(R.color.white));
                rbtn_gril.setTextColor(mContext.getResources().getColor(R.color.black));
                rbtn_secrecy.setTextColor(mContext.getResources().getColor(R.color.black));
                result = 1;
                break;
            case R.id.rbtn_gril:
                rbtn_boy.setTextColor(mContext.getResources().getColor(R.color.black));
                rbtn_gril.setTextColor(mContext.getResources().getColor(R.color.white));
                rbtn_secrecy.setTextColor(mContext.getResources().getColor(R.color.black));
                result = 2;
                break;
            case R.id.rbtn_secrecy:
                rbtn_boy.setTextColor(mContext.getResources().getColor(R.color.black));
                rbtn_gril.setTextColor(mContext.getResources().getColor(R.color.black));
                rbtn_secrecy.setTextColor(mContext.getResources().getColor(R.color.white));
                result = 3;
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_selelct_cancle:
                this.dismiss();
                break;
            case R.id.txt_select_confirm:
                selectSexCallBack.onSelelctSexSuccess(result);
                this.dismiss();
                break;
        }
    }

    public int getSelectResult() {
        return result;
    }
}
