package teams.xianlin.com.teamshit.widget.Popwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import teams.xianlin.com.teamshit.Activity.SearchFriendActivity;
import teams.xianlin.com.teamshit.Activity.SelectFriendsActivity;
import teams.xianlin.com.teamshit.Interface.SelectorTextSizeCallBack;
import teams.xianlin.com.teamshit.R;

/**
 * Created by Administrator on 2016/7/21.
 */
public class SelectorTxtSizePopWindow extends PopupWindow implements AdapterView.OnItemClickListener {
    private View conentView;
    private SelectorTextSizeCallBack selectorTextSizeCallBack;//回调接口

    private List<String> lists = new ArrayList<String>();

    @SuppressLint("InflateParams")
    public SelectorTxtSizePopWindow(final Activity context, SelectorTextSizeCallBack selectorTextSizeCallBack) {
        this.selectorTextSizeCallBack = selectorTextSizeCallBack;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popuwindow_selector_txt_size, null);
        for (int i = 0; i < 15; i++) {
            lists.add(i + "");
        }
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(150);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(450);
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
        ListView lvi_selctor_txt_size = (ListView) conentView.findViewById(R.id.lvi_selctor_txt_size);
        lvi_selctor_txt_size.setAdapter(new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, lists));
        lvi_selctor_txt_size.setOnItemClickListener(this);

    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1] - this.getHeight());
        } else {
            this.dismiss();
        }

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        this.dismiss();
        selectorTextSizeCallBack.SelectorOnSuccess(position * 3 + 35);
    }
}
