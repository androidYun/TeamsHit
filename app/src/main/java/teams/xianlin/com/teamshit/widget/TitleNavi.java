package teams.xianlin.com.teamshit.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import teams.xianlin.com.teamshit.Interface.ClickCallback;
import teams.xianlin.com.teamshit.R;

/**
 * 自定义标题栏
 * Created by Administrator on 2016/7/7.
 */
public class TitleNavi extends RelativeLayout implements View.OnClickListener {
    View inflate;
    private ImageView img_back;

    private TextView txt_back;

    private TextView txt_title;

    private TextView txt_finish;

    private ImageView img_finish;

    private ClickCallback clickCallback;

    public TitleNavi(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate = LayoutInflater.from(context).inflate(R.layout.title_layout, this, true);
        img_back = (ImageView) inflate.findViewById(R.id.img_back);
        txt_back = (TextView) inflate.findViewById(R.id.txt_back);
        txt_title = (TextView) inflate.findViewById(R.id.txt_title);
        txt_finish = (TextView) inflate.findViewById(R.id.txt_finish);
        img_finish = (ImageView) inflate.findViewById(R.id.img_finish);
        img_back.setOnClickListener(this);
        txt_finish.setOnClickListener(this);
        img_finish.setOnClickListener(this);
    }

    public TitleNavi(Context context) {
        this(context, null);

    }


    public void setTitle(String title) {
        setTiltleVisible();
        txt_title.setText(title + "");
    }

    public void setFinish(String finish) {
        setFinishVisible();
        txt_finish.setText(finish + "");
    }

    public void setFinishImg(int finishIcon) {
        setFinishImgVisible();
        img_finish.setImageResource(finishIcon);
    }

    public void setBackTitle(String backTitle) {
        txt_back.setText(backTitle + "");
    }

    @Override
    public void onClick(View view) {
        if (clickCallback == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.img_back:
                clickCallback.onBackClick();
                break;
            case R.id.txt_finish:
                clickCallback.onRightClick();
                break;
            case R.id.img_finish:
                clickCallback.onRightImgClick();
                break;
        }
    }

    public void setTiltleVisible() {
        txt_title.setVisibility(View.VISIBLE);
    }

    public void setFinishVisible() {
        txt_finish.setVisibility(View.VISIBLE);
    }

    public void setFinishImgVisible() {
        img_finish.setVisibility(View.VISIBLE);
    }

    public void setFinishGone() {
        txt_finish.setVisibility(View.GONE);
    }

    public void setBackTitleGone() {
        txt_back.setVisibility(View.GONE);
    }

    public void setClickCallBack(ClickCallback clickCallBack) {
        this.clickCallback = clickCallBack;
    }
}
