package teams.xianlin.com.teamshit.widget.Popwindow;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import teams.xianlin.com.teamshit.Activity.SearchFriendActivity;
import teams.xianlin.com.teamshit.Activity.SelectFriendsActivity;
import teams.xianlin.com.teamshit.Activity.StartGroupChatActivity;
import teams.xianlin.com.teamshit.R;

public class SelectorChatWindow extends PopupWindow {
    private View conentView;


    @SuppressLint("InflateParams")
    public SelectorChatWindow(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popupwindow_add, null);

        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);


        RelativeLayout re_addfriends = (RelativeLayout) conentView.findViewById(R.id.re_addfriends);//re_addfriends
        RelativeLayout re_chatroom = (RelativeLayout) conentView.findViewById(R.id.re_chatroom);//发起聊天
        RelativeLayout re_scanner = (RelativeLayout) conentView.findViewById(R.id.re_scanner);//添加好友
        re_addfriends.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(context, StartGroupChatActivity.class));
                intent.putExtra("createGroup", true);
                context.startActivity(intent);
                SelectorChatWindow.this.dismiss();

            }

        });
        re_chatroom.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, StartGroupChatActivity.class));
//                SelectorChatWindow.this.dismiss();
                RongIM.getInstance().joinChatRoom("1001", 10, new RongIMClient.OperationCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }

        });
        re_scanner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SearchFriendActivity.class));
                SelectorChatWindow.this.dismiss();
            }
        });


    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }
}
