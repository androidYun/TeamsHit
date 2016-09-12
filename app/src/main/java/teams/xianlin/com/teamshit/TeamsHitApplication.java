package teams.xianlin.com.teamshit;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import io.rong.imkit.RongIM;
import io.rong.message.GroupNotificationMessage;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.RongYun.Provider.RealTimeLocationMessageProvider;
import teams.xianlin.com.teamshit.RongYun.SealAppContext;

/**
 * Created by Administrator on 2016/7/6.
 */
public class TeamsHitApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        TeamHitContext.initial(this);
        initialThirdLibrary();
    }

    private void initialThirdLibrary() {
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
            SealAppContext.init(this);
            try {
                RongIM.registerMessageType(GroupNotificationMessage.class);
                //  RongIM.registerMessageTemplate(new TeamHitPassNoteProvider());
                //  RongIM.registerMessageTemplate(new ContactNotificationMessageProvider());
                RongIM.registerMessageTemplate(new RealTimeLocationMessageProvider());
                // RongIM.registerMessageTemplate(new GroupNotificationMessageProvider());
                //@ 消息模板展示
                // RongContext.getInstance().registerConversationTemplate(new NewDiscussionConversationProvider());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static TeamsHitApplication getInstance() {
        return createApplication.TEAMS_HIT_APPLICATION;
    }

    public static Context getContext() {
        return mContext;
    }

    static class createApplication {
        final static TeamsHitApplication TEAMS_HIT_APPLICATION = new TeamsHitApplication();
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


}
