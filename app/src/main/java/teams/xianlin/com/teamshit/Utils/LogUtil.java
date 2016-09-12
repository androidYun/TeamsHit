package teams.xianlin.com.teamshit.Utils;

import android.nfc.Tag;
import android.util.Log;

/**
 * 
 * @author Administrator
 *
 */
public class LogUtil {
	private static String TAG = "Main";
	public static boolean isDebug = true;//

	public static void i(String tag) {
		if (isDebug)
			Log.i(TAG, tag);
	}

	public static void d(String tag) {
		if (isDebug)
			Log.d(TAG, tag);
	}

	public static void e(String tag) {
		if (isDebug)
			Log.e(TAG, tag);
	}

	public static void v(String tag) {
		if (isDebug)
			Log.v(TAG, tag);
	}

	public static void i(String TAG, String tag) {
		if (isDebug)
			Log.i(TAG, tag);
	}

	public static void d(String TAG, String tag) {
		if (isDebug)
			Log.d(TAG, tag);
	}

	public static void e(String TAG, String tag) {
		if (isDebug)
			Log.e(TAG, tag);
	}

	public static void v(String TAG, String tag) {
		if (isDebug)
			Log.v(TAG, tag);
	}
}
