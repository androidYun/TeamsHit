package teams.xianlin.com.teamshit.Utils;

import android.content.Context;
import android.widget.Toast;

import teams.xianlin.com.teamshit.TeamsHitApplication;

public class ToastUtil {
	public static Toast toast;

	public static void show(Context context, String message) {

		if (toast == null) {
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		}
		toast.setText(message);
		toast.show();
	}

	public static void show(String message) {
		if (toast == null) {
			toast = Toast.makeText(TeamsHitApplication.getInstance(), message,
					Toast.LENGTH_SHORT);
		}
		toast.setText(message);
		toast.show();
	}
}
