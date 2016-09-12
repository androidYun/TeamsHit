package teams.xianlin.com.teamshit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.SysUtils;

/**
 * Created by Administrator on 2016/8/17.
 */
public class line_view extends View {

    private int mCanvasColor;//画布颜色

    private int mLineColor;//线条颜色

    private int mWidth;

    private int mHeight;

    private float mMarginLeft;

    private float mMarginRight;

    private float mScreenWidth;

    public line_view(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public line_view(Context context) {
        this(context, null);
    }

    public line_view(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.line_view, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.line_view_lineColor:
                    mLineColor = typedArray.getColor(attr, Color.WHITE);
                    break;
            }
        }
        typedArray.recycle();
        mScreenWidth = SysUtils.getInatcne().getWindowWidth(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY)//精确
        {
            mWidth = widthSize;
        } else {
            if (widthMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mWidth = 300;
            }
        }
        if (heightMode == MeasureSpec.EXACTLY)//精确
        {
            mHeight = heightSize;
        } else {
            if (heightMode == MeasureSpec.AT_MOST)// wrap_content
            {
                heightSize = 1;
            }
        }
        mMarginLeft = getPaddingLeft();
        mMarginRight = getPaddingRight();
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(mLineColor);
        paint.setStrokeWidth(mHeight);
        RectF rectF = new RectF(mMarginLeft, 0, mScreenWidth - mMarginLeft * 2, mHeight);
        canvas.drawRect(rectF, paint);
    }
}
