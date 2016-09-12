package teams.xianlin.com.teamshit.widget.Popwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import teams.xianlin.com.teamshit.Interface.SelectorPaintWidthCallBack;
import teams.xianlin.com.teamshit.R;

/**
 * Created by Administrator on 2016/7/22.
 */
public class SelectorPaintWidthPopWindow extends PopupWindow implements AdapterView.OnItemClickListener {
    private View conentView;

    private Context mContext;

    private SelectorPaintWidthCallBack selectorPaintWidthCallBack;//回调接口

    private List<Integer> lists = new ArrayList<>();

    @SuppressLint("InflateParams")
    public SelectorPaintWidthPopWindow(final Activity context, SelectorPaintWidthCallBack selectorPaintWidthCallBack) {
        this.selectorPaintWidthCallBack = selectorPaintWidthCallBack;
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popuwindow_selector_txt_size, null);
        lists.add(R.drawable.dot_01);
        lists.add(R.drawable.dot_02);
        lists.add(R.drawable.dot_03);
        lists.add(R.drawable.dot_04);
        lists.add(R.drawable.dot_05);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(150);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(500);
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
        lvi_selctor_txt_size.setAdapter(new adapter());
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
        selectorPaintWidthCallBack.SelectorPaintWidthOnSuccess(position+1);

    }

    class adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int i) {
            return lists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.list_item_img, viewGroup, false);
                viewHolder.img_selector = (ImageView) view.findViewById(R.id.img_item);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.img_selector.setImageResource(lists.get(i));
            return view;
        }

        class ViewHolder {
            ImageView img_selector;
        }
    }
}
