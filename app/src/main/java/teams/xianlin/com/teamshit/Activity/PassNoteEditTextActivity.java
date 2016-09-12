package teams.xianlin.com.teamshit.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import io.rong.eventbus.EventBus;
import teams.xianlin.com.teamshit.EventBus.ImgPathEvent;
import teams.xianlin.com.teamshit.Interface.SaveBitMapCallBack;
import teams.xianlin.com.teamshit.Interface.SelectorAlignCallBack;
import teams.xianlin.com.teamshit.Interface.SelectorTextSizeCallBack;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.ImageUtils;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.TextUtils.SpanableUtils;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.widget.Popwindow.SelectorTxtGravityPopWindow;
import teams.xianlin.com.teamshit.widget.Popwindow.SelectorTxtSizePopWindow;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/7/20.
 */
public class PassNoteEditTextActivity extends BaseActivity implements SelectorTextSizeCallBack, SelectorAlignCallBack, SaveBitMapCallBack {
    private TitleNavi edit_text_navi_top;//头部导航

    private EditText edit_text;//编辑文字

    private ImageView img_bold;//文字加粗

    private ImageView img_size;//设置文字大小

    private ImageView img_print;//打印

    private ImageView img_align;//文字对齐

    private ImageView img_rotation;//文字旋转

    private ImageView img_test;

    private String mText;

    private SelectorTxtSizePopWindow selectorTxtSizePopWindow;//选择文字的大小

    private SelectorTxtGravityPopWindow selectorTxtGravityPopWindow;//获取文字的对齐方式

    private int textSize;

    private int mAlign = Gravity.LEFT;
    private Bitmap mBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_note_edit_text);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        edit_text_navi_top = (TitleNavi) findViewById(R.id.edit_text_navi_top);
        edit_text = (EditText) findViewById(R.id.edit_text);
        img_bold = (ImageView) findViewById(R.id.img_bold);
        img_size = (ImageView) findViewById(R.id.img_size);
        img_print = (ImageView) findViewById(R.id.img_print);
        img_align = (ImageView) findViewById(R.id.img_align);
        img_rotation = (ImageView) findViewById(R.id.img_rotation);
        img_test = (ImageView) findViewById(R.id.img_test);
        img_bold.setOnClickListener(this);
        img_size.setOnClickListener(this);
        img_print.setOnClickListener(this);
        img_align.setOnClickListener(this);
        img_rotation.setOnClickListener(this);
        edit_text_navi_top.setClickCallBack(this);
        selectorTxtSizePopWindow = new SelectorTxtSizePopWindow(PassNoteEditTextActivity.this, this);
        selectorTxtGravityPopWindow = new SelectorTxtGravityPopWindow(PassNoteEditTextActivity.this, this);
    }

    @Override
    protected void initialData() {
        edit_text_navi_top.setBackTitle("插入文字");
        edit_text_navi_top.setFinish("完成");
    }

    @Override
    protected void inflateView() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img_bold://加粗
                setEditTextStyle();
                TextPaint tp = edit_text.getPaint();
                tp.setFakeBoldText(true);
                break;
            case R.id.img_size:
                setEditTextStyle();
                selectorTxtSizePopWindow.showPopupWindow(img_size);
                break;
            case R.id.img_print:

                break;
            case R.id.img_align:
                setEditTextStyle();
                selectorTxtGravityPopWindow.showPopupWindow(img_align);
                break;
            case R.id.img_rotation:
                edit_text.setGravity(Gravity.CENTER);
                edit_text.destroyDrawingCache();
                edit_text.setDrawingCacheEnabled(true);
                edit_text.buildDrawingCache();
                mBitmap = edit_text.getDrawingCache();
                mBitmap = ImageUtils.roratalImage(mBitmap, 90);
                img_test.setImageBitmap(mBitmap);
                edit_text.setVisibility(View.GONE);
                img_test.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onRightClick() {
        super.onRightClick();
        edit_text.destroyDrawingCache();
        edit_text.setDrawingCacheEnabled(true);
        edit_text.buildDrawingCache();
        mBitmap = edit_text.getDrawingCache();
        ImageUtils.getInstance().saveBitmapToPath(mBitmap, this, PassNoteEditTextActivity.this, 3);
    }

    @Override
    public void SelectorOnSuccess(int size) {
        textSize = size;
        edit_text.setTextSize(textSize);
    }
    @Override
    public void SelectorAlignOnSuccess(int align) {
        mAlign = align;
        edit_text.setGravity(mAlign);
    }

    public void setEditTextStyle() {
        edit_text.setVisibility(View.VISIBLE);
        img_test.setVisibility(View.GONE);
        edit_text.setGravity(mAlign);
    }

    @Override
    public void SaveBitMapOnSuccess(int saveCode, String result) {
        EventBus.getDefault().post(new ImgPathEvent(result));
        finish();
    }
}
