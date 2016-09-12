package teams.xianlin.com.teamshit.widget.Popwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import java.security.PublicKey;

import teams.xianlin.com.teamshit.R;

/**
 * Created by Administrator on 2016/8/4.
 */
public abstract class BasePopWindow extends PopupWindow implements View.OnClickListener, AdapterView.OnItemClickListener {
    private LayoutInflater inflater;//加载布局

    public View mContenView;//初始化布局

    public PopupWindow mPopuWindow;//初始化Windo

    public Context mContext;

    public BasePopWindow(Context context) {
        super(context);
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        initialView();
        initiaPop();
        bindView(mContenView);
    }

    public PopupWindow initialPopWindow(View conentView, int width, int height) {
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(width);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(height);
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
        mPopuWindow = this;
        return mPopuWindow;
    }

    public View inflateView(int id) {
        mContenView = inflater.inflate(id, null, false);
        return mContenView;
    }

    public abstract View initialView();

    public abstract PopupWindow initiaPop();

    public abstract void bindView(View mContenView);

    public abstract void inflateData();


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
