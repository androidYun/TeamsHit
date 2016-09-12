package teams.xianlin.com.teamshit.widget.Game;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import teams.xianlin.com.teamshit.Activity.GroupDetailActivity;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.db.Groups;

/**
 * Created by Administrator on 2016/9/8.
 */
public class GameTitleNavi extends RelativeLayout implements View.OnClickListener {

    private View inflate;

    TextView txtTimeTitle;

    TextView txtTime;

    TextView txtGameType;

    TextView txtGameNumber;

    TextView txtSet;

    private Context mContext;

    private Groups groups;

    public GameTitleNavi(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initialView();
        inflateData();

    }
    private void initialView() {
        inflate = LayoutInflater.from(mContext).inflate(R.layout.define_game_head_layout, this, true);
        txtTimeTitle = (TextView) inflate.findViewById(R.id.txt_time_title);
        txtTime = (TextView) inflate.findViewById(R.id.txt_set);
        txtSet = (TextView) inflate.findViewById(R.id.txt_time);
        txtGameType = (TextView) inflate.findViewById(R.id.txt_game_type);
        txtGameNumber = (TextView) inflate.findViewById(R.id.txt_game_number);
        txtSet = (TextView) inflate.findViewById(R.id.txt_set);
        txtSet.setOnClickListener(this);
    }

    private void inflateData() {
    }

    public void setGroupData(Groups groups) {
        this.groups = groups;
        if (groups.getGroupType() == Groups.GroupBoastGame) {
            txtGameType.setText(getResources().getText(R.string.boast_game));
        } else {
            txtGameType.setText(getResources().getText(R.string.twenty_one));
        }
        txtGameNumber.setText(groups.getGroupId() + "房间");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_set:
                if (groups != null) {
                    Intent groupDetailIntent = new Intent(mContext, GroupDetailActivity.class);
                    groupDetailIntent.putExtra(Constant.GroupId, groups.getGroupId());
                    mContext.startActivity(groupDetailIntent);
                }
                break;
        }
    }
}
