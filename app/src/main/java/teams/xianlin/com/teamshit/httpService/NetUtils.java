package teams.xianlin.com.teamshit.httpService;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/7/6.
 */
public class NetUtils {
    public static NetUtils getinStance() {
        return createNetUtils.netUtils;
    }

    static class createNetUtils {
        final static NetUtils netUtils = new NetUtils();
    }

    // 检测网络是否连接
    public boolean isNetworkAvailable(Context mActivity) {
        if (null == mActivity) {
            return false;
        }
        Context context = mActivity.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
