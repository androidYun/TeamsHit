package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import io.rong.eventbus.EventBus;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.EventBus.ImgPathEvent;
import teams.xianlin.com.teamshit.Interface.ClickCallback;
import teams.xianlin.com.teamshit.Interface.SelectorPaintWidthCallBack;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.widget.GraffitiBoard.GraffitiView;
import teams.xianlin.com.teamshit.widget.Popwindow.SelectorPaintWidthPopWindow;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/7/28.
 * 空白涂鸦
 */
public class GraffitiBoardActivity extends BaseActivity implements ClickCallback, SelectorPaintWidthCallBack {
    private GraffitiView img_graffiti;

    private TitleNavi title_navi;

    private ImageView img_head;

    private ImageView img_paint;

    private ImageView img_eraser;

    private ImageView img_print;

    private ImageView img_paint_width;

    private ImageView img_return;

    private String mImgPath;

    private SelectorPaintWidthPopWindow selectorPaintWidthPopWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graffiti_board);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        title_navi = (TitleNavi) findViewById(R.id.title_navi);
        img_graffiti = (GraffitiView) findViewById(R.id.img_graffiti);
        img_paint = (ImageView) findViewById(R.id.img_paint);
        img_eraser = (ImageView) findViewById(R.id.img_eraser);
        img_print = (ImageView) findViewById(R.id.img_print);
        img_paint_width = (ImageView) findViewById(R.id.img_paint_width);
        img_return = (ImageView) findViewById(R.id.img_return);
        img_head = (ImageView) findViewById(R.id.img_head);
        img_paint.setOnClickListener(this);
        img_eraser.setOnClickListener(this);
        img_print.setOnClickListener(this);
        img_paint_width.setOnClickListener(this);
        img_return.setOnClickListener(this);
        title_navi.setClickCallBack(this);
        selectorPaintWidthPopWindow = new SelectorPaintWidthPopWindow(GraffitiBoardActivity.this, this);
    }

    @Override
    protected void initialData() {

    }

    @Override
    protected void inflateView() {
        title_navi.setBackTitle("涂鸦板");
        title_navi.setFinishVisible();
        title_navi.setFinish("保存");
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img_paint:
                img_graffiti.setPainter();
                break;
            case R.id.img_eraser:
                img_graffiti.setEraser();
                break;
            case R.id.img_print:
                break;
            case R.id.img_paint_width:
                selectorPaintWidthPopWindow.showPopupWindow(img_paint_width);
                break;
            case R.id.img_return:
                img_graffiti.undo();
                break;

        }
    }

    @Override
    public void onRightClick() {
        super.onRightClick();
        loadDialog.show();
        String saveBitmap = img_graffiti.saveBitmap();
        EventBus.getDefault().post(new ImgPathEvent(saveBitmap));
        finish();
    }

    @Override
    public void SelectorPaintWidthOnSuccess(int position) {
        switch (position) {
            case 1:
                img_paint_width.setImageResource(R.drawable.ico_dot_01);
                break;
            case 2:
                img_paint_width.setImageResource(R.drawable.ico_dot_02);
                break;
            case 3:
                img_paint_width.setImageResource(R.drawable.ico_dot_03);
                break;
            case 4:
                img_paint_width.setImageResource(R.drawable.ico_dot_04);
                break;
            case 5:
                img_paint_width.setImageResource(R.drawable.ico_dot_05);
                break;
        }
        img_graffiti.setPaintWidth(position * 10 + 35);
    }


}
