package teams.xianlin.com.teamshit.Utils.ImageUtisl;

import android.graphics.Bitmap;
import android.os.Handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Administrator on 2016/7/11.
 */
public class PhotoPickManger {
    public static PhotoPickManger getInStance() {
        return createPhotoPickManger.PHOTO_PICK_MANGER;
    }

    static class createPhotoPickManger {
        final static PhotoPickManger PHOTO_PICK_MANGER = new PhotoPickManger();
    }

    public PhotoPickManger() {
    }

    private Handler handler = new Handler();
    /**
     * 压缩图片
     */
    public int needProcessFileLength = (int) 0.1 * 1024 * 1024;

    public interface OnProcessedPhotos {
        void onProcessed(List<PhotoInfo> list);
    }

    /**
     * 对当前已选图片进行压缩
     */
    public void doProcessedPhotos(final List<PhotoInfo> files, final OnProcessedPhotos on) {

        if (files != null && !files.isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (Iterator<PhotoInfo> it = files.iterator(); it.hasNext(); ) {
                        try {
                            File file = new File(it.next().getPhotoPath());
                            if (file.length() > needProcessFileLength) {
                                final Bitmap bm = ImageUtils.getSmallBitmap(file.getAbsolutePath(), 360, 600);
                                try {
                                    FileOutputStream fos = new FileOutputStream(file);
                                    bm.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            on.onProcessed(files);
                        }
                    });


                }
            }).start();

        }
    }
}
