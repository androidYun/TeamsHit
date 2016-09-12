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
import teams.xianlin.com.teamshit.Interface.EditTeamNameCallBack;
import teams.xianlin.com.teamshit.R;

/**
 * Created by Administrator on 2016/8/8.
 */
public class EditTeamNameDialog extends Dialog implements View.OnClickListener {
    @Bind(R.id.edit_name)
    EditText editName;
    @Bind(R.id.txt_confirm)
    TextView txtConfirm;
    @Bind(R.id.txt_cancle)
    TextView txtCancle;
    private Context mContext;

    private View mView;

    private EditTeamNameCallBack editTeamNameCallBack;

    public EditTeamNameDialog(Context mContext, EditTeamNameCallBack editTeamNameCallBack) {
        super(mContext, R.style.select_sex_dialog_style);
        this.mContext = mContext;
        this.editTeamNameCallBack = editTeamNameCallBack;
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
                editTeamNameCallBack.onEditTeamNameSuccess(editName.getText().toString());
                this.dismiss();
                break;

            case R.id.txt_cancle:
                this.dismiss();
                break;
        }
    }
}
