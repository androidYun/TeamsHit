package teams.xianlin.com.teamshit.Utils.ImageUtisl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.concurrent.ExecutionException;

import teams.xianlin.com.teamshit.R;

/**
 * Created by Administrator on 2016/7/16.
 */
public class ImageLoadUtils {
    public static ImageLoadUtils getInstance() {
        return createImageLoadUtils.imageLoadUtils;
    }

    public static class createImageLoadUtils {
        private final static ImageLoadUtils imageLoadUtils = new ImageLoadUtils();
    }

    public void with(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context).load(imageUrl).error(R.drawable.de_default_portrait).sizeMultiplier(1).skipMemoryCache(false).into(imageView);
    }

    public void with(Context context, String imageUrl, int errorRes, ImageView imageView) {
        Glide.with(context).load(imageUrl).error(errorRes).placeholder(errorRes).sizeMultiplier(1).skipMemoryCache(false).into(imageView);
    }

    /**
     * 缓存图片显示自定义图片 二位是圆形图片
     *
     * @param context
     * @param imageUrl
     * @param errorRes
     * @param imageView
     */
    public void cachaewith(Context context, String imageUrl, int errorRes, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl).error(errorRes).placeholder(errorRes)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true).centerCrop()
                .into(imageView);
    }

    /**
     * 根据路径获取Bitmap
     *
     * @param context
     * @param imgPath
     * @return
     */
    public Bitmap getBitMap(Context context, String imgPath) {
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context)
                    .load("file://" + imgPath)
                    .asBitmap() //必须
                    .centerCrop()
                    .into(500, 500)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
