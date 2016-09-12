package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import io.rong.eventbus.EventBus;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.EventBus.ImgPathEvent;
import teams.xianlin.com.teamshit.Interface.SaveBitMapCallBack;
import teams.xianlin.com.teamshit.Interface.onTransBimapCallBack;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.ImageUtils;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.TransBitmapUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/7/20.
 */
public class PassNotePhotoActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, onTransBimapCallBack, SaveBitMapCallBack {
    private ImageView img_handler_img;

    private RadioGroup rgroup_img;

    private HorizontalScrollView recycle_hander;

    private String mImgPath;//获取图片路径

    private Bitmap imgBitmap;//这个是原图，比如进行素描之类的

    private Bitmap handleBitmap;//这个是做Bitmap

    private Bitmap bitmap;

    private ImageView img_effect;//效果

    private ImageView img_crop;//剪切

    private ImageView img_print;//打印

    private ImageView img_scrawal;//涂写

    private ImageView img_roratal;//旋转

    private int roratalAngle = 0;//1 是旋转 90  2 是旋转 180  3是旋转 270  4 是旋转 360

    private TitleNavi handler_img_navi_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_note_img_handle);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        handler_img_navi_top = (TitleNavi) findViewById(R.id.handler_img_navi_top);
        recycle_hander = (HorizontalScrollView) findViewById(R.id.recycle_hander);
        img_handler_img = (ImageView) findViewById(R.id.img_handler_img);
        rgroup_img = (RadioGroup) findViewById(R.id.rgroup_img);
        img_effect = (ImageView) findViewById(R.id.img_effect);
        img_crop = (ImageView) findViewById(R.id.img_crop);
        img_print = (ImageView) findViewById(R.id.img_print);
        img_scrawal = (ImageView) findViewById(R.id.img_scrawal);
        img_roratal = (ImageView) findViewById(R.id.img_roratal);
        img_effect.setOnClickListener(this);
        img_crop.setOnClickListener(this);
        img_print.setOnClickListener(this);
        img_scrawal.setOnClickListener(this);
        img_roratal.setOnClickListener(this);
        rgroup_img.setOnCheckedChangeListener(this);
        handler_img_navi_top.setClickCallBack(this);
    }

    @Override
    protected void initialData() {
        handler_img_navi_top.setBackTitle("图片处理");
        handler_img_navi_top.setFinish("插入");
        Intent intent = getIntent();
        mImgPath = intent.getStringExtra(Constant.Img_Path);
    }

    @Override
    protected void inflateView() {
        inflateBitmap();
    }

    public void inflateBitmap() {
        Glide.with(this).load("file://" + mImgPath).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                imgBitmap = resource;
                handleBitmap = resource;
                img_handler_img.setImageBitmap(imgBitmap);
            }
        });
    }

    @Override
    public void onRightClick() {
        super.onRightClick();
        ImageUtils.getInstance().saveBitmapToPath(handleBitmap, this, PassNotePhotoActivity.this, 2);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        switch (checkId) {
            case R.id.rbtn_default:
                TransBitmapUtils.getInstance().startTransForBitmap(1, imgBitmap, this);
                break;
            case R.id.rbtn_sumiao:
                TransBitmapUtils.getInstance().startTransForBitmap(2, imgBitmap, this);
                break;
            case R.id.rbtn_miaobian:
                TransBitmapUtils.getInstance().startTransForBitmap(1, imgBitmap, this);
                break;
            case R.id.rbtn_fanse:
                TransBitmapUtils.getInstance().startTransForBitmap(3, imgBitmap, this);
                break;
            case R.id.rbtn_penmo:
                TransBitmapUtils.getInstance().startTransForBitmap(1, imgBitmap, this);
                break;
        }
        loadDialog.show();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img_effect:
                recycle_hander.setVisibility(View.VISIBLE);
                break;
            case R.id.img_crop:
                GalleryFinal.openCrop(Constant.Crop_Photo_Code, mImgPath, onHanlderResultCallback);
                break;
            case R.id.img_print:

                break;
            case R.id.img_scrawal:
                loadDialog.show();
                ImageUtils.getInstance().saveBitmapToPath(handleBitmap, this, PassNotePhotoActivity.this, 3);
                break;
            case R.id.img_roratal:
                handleBitmap = ImageUtils.roratalImage(handleBitmap, 90);
                img_handler_img.setImageBitmap(handleBitmap);
                break;
        }

    }

    public void onEventMainThread(ImgPathEvent event) {
        mImgPath = event.getImgPath();
        inflateBitmap();
    }

    @Override
    public void onTransBimapOnSuccess(Bitmap bitmap) {
        loadDialog.cancel();
        handleBitmap = bitmap;
        img_handler_img.setImageBitmap(bitmap);
    }

    @Override
    public void SaveBitMapOnSuccess(int saveCode, String result) {
        loadDialog.cancel();
        if (saveCode == 2) {//保存当前图片
            EventBus.getDefault().post(new ImgPathEvent(result));
            finish();
        } else if (saveCode == 3) {//跳转到涂鸦板
            Intent graffitiIntent = new Intent(PassNotePhotoActivity.this, GraffitiBoardImgActivity.class);
            graffitiIntent.putExtra(Constant.Img_Path, result);
            startActivity(graffitiIntent);
        }
    }

    GalleryFinal.OnHanlderResultCallback onHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            ToastUtil.show(PassNotePhotoActivity.this, "裁剪成功");
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            ToastUtil.show(PassNotePhotoActivity.this, "" + errorMsg);
        }
    };
}
