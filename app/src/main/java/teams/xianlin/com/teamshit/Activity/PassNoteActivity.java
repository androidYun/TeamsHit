package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import teams.xianlin.com.teamshit.Adapter.DragListAdapter;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.EventBus.ImgPathEvent;
import teams.xianlin.com.teamshit.Interface.CreateQrCodeCallBack;
import teams.xianlin.com.teamshit.Interface.SelectPhotoCallBack;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.widget.DialogUtils.SelectPhotoDialog;
import teams.xianlin.com.teamshit.widget.Popwindow.CreateQrCodePopWindow;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/7/20.
 */
public class PassNoteActivity extends BaseActivity implements SelectPhotoCallBack, CreateQrCodeCallBack, AdapterView.OnItemClickListener {
    private RelativeLayout parent;

    private TitleNavi pass_note_navi_top;

    private ListView list_pass_note;//图形列表

    private ImageView img_text;//文字

    private ImageView img_photo;//图片

    private ImageView img_expresstion;//表情

    private ImageView img_qrcode;//打印

    private ImageView img_brush;//画笔

    private ImageView img_time;//时间柱

    private DragListAdapter dragListAdapter;

    private SelectPhotoDialog selectPhotoDialog;//选择图片对话框

    private CreateQrCodePopWindow createQrCodePopWindow;//生成二维码

    private List<String> pathList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_note);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        parent = (RelativeLayout) findViewById(R.id.parent);
        pass_note_navi_top = (TitleNavi) findViewById(R.id.pass_note_navi_top);
        list_pass_note = (ListView) findViewById(R.id.list_pass_note);
        img_text = (ImageView) findViewById(R.id.img_text);
        img_photo = (ImageView) findViewById(R.id.img_photo);
        img_expresstion = (ImageView) findViewById(R.id.img_expresstion);
        img_qrcode = (ImageView) findViewById(R.id.img_qrcode);
        img_brush = (ImageView) findViewById(R.id.img_brush);
        img_time = (ImageView) findViewById(R.id.img_time);
        img_text.setOnClickListener(this);
        img_photo.setOnClickListener(this);
        img_expresstion.setOnClickListener(this);
        img_qrcode.setOnClickListener(this);
        img_brush.setOnClickListener(this);
        img_time.setOnClickListener(this);
        pass_note_navi_top.setClickCallBack(this);
        selectPhotoDialog = new SelectPhotoDialog(mActivity,this);
        selectPhotoDialog.setonSelectCallBack(this);
        createQrCodePopWindow = new CreateQrCodePopWindow(PassNoteActivity.this, this);
    }

    @Override
    protected void initialData() {
        pass_note_navi_top.setBackTitle("素材");
        pass_note_navi_top.setFinish("打印预览");
        pathList = new ArrayList<>();
        dragListAdapter = new DragListAdapter(pathList, PassNoteActivity.this);
        list_pass_note.setAdapter(dragListAdapter);
        list_pass_note.setOnItemClickListener(this);
    }

    @Override
    protected void inflateView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_text:
                Intent passNoteTextIntent = new Intent(PassNoteActivity.this, PassNoteEditTextActivity.class);
                startActivity(passNoteTextIntent);
                break;
            case R.id.img_photo:
                selectPhotoDialog.show();
                break;
            case R.id.img_expresstion:


                break;
            case R.id.img_qrcode:
                createQrCodePopWindow.showPopupWindow(parent);

                break;
            case R.id.img_brush:
                Intent graffitionIntent = new Intent(PassNoteActivity.this, GraffitiBoardActivity.class);
                startActivity(graffitionIntent);
                break;
            case R.id.img_time:


                break;
        }
    }

    public void onEventMainThread(ImgPathEvent event) {
        pathList.add(event.getImgPath());
        dragListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRightClick() {
        super.onRightClick();
    }

    @Override
    public void ClickCamera() {
        GalleryFinal.openCamera(Constant.Selector_Photo_Code, TeamHitContext.getFunctionConfig(), onHanlderResultCallback);
        selectPhotoDialog.cancel();
    }

    @Override
    public void ClickPhoto() {
        GalleryFinal.openGallerySingle(Constant.Selector_Photo_Code, TeamHitContext.getFunctionConfig(), onHanlderResultCallback);
        selectPhotoDialog.cancel();
    }

    @Override
    public void ClickCancle() {
        selectPhotoDialog.cancel();
    }

    @Override
    public void onCreateQrCodeSuccess(String qrcodePath) {
        pathList.add(qrcodePath);
        dragListAdapter.notifyDataSetChanged();
        LogUtil.d(qrcodePath);
        // Glide.with(this).load("file://" + qrcodePath).into(img_brush);
    }

    GalleryFinal.OnHanlderResultCallback onHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (reqeustCode == Constant.Selector_Photo_Code) {
                if (resultList == null || resultList.get(0) == null) {
                    ToastUtil.show(PassNoteActivity.this, "选择图片错误，请重新选择");
                    return;
                }
                Intent handleImaIntent = new Intent(PassNoteActivity.this, PassNotePhotoActivity.class);
                handleImaIntent.putExtra(Constant.Img_Path, resultList.get(0).getPhotoPath());
                startActivity(handleImaIntent);
            }

        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            ToastUtil.show(PassNoteActivity.this, errorMsg + "");
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        switch (view.getId()) {
            case R.id.img_cancle:
                pathList.remove(position);
                dragListAdapter.notifyDataSetChanged();
                break;
        }

    }
}
