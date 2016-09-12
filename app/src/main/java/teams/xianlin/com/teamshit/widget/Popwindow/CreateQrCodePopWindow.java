package teams.xianlin.com.teamshit.widget.Popwindow;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import teams.xianlin.com.teamshit.Interface.CreateQrCodeCallBack;
import teams.xianlin.com.teamshit.Interface.SaveBitMapCallBack;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.ImageUtils;
import teams.xianlin.com.teamshit.Utils.QrCodeUtils;
import teams.xianlin.com.teamshit.Utils.SysUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;

/**
 * Created by Administrator on 2016/7/27.
 */
public class CreateQrCodePopWindow extends PopupWindow implements View.OnClickListener, SaveBitMapCallBack {
    private View conentView;
    private CreateQrCodeCallBack createQrCodeCallBack;

    private TextView txt_confirm;

    private TextView txt_cancle;

    private TextView txt_txtsize;

    private EditText edit_content;

    private Context mContext;

    private int textLegth = 140;

    @SuppressLint("InflateParams")
    public CreateQrCodePopWindow(final Activity context, CreateQrCodeCallBack createQrCodeCallBack) {
        this.createQrCodeCallBack = createQrCodeCallBack;
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popuwindow_create_qrcode, null);
        setPopWindowParam();
        initialView();

    }


    private void initialView() {
        edit_content = (EditText) conentView.findViewById(R.id.edit_content);
        txt_txtsize = (TextView) conentView.findViewById(R.id.txt_txtsize);
        txt_cancle = (TextView) conentView.findViewById(R.id.txt_cancle);
        txt_confirm = (TextView) conentView.findViewById(R.id.txt_confirm);
        txt_confirm.setOnClickListener(this);
        txt_cancle.setOnClickListener(this);
        edit_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 140) {
                    edit_content.setEnabled(false);
                    ToastUtil.show(mContext, "输入字数不能超过140");
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                int textSize = 140 - editable.length();
                txt_txtsize.setText("你还可以输入" + textSize + "字");
            }
        });
    }

    private void setPopWindowParam() {
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
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
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        this.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_confirm:
                int width = SysUtils.getInatcne().getWindowWidth(mContext) / 5 * 2;
                int height = width;
                Bitmap qrImage = QrCodeUtils.getInstance().createQRImage(edit_content.getText().toString(), width, height);
                ImageUtils.getInstance().saveBitmapToPath(qrImage, this, mContext, 3);
                break;

            case R.id.txt_cancle:
                this.dismiss();
                break;
        }
    }

    @Override
    public void SaveBitMapOnSuccess(int saveCode, String result) {
        createQrCodeCallBack.onCreateQrCodeSuccess(result);
        this.dismiss();
    }
}
