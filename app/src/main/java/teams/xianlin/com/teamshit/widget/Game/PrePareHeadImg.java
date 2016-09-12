package teams.xianlin.com.teamshit.widget.Game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.widget.FriendCircle.GlideCircleTransform;

/**
 * Created by Administrator on 2016/9/8.
 */
public class PrePareHeadImg extends RelativeLayout {

    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.img_selector)
    ImageView imgSelector;

    private Context mContext;

    public PrePareHeadImg(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_prepare_head, this, true);
        ButterKnife.bind(this, view);
    }

    public void load(String url) {
        imgSelector.setVisibility(View.VISIBLE);
        Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(mContext)).into(imgHead);
    }

    public void showDefaultHead(int drawable) {
        imgSelector.setVisibility(View.GONE);
        Glide.with(mContext).load(drawable).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(mContext)).into(imgHead);
    }
}
