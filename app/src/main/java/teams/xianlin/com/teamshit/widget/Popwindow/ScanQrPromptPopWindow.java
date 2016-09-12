package teams.xianlin.com.teamshit.widget.Popwindow;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import teams.xianlin.com.teamshit.Activity.MipcaActivityCapture;
import teams.xianlin.com.teamshit.R;

/**
 * Created by Administrator on 2016/8/4.
 */
public class ScanQrPromptPopWindow extends BasePopWindow {
    private Button btn_confirm;


    public ScanQrPromptPopWindow(Context context) {
        super(context);
        inflateData();
    }

    @Override
    public View initialView() {
        return inflateView(R.layout.dialog_bind_device_fail);
    }

    @Override
    public PopupWindow initiaPop() {
        return initialPopWindow(mContenView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void bindView(View mContenView) {
        btn_confirm = (Button) mContenView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public View inflateView(int id) {
        return super.inflateView(id);
    }

    @Override
    public void inflateData() {
        btn_confirm.setText("去扫描二维码");
    }

    public void showPopWindow(View parent) {
        if (!mPopuWindow.isShowing()) {
            mPopuWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent selectWifiIntent = new Intent(mContext, MipcaActivityCapture.class);
        mContext.startActivity(selectWifiIntent);
    }
}
