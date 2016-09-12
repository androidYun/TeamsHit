package teams.xianlin.com.teamshit.BaseInfor;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/7/26.
 * 穿件文件夹
 */
public class FileUtils {
    public static void initial() {
        initFiles();
    }

    public static void initFiles() {
        File file = new File(Environment.getExternalStorageDirectory(), "teamshit/data");
        if (!file.exists())
            file.mkdirs();
        file = new File(Environment.getExternalStorageDirectory(), "teamshit/images/send");
        if (!file.exists())
            file.mkdirs();
        file = new File(Environment.getExternalStorageDirectory(), "teamshit/images/shot");
        if (!file.exists())
            file.mkdirs();
        file = new File(Environment.getExternalStorageDirectory(), "teamshit/images/cache");
        if (!file.exists())
            file.mkdirs();
        file = new File(Environment.getExternalStorageDirectory(), "teamshit/download");
        if (!file.exists())
            file.mkdirs();
        file = new File(Environment.getExternalStorageDirectory(), "teamshit/voice/send");
        if (!file.exists())
            file.mkdirs();
        file = new File(Environment.getExternalStorageDirectory(), "teamshit/voice/receive");
        if (!file.exists())
            file.mkdirs();
    }
}
