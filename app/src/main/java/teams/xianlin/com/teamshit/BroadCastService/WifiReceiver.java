package teams.xianlin.com.teamshit.BroadCastService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import teams.xianlin.com.teamshit.BaseInfor.Constant;

/**
 * Created by Administrator on 2016/7/30.
 */
public class WifiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {//wifi连接上
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                Intent wifiIntent = new Intent(Constant.teams_xianlin_com_teamshit_Activity_Action);
                wifiIntent.putExtra(Constant.Wifi_Ssid, wifiInfo.getSSID());
                context.sendBroadcast(wifiIntent);
            }
        }
    }


}
