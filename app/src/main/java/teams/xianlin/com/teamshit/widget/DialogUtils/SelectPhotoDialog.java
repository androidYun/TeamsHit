package teams.xianlin.com.teamshit.widget.DialogUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import teams.xianlin.com.teamshit.Activity.CompleteUserInforActivity;
import teams.xianlin.com.teamshit.Activity.MainActivity;
import teams.xianlin.com.teamshit.Interface.SelectPhotoCallBack;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.PhotoPickManger;
import teams.xianlin.com.teamshit.Utils.LogUtil;

/**
 * Created by Administrator on 2016/7/11.
 */
public class SelectPhotoDialog extends Dialog implements View.OnClickListener {
    private TextView txt_cancle;

    private TextView txt_camera;

    private TextView txt_photo_grally;

    private Context mConetxt;

    private PhotoPickManger photoPickManger;

    public SelectPhotoCallBack selectPhotoCallBack;

    private View view;

    public SelectPhotoDialog(Activity context, SelectPhotoCallBack selectPhotoCallBack) {
        super(context, R.style.select_photo_dialog_style);
        this.mConetxt = context;
        this.selectPhotoCallBack = selectPhotoCallBack;
        setSelectdialog(context);
    }


    private void setSelectdialog(Context context) {
        photoPickManger = PhotoPickManger.getInStance();
        view = LayoutInflater.from(context).inflate(R.layout.dialog_select_layout, null);
        txt_cancle = (TextView) view.findViewById(R.id.txt_cancle);
        txt_camera = (TextView) view.findViewById(R.id.txt_camera);
        txt_photo_grally = (TextView) view.findViewById(R.id.txt_photo_grally);
        txt_cancle.setOnClickListener(this);
        txt_camera.setOnClickListener(this);
        txt_photo_grally.setOnClickListener(this);
        this.getWindow().setWindowAnimations(R.style.dialog_animation);  //添加动画
        super.setContentView(view);
    }

    public void setonSelectCallBack(SelectPhotoCallBack selectPhotoCallBack) {
        this.selectPhotoCallBack = selectPhotoCallBack;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_cancle:
                selectPhotoCallBack.ClickCancle();
                break;
            case R.id.txt_camera:
                selectPhotoCallBack.ClickCamera();
                break;
            case R.id.txt_photo_grally:
                selectPhotoCallBack.ClickPhoto();
                break;

        }
    }
}
