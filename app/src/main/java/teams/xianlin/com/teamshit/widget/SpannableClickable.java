package teams.xianlin.com.teamshit.widget;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.TeamsHitApplication;

/**
 * Created by Administrator on 2016/8/26.
 */
public abstract class SpannableClickable extends ClickableSpan implements View.OnClickListener {
    private int DEFAULT_COLOR_ID = R.color.color_8290AF;
    /**
     * text颜色
     */
    private int textColor;

    public SpannableClickable() {
        this.textColor = TeamsHitApplication.getInstance().getResources().getColor(DEFAULT_COLOR_ID);
    }

    public SpannableClickable(int textColor) {
        this.textColor = textColor;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setColor(textColor);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }


}
