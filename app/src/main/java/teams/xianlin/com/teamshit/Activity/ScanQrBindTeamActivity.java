package teams.xianlin.com.teamshit.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.eventbus.EventBus;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.EventBus.BindTeamHitEvent;
import teams.xianlin.com.teamshit.EventBus.ScanQrStrEvent;
import teams.xianlin.com.teamshit.NetWorkResp.BindTeamHitResp;
import teams.xianlin.com.teamshit.Presenter.BindTeamHitPresenter;
import teams.xianlin.com.teamshit.PresenterView.BindTeamHitView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.widget.DialogUtils.BindDeviceFailDialog;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/8/3.
 * 扫码配置wifi
 */
public class ScanQrBindTeamActivity extends BaseActivity implements BindTeamHitView {
    final int REQUEST_CAMERA = 101;
    @Bind(R.id.navi_scan_qr)
    TitleNavi naviScanQr;//头部导航
    @Bind(R.id.btn_confirm)
    Button btnConfirm;//扫描二维码

    private BindTeamHitPresenter bindTeamHitPresenter;//请求扫描二维码内容

    private BindDeviceFailDialog deviceFailDialog;//配置wifi失败界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_bind_team);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        naviScanQr.setClickCallBack(this);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void initialData() {
        bindTeamHitPresenter = new BindTeamHitPresenter(this);
        deviceFailDialog = new BindDeviceFailDialog(ScanQrBindTeamActivity.this);
    }

    @Override
    protected void inflateView() {
        naviScanQr.setBackTitle("扫描二维码绑定队队机");
        btnConfirm.setText("去扫描二维码");

    }

    @Override
    public void onBackClick() {
        super.onBackClick();
    }

    @Override
    public void onLoadSucess(BindTeamHitResp bindTeamHitResp) {
        Intent selectorIntent = new Intent(ScanQrBindTeamActivity.this, SelectorWifiActivity.class);//配置wifi
        selectorIntent.putExtra(Constant.Device_Mac, bindTeamHitResp.getDeviceMac());//80:89:17:69:9a:fc
        startActivity(selectorIntent);
        EventBus.getDefault().post(new BindTeamHitEvent());
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        ToastUtil.show(ScanQrBindTeamActivity.this, errorMsg.getErrorMsg());
        deviceFailDialog.showDialog(errorMsg.getErrorMsg());
    }

    @Override
    public void showBindTeamHitProgress() {
        loadDialog.show();
    }

    @Override
    public void hideBindTeamHitProgress() {
        loadDialog.hide();
    }

    public void onEventMainThread(ScanQrStrEvent event) {
        String resultStr = event.getResultStr();//"http://download.www.mstching.com/?8ef12e995779dac9
        int index = StringUtils.getIndex(resultStr, "/?");
        resultStr = resultStr.substring(index + 2, resultStr.length());//获取Uuid
        bindTeamHitPresenter.LoadData(resultStr, 1, mActivity);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                    } else {
                        Intent selectWifiIntent = new Intent(ScanQrBindTeamActivity.this, MipcaActivityCapture.class);
                        selectWifiIntent.putExtra(Constant.Scan_Title_Name, "绑定设备");
                        startActivity(selectWifiIntent);
                    }
                } else {
                    Intent selectWifiIntent = new Intent(ScanQrBindTeamActivity.this, MipcaActivityCapture.class);
                    selectWifiIntent.putExtra(Constant.Scan_Title_Name, "绑定设备");
                    startActivity(selectWifiIntent);
                }


                break;
        }
    }
}
