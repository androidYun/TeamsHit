package teams.xianlin.com.teamshit.widget.DialogUtils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teams.xianlin.com.teamshit.Interface.EditNameCallBack;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;

/**
 * Created by Administrator on 2016/8/8.
 */
public class EditNameDialog extends Dialog implements View.OnClickListener {
    @Bind(R.id.edit_name)
    EditText editName;
    @Bind(R.id.txt_confirm)
    TextView txtConfirm;
    @Bind(R.id.txt_cancle)
    TextView txtCancle;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    private Context mContext;

    private View mView;

    private EditNameCallBack editNameCallBack;

    public EditNameDialog(Context mContext, EditNameCallBack editNameCallBack) {
        super(mContext, R.style.select_sex_dialog_style);
        this.mContext = mContext;
        this.editNameCallBack = editNameCallBack;
        setEditTeamNameDialog(mContext);
    }

    public void setEditTeamNameDialog(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_team_name, null);
        ButterKnife.bind(this, mView);
        super.setContentView(mView);
    }

    @OnClick({R.id.txt_confirm, R.id.txt_cancle})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_confirm:
                this.dismiss();
                if (StringUtils.isBlank(editName.getText().toString())) {
                    ToastUtil.show(mContext, "不能为空");
                    return;
                }
                editNameCallBack.onEditTeamNameSuccess(editName.getText().toString());
                break;

            case R.id.txt_cancle:
                this.dismiss();
                break;
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        txtTitle.setText(title + "");
    }
}
