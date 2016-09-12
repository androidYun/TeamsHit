package teams.xianlin.com.teamshit.widget.Popwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.ScanResult;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import teams.xianlin.com.teamshit.Adapter.HolderAdapter;
import teams.xianlin.com.teamshit.Interface.SelectoreWifi;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;

/**
 * Created by Administrator on 2016/7/30.
 */
public class SelectorWifiPopWindow extends PopupWindow implements AdapterView.OnItemClickListener, View.OnClickListener {
    private View conentView;

    private Context mContext;

    private SelectoreWifi selectoreWifi;//回调接口

    private List<ScanResult> scanResultLists;

    private WifiAdapter wifiAdapter;

    private TextView txt_search;

    private ListView liv_selector_wifi;

    @SuppressLint("InflateParams")
    public SelectorWifiPopWindow(Activity context, List<ScanResult> scanResultLists, SelectoreWifi selectoreWifi) {
        this.selectoreWifi = selectoreWifi;
        this.mContext = context;
        this.scanResultLists = scanResultLists;
        initialView();
        initialData();
    }

    private void initialView() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popwindow_selector_wifi, null);
        txt_search = (TextView) conentView.findViewById(R.id.txt_search);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
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

    private void initialData() {
        liv_selector_wifi = (ListView) conentView.findViewById(R.id.liv_selector_wifi);
        wifiAdapter = new WifiAdapter(mContext, scanResultLists);
        liv_selector_wifi.setAdapter(wifiAdapter);
        liv_selector_wifi.setOnItemClickListener(this);
        txt_search.setOnClickListener(this);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }
    }

    public void adapterNotifyDataSetChanged(List<ScanResult> lists) {
            wifiAdapter = new WifiAdapter(mContext, lists);
            liv_selector_wifi.setAdapter(wifiAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        this.dismiss();
        selectoreWifi.onSelelctWifiSuccess(1, scanResultLists.get(position));
    }

    class WifiAdapter extends HolderAdapter<ScanResult, SelectorWifiPopWindow.WifiAdapter.ViewHolder> {
        private Context mContext;
        private List<ScanResult> listData;

        public WifiAdapter(Context context, List<ScanResult> listData) {
            super(context, listData);
            this.mContext = context;
            this.listData = listData;
        }
        @Override
        public View buildConvertView(LayoutInflater layoutInflater, ScanResult scanResult, int position) {
            return inflate(R.layout.list_item_wifi);
        }

        @Override
        public ViewHolder buildHolder(View convertView, ScanResult scanResult, int position) {
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txt_ssid = (TextView) convertView.findViewById(R.id.txt_ssid);
            viewHolder.txt_bssid = (TextView) convertView.findViewById(R.id.txt_bssid);
            return viewHolder;
        }

        @Override
        public void bindViewDatas(ViewHolder holder, ScanResult scanResult, int position) {
            holder.txt_ssid.setText("" + scanResult.SSID);
            holder.txt_bssid.setText("" + scanResult.BSSID);
        }

        class ViewHolder {
            TextView txt_ssid;

            TextView txt_bssid;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_search:
                selectoreWifi.onSelelctWifiSuccess(2, null);
                break;
        }
    }
}
