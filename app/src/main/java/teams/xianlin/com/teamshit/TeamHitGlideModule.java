package teams.xianlin.com.teamshit;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

import teams.xianlin.com.teamshit.BaseInfor.Constant;

/**
 * Created by suneee on 2016/6/6.
 */
public class TeamHitGlideModule implements GlideModule {

    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Constant.FILE_IMG_CACHE;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(
                new InternalCacheDiskCacheFactory(context, DEFAULT_SAVE_IMAGE_PATH, 50 * 1024 * 1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
