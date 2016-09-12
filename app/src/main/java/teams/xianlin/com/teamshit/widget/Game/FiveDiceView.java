package teams.xianlin.com.teamshit.widget.Game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import teams.xianlin.com.teamshit.Bean.DiceBean;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.DensityUtil;
import teams.xianlin.com.teamshit.widget.FriendCircle.ColorFilterImageView;

/**
 * Created by Administrator on 2016/9/10.
 */
public class FiveDiceView extends LinearLayout {
    private Context mContext;

    public static int MAX_WIDTH = 0;

    /**
     * 长度 单位为Pixel
     **/
    private int pxOneMaxWandH;  // 骰子最大宽度
    private int pxMoreWandH = 0;// 多张图的宽高
    private int pxImagePadding = DensityUtil.dip2px(getContext(), 3);// 图片间的间距

    private int MAX_PER_ROW_COUNT = 5;// 每行显示最大数

    private LinearLayout.LayoutParams morePara;//多张个骰子的样式

    private List<DiceBean> mDatas;

    private int columnCount;//骰子个数


    public FiveDiceView(Context context) {
        super(context);
        this.mContext = context;
    }

    public FiveDiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MAX_WIDTH == 0) {
            int width = measureWidth(widthMeasureSpec);
            if (width > 0) {
                MAX_WIDTH = width;
                if (mDatas != null && mDatas.size() > 0) {
                    setList(mDatas);
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            // result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
            // + getPaddingRight();
            if (specMode == View.MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    public void setList(List<DiceBean> lists) throws IllegalArgumentException {
        if (lists == null) {
            throw new IllegalArgumentException("imageList is null...");
        }
        MAX_PER_ROW_COUNT = lists.size();
        mDatas = lists;
        if (MAX_WIDTH > 0) {
            pxMoreWandH = (MAX_WIDTH - pxImagePadding * (MAX_PER_ROW_COUNT - 1)) / MAX_PER_ROW_COUNT; //解决右侧图片和内容对不齐问题
            pxOneMaxWandH = MAX_WIDTH * (MAX_PER_ROW_COUNT - 1) / MAX_PER_ROW_COUNT;
            initImageLayoutParams();
        }
        initView();
    }

    private void initImageLayoutParams() {
        morePara = new LinearLayout.LayoutParams(pxMoreWandH, pxMoreWandH);
        morePara.setMargins(pxImagePadding, 0, 0, 0);
    }

    // 根据imageView的数量初始化不同的View布局,还要为每一个View作点击效果
    private void initView() {
        this.setOrientation(VERTICAL);
        this.removeAllViews();
        if (MAX_WIDTH == 0) {
            //为了触发onMeasure()来测量MultiImageView的最大宽度，MultiImageView的宽设置为match_parent
            addView(new View(getContext()));
            return;
        }
        if (mDatas == null || mDatas.size() == 0) {
            return;
        }
        LinearLayout rowLayout = new LinearLayout(getContext());
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        columnCount = MAX_PER_ROW_COUNT;//设置多少个骰子
        addView(rowLayout);
        for (int position = 0; position < columnCount; position++) {
            rowLayout.addView(createImageView(position, true));
        }
    }


    private ImageView createImageView(int position, final boolean isMultiImage) {
        int resourceId = R.drawable.open_one;
        if (mDatas == null && mDatas.get(position) == null) {
            return null;
        }
        DiceBean diceBean = mDatas.get(position);
        ImageView imageView = new ColorFilterImageView(getContext());
        imageView.setClickable(false);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(morePara);
        int diceCount = diceBean.getDiceCount();//点数大小
        if (diceCount == 1) {
            resourceId = R.drawable.open_one;
        } else if (diceCount == 2) {
            resourceId = R.drawable.open_two;
        } else if (diceCount == 3) {
            resourceId = R.drawable.open_three;
        } else if (diceCount == 4) {
            resourceId = R.drawable.open_four;
        } else if (diceCount == 5) {
            resourceId = R.drawable.open_five;
        } else if (diceCount == 6) {
            resourceId = R.drawable.open_sixten;
        }
        Glide.with(getContext()).load(resourceId).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        return imageView;
    }
}
