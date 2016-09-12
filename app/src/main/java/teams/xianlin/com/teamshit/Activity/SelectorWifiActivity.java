package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.Interface.SelectoreWifi;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.Utils.WifiAdminUtils;
import teams.xianlin.com.teamshit.widget.DialogUtils.ConfigWifiPromptDialog;
import teams.xianlin.com.teamshit.widget.Popwindow.SelectorWifiPopWindow;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/8/1.
 * 输入配置wifi的密码
 */
public class SelectorWifiActivity extends BaseActivity implements SelectoreWifi {
    @Bind(R.id.navi_wifi)
    TitleNavi naviWifi;
    @Bind(R.id.txt_wifi_name)
    TextView txtWifiName;
    @Bind(R.id.img_add_device)
    ImageView imgAddDevice;
    @Bind(R.id.layout_add_device)
    RelativeLayout layoutAddDevice;//选择Wifi
    @Bind(R.id.edit_pwd)
    EditText editPwd;//输入密码
    @Bind(R.id.btn_confirm)
    Button btnConfirm;//开始匹配

    private Handler handler;//异步处理开启线程

    private WifiAdminUtils mWifiAdminUtils;//获取wifi

    private List<ScanResult> mScanResultList;//存放wifi的列表

    private ScanResult mScanResult;//选择配置的wifi

    private SelectorWifiPopWindow selectorWifiPopWindow;//选择wifi  的PopWindow

    private ConfigWifiPromptDialog configWifiPromptDialog;

    private String mDeviceMac;//设备编号

    private String mHotSsid;//要连接热点的Ssid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_wifi);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        layoutAddDevice.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        naviWifi.setClickCallBack(this);
    }

    @Override
    protected void initialData() {
        // mDeviceMac = getIntent().getStringExtra(Constant.Device_Mac);
        mDeviceMac = "00:1a:13:30:23:86";
        mWifiAdminUtils = new WifiAdminUtils(SelectorWifiActivity.this);
        mWifiAdminUtils.startScan();
        mScanResultList = new ArrayList<>();
        handler = new Handler();
        selectorWifiPopWindow = new SelectorWifiPopWindow(SelectorWifiActivity.this, mScanResultList, this);
        configWifiPromptDialog = new ConfigWifiPromptDialog(SelectorWifiActivity.this);
    }

    @Override
    protected void inflateView() {
        btnConfirm.setText("开始匹配");
        configWifiPromptDialog.showDialog();
        naviWifi.setBackTitle("配置设备链接路由器");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.layout_add_device:
                handler.post(runnable);
                break;
            case R.id.btn_confirm:
                if (mScanResult == null) {
                    ToastUtil.show(SelectorWifiActivity.this, "请选择要配置的wifi");
                    return;
                }
                if (!isExitHot()) {
                    ToastUtil.show(SelectorWifiActivity.this, "请开启设备热点，长按五秒钟即开启热点");
                    return;
                }
                handler.removeCallbacks(runnable);
                Intent linkWifiIntent = new Intent(SelectorWifiActivity.this, LinkWifiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.ScanResult, mScanResult);
                bundle.putString(Constant.WifiPwd, editPwd.getText().toString());
                bundle.putString(Constant.Wifi_Ssid, mHotSsid);
                linkWifiIntent.putExtras(bundle);
                startActivity(linkWifiIntent);
                finish();
                break;
        }
    }

    /**
     * 判断配置wifi的热点是否存在
     *
     * @return
     */
    private boolean isExitHot() {
        for (ScanResult scanResult : mScanResultList) {
            if (scanResult.BSSID.equals(mDeviceMac)) {
                mHotSsid = scanResult.SSID;
                return true;
            }

        }
        return false;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            List<ScanResult> lists = getWifiScanResultList();
            if (lists == null) {
                handler.postDelayed(runnable, 10000);
                return;
            }
            if (lists != null) {
                mScanResultList.clear();
            }
            mScanResultList.addAll(lists);//添加所有列表信息
            if (!selectorWifiPopWindow.isShowing()) {
                selectorWifiPopWindow.showPopupWindow(getWindow().getDecorView());
            } else {
                selectorWifiPopWindow.adapterNotifyDataSetChanged(mScanResultList);
            }
            handler.postDelayed(runnable, 10000);
        }
    };

    @Override
    public void onSelelctWifiSuccess(int type, ScanResult scanResult) {//1是选择成功  2，是重新搜索
        if (type == 1) {
            mScanResult = scanResult;
            txtWifiName.setText("" + mScanResult.SSID);
            handler.removeCallbacks(runnable);
        } else if (type == 2) {
            List<ScanResult> lists = getWifiScanResultList();
            if (lists != null) {
                mScanResultList.clear();
            }
            mScanResultList.addAll(lists);//添加所有列表信息
            selectorWifiPopWindow.adapterNotifyDataSetChanged(mScanResultList);
        }
    }

    public List<ScanResult> getWifiScanResultList() {
        mWifiAdminUtils.startScan();
        List<ScanResult> scanResults = mWifiAdminUtils.getWifiList();
        return scanResults;
    }

    @Override
    public void onBackClick() {
        super.onBackClick();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /**
             * 返回键监听
             */
            handler.removeCallbacks(runnable);
        }
        return false;
    }
}
