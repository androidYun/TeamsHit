package teams.xianlin.com.teamshit.widget.Game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.widget.RoundImageView;

/**
 * Created by Administrator on 2016/9/10.
 */
public class RollDiceView extends LinearLayout implements View.OnClickListener {

    ImageView imgDiceCup;

    FiveDiceView fiveDiceView;

    TextView txtAgain;

    TextView txtConfirm;

    TextView txtTitle;

    private Context mContext;

    private RoundImageView imgRoll;//测试

    public RollDiceView(Context context) {
        this(context, null);
    }

    public RollDiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initialView();
        initialData();
        inflateView();
    }

    private void initialView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_roll_dice_boast_layout, this, true);
        imgDiceCup = (ImageView) view.findViewById(R.id.img_dice_cup);
        fiveDiceView = (FiveDiceView) view.findViewById(R.id.five_dice_view);
        txtAgain = (TextView) view.findViewById(R.id.txt_again);
        txtConfirm = (TextView) view.findViewById(R.id.txt_confirm);
        txtTitle = (TextView) view.findViewById(R.id.txt_title);
        imgRoll = (RoundImageView) view.findViewById(R.id.img_roll);
        imgDiceCup.setOnClickListener(this);
        txtAgain.setOnClickListener(this);
        txtConfirm.setOnClickListener(this);
        imgRoll.setOnClickListener(this);
    }

    private void initialData() {
    }

    private void inflateView() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_confirm:
                imgDiceCup.clearAnimation();
                break;
            case R.id.txt_again:
                imgRoll.setNormal();
                break;
            case R.id.img_roll:
                imgRoll.setSelector();
                break;
            case R.id.img_dice_cup:
                TranslateAnimation animation = new TranslateAnimation(150, -150, 0, 0);
                animation.setDuration(400);
                animation.setRepeatCount(Animation.INFINITE);
                animation.setRepeatMode(Animation.REVERSE);
                imgDiceCup.startAnimation(animation);
                break;
        }
    }
}
