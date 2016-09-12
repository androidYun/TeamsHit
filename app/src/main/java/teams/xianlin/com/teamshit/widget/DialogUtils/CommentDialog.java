package teams.xianlin.com.teamshit.widget.DialogUtils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.CommentItemBean;
import teams.xianlin.com.teamshit.Interface.OnItemClickListener;
import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleListResp;
import teams.xianlin.com.teamshit.PrensenterModel.CirclePresenter;
import teams.xianlin.com.teamshit.Presenter.OperateFriendCirclePresenter;
import teams.xianlin.com.teamshit.R;

/**
 * Created by Administrator on 2016/8/26.
 */
public class CommentDialog extends Dialog implements
        android.view.View.OnClickListener {
    private Context mContext;

    private GetFriendCircleListResp.FriendCircleItem friendCircleItem;//你点那个朋友圈

    private CommentItemBean commentItemBean;//评论Itenm

    private int mCommentPosition;//你点击的第几条评论

    private int friendItemPosition;//Item位置

    private String mUserId;

    private OnItemClickListener onItemClickListener;

    private TextView deleteTv;

    public CommentDialog(Context context, OnItemClickListener onItemClickListener,
                         GetFriendCircleListResp.FriendCircleItem friendCircleItem, int friendItemPosition, int mCommentPosition) {
        super(context, R.style.comment_dialog);
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
        this.friendCircleItem = friendCircleItem;
        this.mCommentPosition = mCommentPosition;
        this.friendItemPosition = friendItemPosition;
        if (friendCircleItem != null && friendCircleItem.getCommentLists() != null) {
            commentItemBean = friendCircleItem.getCommentLists().get(mCommentPosition);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comment);
        initWindowParams();
        initView();
    }

    private void initWindowParams() {
        Window dialogWindow = getWindow();
        // 获取屏幕宽、高用
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (display.getWidth() * 0.65); // 宽度设置为屏幕的0.65

        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
    }

    private void initView() {
        mUserId = String.valueOf(TeamHitContext.getInstance().getUserId());
        TextView copyTv = (TextView) findViewById(R.id.copyTv);
        copyTv.setOnClickListener(this);
        deleteTv = (TextView) findViewById(R.id.deleteTv);
        String commentUserId = "";
        if (commentItemBean != null && commentItemBean.getUser() != null) {
            commentUserId = String.valueOf(commentItemBean.getUser().getUserId());
        }
        if (mUserId.equals(commentUserId)) {
            deleteTv.setVisibility(View.VISIBLE);
        } else {
            deleteTv.setVisibility(View.GONE);
        }
        deleteTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copyTv:
                if (commentItemBean != null) {
                    ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(commentItemBean.getContent());
                }
                dismiss();
                break;
            case R.id.deleteTv:
                if (onItemClickListener != null && commentItemBean != null) {
                    deleteTv.setTag(mCommentPosition);//评论的位置
                    onItemClickListener.onItemClick(deleteTv, friendItemPosition, friendCircleItem);//说说的位置
                }
                dismiss();
                break;
            default:
                break;
        }
    }
}
