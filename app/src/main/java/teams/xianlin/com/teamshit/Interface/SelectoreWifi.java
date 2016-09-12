package teams.xianlin.com.teamshit.Interface;

import android.net.wifi.ScanResult;

/**
 * Created by Administrator on 2016/7/30.
 */
public interface SelectoreWifi {
    void onSelelctWifiSuccess(int type,ScanResult scanResult);//1是选择成功  2  是点击搜索
}
