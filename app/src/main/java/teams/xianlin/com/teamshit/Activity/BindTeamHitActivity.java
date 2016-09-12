package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.Adapter.TeamHitDeviceAdapter;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.HttpDefine;
import teams.xianlin.com.teamshit.Bean.DeviceDetailBean;
import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.EventBus.BindTeamHitEvent;
import teams.xianlin.com.teamshit.Interface.EditNameCallBack;
import teams.xianlin.com.teamshit.Interface.OnItemClickListener;
import teams.xianlin.com.teamshit.NetWorkResp.BindTeamHitResp;
import teams.xianlin.com.teamshit.NetWorkResp.BuzzerSwitchResp;
import teams.xianlin.com.teamshit.NetWorkResp.EditTeamNameResp;
import teams.xianlin.com.teamshit.NetWorkResp.GetDeviceResp;
import teams.xianlin.com.teamshit.NetWorkResp.IndicatorSwitchResp;
import teams.xianlin.com.teamshit.Presenter.BindTeamHitPresenter;
import teams.xianlin.com.teamshit.Presenter.BuzzerSwitchPresenter;
import teams.xianlin.com.teamshit.Presenter.EditTeamNamePresenter;
import teams.xianlin.com.teamshit.Presenter.GetDeviceListPresenter;
import teams.xianlin.com.teamshit.Presenter.IndicatorSwitchPresenter;
import teams.xianlin.com.teamshit.PresenterView.BindTeamHitView;
import teams.xianlin.com.teamshit.PresenterView.BuzzerSwitchView;
import teams.xianlin.com.teamshit.PresenterView.EditTeamNameView;
import teams.xianlin.com.teamshit.PresenterView.GetDeviceListView;
import teams.xianlin.com.teamshit.PresenterView.IndicatorSwitchView;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.widget.DialogUtils.EditNameDialog;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/7/29.
 * 设备列表添加西你设备
 */
public class BindTeamHitActivity extends BaseActivity implements EditTeamNameView, EditNameCallBack, GetDeviceListView, BuzzerSwitchView, IndicatorSwitchView, BindTeamHitView, OnItemClickListener<DeviceDetailBean> {

    @Bind(R.id.navi_friend)
    TitleNavi naviFriend;
    @Bind(R.id.img_add_device)
    ImageView imgAddDevice;
    @Bind(R.id.layout_add_device)
    RelativeLayout layoutAddDevice;////添加设备
    @Bind(R.id.rvi_device)
    RecyclerView rviDevice;////设备列表

    private TeamHitDeviceAdapter teamHitDeviceAdapter;//适配器

    private GetDeviceListPresenter getDeviceListPresenter;//获取设备列表

    private BuzzerSwitchPresenter buzzerSwitchPresenter;//蜂鸣器开关

    private IndicatorSwitchPresenter indicatorSwitchPresenter;//指示灯开关

    private BindTeamHitPresenter bindTeamHitPresenter;//解除绑定

    private EditTeamNamePresenter editTeamNamePresenter;//修改设备名称

    private EditNameDialog editNameDialog;//修改姓名回调方法

    private List<DeviceDetailBean> detailBeanList;//设备列表

    private DeviceDetailBean mDeviceDetailBean;//操作当前的设备信息

    private int mPosition;//操作当前设备的位置;

    private String mNewDeviceName;//修改设备名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_team_hit);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        rviDevice.setLayoutManager(new LinearLayoutManager(this));
        layoutAddDevice.setOnClickListener(this);
        naviFriend.setClickCallBack(this);
    }

    @Override
    protected void initialData() {
        detailBeanList = new ArrayList<>();
        getDeviceListPresenter = new GetDeviceListPresenter(this);
        buzzerSwitchPresenter = new BuzzerSwitchPresenter(this);
        indicatorSwitchPresenter = new IndicatorSwitchPresenter(this);
        bindTeamHitPresenter = new BindTeamHitPresenter(this);
        editTeamNamePresenter = new EditTeamNamePresenter(this);
        editNameDialog = new EditNameDialog(BindTeamHitActivity.this, this);
        editNameDialog.setTitle("修改设备名称");
        getDeviceListPresenter.loadData(BindTeamHitActivity.this);

    }

    @Override
    protected void inflateView() {
        loadDialog.setTitle("正在连接请稍后...");
        naviFriend.setBackTitle("配置设备连接路由器");
    }

    @Override
    public void onLoadSucess(GetDeviceResp getDeviceResp) {
        if (detailBeanList != null) {
            detailBeanList.clear();
        }
        detailBeanList.addAll(getDeviceResp.getDeviceList());
        if (teamHitDeviceAdapter == null) {
            teamHitDeviceAdapter = new TeamHitDeviceAdapter(BindTeamHitActivity.this, detailBeanList);
            teamHitDeviceAdapter.setOnItemClickListener(this);
            rviDevice.setAdapter(teamHitDeviceAdapter);
        } else {
            teamHitDeviceAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadFail(ErrorMsg errorMsg) {
        ToastUtil.show(BindTeamHitActivity.this, "" + errorMsg.getErrorMsg());
    }

    @Override
    public void showGetDevicesProgress() {
        loadDialog.show();
    }

    @Override
    public void hideGetDevicesProgress() {
        loadDialog.hide();
    }

    @Override
    public void onLoadSucess(BindTeamHitResp bindTeamHitResp) {
        if (bindTeamHitResp.getCommand() == HttpDefine.UnBind_Team_Hit_RESP) {
            detailBeanList.remove(mPosition);
            teamHitDeviceAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showBindTeamHitProgress() {
        loadDialog.show();
    }

    @Override
    public void hideBindTeamHitProgress() {
        loadDialog.cancel();
    }

    @Override
    public void onLoadSucess(BuzzerSwitchResp buzzerSwitchResp) {
        detailBeanList.get(mPosition).setBuzzer(1 - mDeviceDetailBean.getBuzzer());//如果之前为0 是关闭状态，那请求 开启状态 就是1-mDeviceDetailBean.getBuzzer()
        teamHitDeviceAdapter.notifyItemChanged(mPosition);
    }

    @Override
    public void showBuzzerSwitchProgress() {
        loadDialog.show();
    }

    @Override
    public void hideBuzzerSwitchProgress() {
        loadDialog.hide();
    }

    @Override
    public void onLoadSucess(IndicatorSwitchResp indicatorSwitchResp) {
        detailBeanList.get(mPosition).setIndicator(1 - mDeviceDetailBean.getIndicator());//如果之前为0 是关闭状态，那请求 开启状态 就是1-mDeviceDetailBean.getBuzzer()
        teamHitDeviceAdapter.notifyItemChanged(mPosition);
    }

    @Override
    public void showIndicatorSwitchProgress() {
        loadDialog.show();
    }

    @Override
    public void hideIndicatorSwitchProgress() {
        loadDialog.hide();
    }

    @Override
    public void onLoadSucess(EditTeamNameResp editTeamNameResp) {
        detailBeanList.get(mPosition).setDeviceName(mNewDeviceName);//如果之前为0 是关闭状态，那请求 开启状态 就是1-mDeviceDetailBean.getBuzzer()
        teamHitDeviceAdapter.notifyItemChanged(mPosition);
    }

    @Override
    public void showEditTeamNameProgress() {
        loadDialog.show();
    }

    @Override
    public void hideEditTeamNameProgress() {
        loadDialog.hide();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.layout_add_device:
                Intent selectorIntent = new Intent(BindTeamHitActivity.this, ScanQrBindTeamActivity.class);
                startActivity(selectorIntent);
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position, DeviceDetailBean model) {
        mDeviceDetailBean = model;
        mPosition = position;
        LogUtil.d("开始" + view.getId() + "        " + R.id.txt_config_wifi);
        switch (view.getId()) {

            case R.id.txt_buzzer_switch:
                buzzerSwitchPresenter.loadData(1 - mDeviceDetailBean.getBuzzer(), mDeviceDetailBean.getUuid(), mActivity);
                break;
            case R.id.txt_light_switch:
                indicatorSwitchPresenter.loadData(1 - mDeviceDetailBean.getIndicator(), mDeviceDetailBean.getUuid(), mActivity);
                break;
            case R.id.txt_modify_device_name:
                editNameDialog.show();
                break;
            case R.id.txt_handler:
                bindTeamHitPresenter.LoadData(mDeviceDetailBean.getUuid(), 2, mActivity);
                break;
            case R.id.txt_config_wifi:
                Intent selectorWifiIntent = new Intent(BindTeamHitActivity.this, SelectorWifiActivity.class);
                selectorWifiIntent.putExtra(Constant.Device_Mac, mDeviceDetailBean.getDeviceMac());
                startActivity(selectorWifiIntent);
                break;

        }
    }

    @Override
    public void onItemLongClick(View view, int position, DeviceDetailBean model) {

    }

    @Override
    public void onEditTeamNameSuccess(String result) {
        mNewDeviceName = result;//给设备名赋值
        editTeamNamePresenter.loadData(BindTeamHitActivity.this, mDeviceDetailBean.getUuid(), result);
    }

    public void onEventMainThread(BindTeamHitEvent event) {
        getDeviceListPresenter.loadData(BindTeamHitActivity.this);
    }
}
