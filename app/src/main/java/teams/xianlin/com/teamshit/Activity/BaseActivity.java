package teams.xianlin.com.teamshit.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import io.rong.eventbus.EventBus;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.Interface.ClickCallback;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.VaryView.VaryViewHelper;
import teams.xianlin.com.teamshit.widget.Progressdiaolg;

/**
 * Created by Administrator on 2016/7/7.
 */
public abstract class BaseActivity extends FragmentActivity implements ClickCallback, View.OnClickListener {

    public VaryViewHelper mVaryViewHelper;//加载错误界面的工具

    public View empty_View;//数据为空的界面

    public View error_View;//加载错误的界面

    public View load_View;//正在加载界面

    public Progressdiaolg loadDialog;//加载框

    public Activity mActivity;
    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        EventBus.getDefault().register(this);
        loadDialog = new Progressdiaolg(this, "正在加载...");
        loadDialog.setCanceledOnTouchOutside(false);
    }


    /*初始化布局
    * */
    public void initialView() {
        // TODO Auto-generated method stub
        load_View = LayoutInflater.from(this).inflate(
                R.layout.layout_loadingview, null);
        error_View = LayoutInflater.from(this).inflate(
                R.layout.layout_errorview, null);
        empty_View = LayoutInflater.from(this).inflate(
                R.layout.layout_emptyview, null);
        mVaryViewHelper = new VaryViewHelper.Builder()
                .setDataView(findViewById(R.id.layout_content))// 放数据的父布局，逻辑处理在该Activity中处理
                .setLoadingView(
                        load_View)// 加载页，无实际逻辑处理
                .setEmptyView(
                        empty_View)// 空页面，无实际逻辑处理
                .setErrorView(
                        error_View)// 错误页面// //
                // 错误页点击刷新实现
                .build();

    }


    @Override
    public void onRightClick() {

    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onRightImgClick() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }


    protected abstract void initialData();

    protected abstract void inflateView();

    public void onEventMainThread(EventBus event) {

    }

    /**
     * 获取个人信息
     *
     * @return
     */
    public UserBean getUserBean() {
        userBean = TeamHitContext.getInstance().getUserBean();
        return userBean;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
