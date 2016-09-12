package teams.xianlin.com.teamshit.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.rong.eventbus.EventBus;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.EventBus.BindTeamHitEvent;
import teams.xianlin.com.teamshit.NetWorkResp.ConfigWifiResp;
import teams.xianlin.com.teamshit.Presenter.ConfigWifiPresenter;
import teams.xianlin.com.teamshit.PresenterView.ConfigWifiView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.Utils.WifiAdminUtils;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/8/1.
 * 连接wifi
 */
public class LinkWifiActivity extends BaseActivity implements ConfigWifiView {

    TitleNavi naviLinkWifi;
    TextView txtLinkState;
    ProgressBar progress;
    Button btnConfirm;
    ImageView imgWait;//等待
    private WifiAdminUtils mWifiAdminUtils;//wifi开发工具

    private ScanResult mScanResult;//wifi参数

    private String mWifiPwd;//密码

    private String mHotSsid;//ssId//需要连接的wifi名称

    private Handler handler;

    private ConfigWifiPresenter configWifiPresenter;

    private String mCurrentSsid;//获取在配置wifi的时候连接wifi的ssid

    private String linkWifiSsid;//目前连接wifi的Ssid

    private Boolean isConfigSuccess = false;//是否配置成功

    private int mCycleTimes = 0;//循环请求次数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_wifi);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        naviLinkWifi = (TitleNavi) findViewById(R.id.navi_link_wifi);
        txtLinkState = (TextView) findViewById(R.id.txt_link_state);
        progress = (ProgressBar) findViewById(R.id.progress);
        imgWait = (ImageView) findViewById(R.id.img_wait);
        btnConfirm.setOnClickListener(this);
        naviLinkWifi.setClickCallBack(this);

    }

    @Override
    protected void initialData() {
        Bundle bundle = getIntent().getExtras();//获取数据
        mScanResult = bundle.getParcelable(Constant.ScanResult);
        mWifiPwd = bundle.getString(Constant.WifiPwd);//wifi密码
        mHotSsid = bundle.getString(Constant.Wifi_Ssid);
        configWifiPresenter = new ConfigWifiPresenter(this);//网络请求
        handler = new Handler();//创建一个线程
        mWifiAdminUtils = new WifiAdminUtils(LinkWifiActivity.this);
        mWifiAdminUtils.startScan();
        mCurrentSsid = mWifiAdminUtils.getCurrentSSID().replaceAll("\"", "");
        handler.post(runnable);//开启一个线程  也就是开启一个定时器  轮回 获取热点
        //筛选一个广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.teams_xianlin_com_teamshit_Activity_Action);//当wifi连接的时候接受一个wifi广播、、用来判断设备热点时候开启了
        registerReceiver(broadcastReceiver, intentFilter);
    }


    @Override
    protected void inflateView() {
        naviLinkWifi.setBackTitle("连接设备");
        btnConfirm.setText("取消");
        Glide.with(this).load(R.drawable.gif_config_wifi).into(imgWait);
    }

    @Override
    public void onLoadSucess(ConfigWifiResp configWifiResp) {
        txtLinkState.setText("配置成功");
        isConfigSuccess = true;//检测配置成功
        mWifiAdminUtils.disconnectWifi(mWifiAdminUtils.getNetworkId());
        if (!StringUtils.isBlank(mCurrentSsid)) {
            mWifiAdminUtils.connectConfiguratedWifi(mCurrentSsid);
        }
        EventBus.getDefault().post(new BindTeamHitEvent());//配置成功之后，再次请求网络，更新设备列表
        Intent configWifiSuccess = new Intent(LinkWifiActivity.this, ConfigWifiSuccessActivity.class);
        startActivity(configWifiSuccess);
        finish();
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        handler.postDelayed(runnable, 2000);
    }

    @Override
    public void showConfigWifiProgress() {
        loadDialog.show();
    }

    @Override
    public void hideConfigWifiProgress() {
        loadDialog.cancel();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mCycleTimes++;
            if (mCycleTimes > 10) {//请求时间超过20秒  定位请求失败
                handler.removeCallbacks(this);
                Intent configWifiSuccess = new Intent(LinkWifiActivity.this, ConfigWifiFailActivity.class);
                startActivity(configWifiSuccess);
                finish();
            }
            if (!isExitWifi(mHotSsid)) {//如果这个热点不存在 就不能进行 连接，继续轮回查找这个wifi
                ToastUtil.show(mActivity, "热点不存在 请打开热点在链接");
                handler.postDelayed(this, 2000);
                return;
            }
            mWifiAdminUtils.openWifi();//先打开wifi
            while (mWifiAdminUtils.checkState() == WifiManager.WIFI_STATE_ENABLING) {
                try {
                    Thread.sleep(100);// 为了避免程序一直while循环，让它睡个100毫秒检测……
                } catch (InterruptedException ie) {
                }
            }
            WifiConfiguration wifiConfig = mWifiAdminUtils.CreateWifiInfo(mHotSsid, "", 1);
            boolean enabled = mWifiAdminUtils.addNetWork(wifiConfig);
            boolean reConnect = mWifiAdminUtils.reConnect();
            if (enabled) {
                handler.removeCallbacks(this);
                configWifiPresenter.loadData(mScanResult.SSID, 4, mWifiPwd, 0, LinkWifiActivity.this);
            } else {
                handler.postDelayed(this, 2000);//链接失败继续轮回
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_confirm:
                // configWifiPresenter.loadData(mScanResult.SSID, mWifiAdminUtils.getSecurity(mScanResult), mWifiPwd, 0, LinkWifiActivity.this);
                finish();
                break;
        }
    }

    @Override
    public void onBackClick() {
        super.onBackClick();
        boolean wlife = mWifiAdminUtils.connectConfiguratedWifi(mCurrentSsid);//如果点击返回那就去连接之前配置好的wifi
        handler.removeCallbacks(runnable);
    }

    /**
     * 判断是否存下这个热点
     *
     * @param ssid
     * @return
     */
    public boolean isExitWifi(String ssid) {
        mWifiAdminUtils.startScan();
        List<ScanResult> wifiList = mWifiAdminUtils.getWifiList();
        if (wifiList == null) {
            return false;
        }
        for (int i = 0; i < wifiList.size(); i++) {
            if (wifiList.get(i).SSID.equals(ssid)) {
                return true;
            }
        }
        return false;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {//收到wifi链接成功后去请求  配置wifi接口
        @Override
        public void onReceive(Context context, Intent intent) {
            linkWifiSsid = intent.getStringExtra(Constant.Wifi_Ssid);
            if (linkWifiSsid.contains(mCurrentSsid) && !isConfigSuccess) {//配置成功 就不需要再次请求了
                configWifiPresenter.loadData(mScanResult.SSID, mWifiAdminUtils.getSecurity(mScanResult), mWifiPwd, 0, LinkWifiActivity.this);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
