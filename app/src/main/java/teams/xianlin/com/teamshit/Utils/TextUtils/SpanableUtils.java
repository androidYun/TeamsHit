package teams.xianlin.com.teamshit.Utils.TextUtils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

/**
 * Created by Administrator on 2016/7/26.
 */
public class SpanableUtils {
    public static SpanableUtils getInstance() {
        return createSpanAble.SPANABLE_UTILS;
    }

    static class createSpanAble {
        final static SpanableUtils SPANABLE_UTILS = new SpanableUtils();
    }

    public void setSpanAbleStyle(SpannableString spannableString, float textSize, int startPosition, int endPosition) {
        spannableString.setSpan(new RelativeSizeSpan(textSize), startPosition, endPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 设置粗体文字
     *
     * @return
     */
    public void setBoldSpanStyle(SpannableString spannableString, int startPosition, int endPosition) {
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startPosition, endPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 设置颜色值
     *
     * @return
     */
    public SpannableString setColorSpanStyle(String str, int color, int startPosition, int endPosition) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(color), startPosition, endPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}