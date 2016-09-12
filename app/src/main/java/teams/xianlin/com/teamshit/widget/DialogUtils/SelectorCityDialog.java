package teams.xianlin.com.teamshit.widget.DialogUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teams.xianlin.com.teamshit.Interface.SelectorCityCallBack;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.widget.WheelCity.View.OnWheelChangedListener;
import teams.xianlin.com.teamshit.widget.WheelCity.View.WheelView;
import teams.xianlin.com.teamshit.widget.WheelCity.adapter.ArrayWheelAdapter;

/**
 * Created by Administrator on 2016/8/19.
 */
public class SelectorCityDialog extends SelectorCityParentDialog implements View.OnClickListener, OnWheelChangedListener {

    @Bind(R.id.txt_cancle)
    TextView txtCancle;
    @Bind(R.id.txt_confirm)
    TextView txtConfirm;
    @Bind(R.id.id_province)
    WheelView idProvince;
    @Bind(R.id.id_city)
    WheelView idCity;
    @Bind(R.id.id_district)
    WheelView idDistrict;
    @Bind(R.id.layout_wheel)
    LinearLayout layoutWheel;
    private Context mContext;

    private SelectorCityCallBack selectorCityCallBack;

    private View view;

    public SelectorCityDialog(Context mContext, SelectorCityCallBack selectorCityCallBack) {
        super(mContext, R.style.select_city_dialog_style);
        this.mContext = mContext;
        this.selectorCityCallBack = selectorCityCallBack;
        setSelectSexDialog(mContext);
    }

    private void setSelectSexDialog(Context mContext) {
        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_selector_city, null, false);
        ButterKnife.bind(this, view);
        this.setContentView(view);
        // ���change�¼�
        idCity.addChangingListener(this);
        // ���change�¼�
        idDistrict.addChangingListener(this);
        // ���change�¼�
        idProvince.addChangingListener(this);
        setUpData();
    }

    @OnClick({R.id.txt_confirm, R.id.txt_cancle})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_confirm:
                if (StringUtils.isBlank(mCurrentProviceName)) {
                    ToastUtil.show(mContext, "选择的省份不能为空");
                    return;
                }
                if (StringUtils.isBlank(mCurrentCityName)) {
                    ToastUtil.show(mContext, "选择的城市不能为空");
                    return;
                }
                selectorCityCallBack.onSelelctCitySuccess(mCurrentProviceName, mCurrentCityName, mCurrentDistrictName);
                break;
            case R.id.txt_cancle:
                break;
            default:
                break;
        }
        this.dismiss();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == idProvince) {
            updateCities();
        } else if (wheel == idCity) {
            updateAreas();
        } else if (wheel == idDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    private void setUpData() {
        initialData();
        idProvince.setViewAdapter(new ArrayWheelAdapter<>(mContext, mProvinceDatas));
        // 设置可见条目数量
        idProvince.setVisibleItems(7);
        idCity.setVisibleItems(7);
        idDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = idCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        idDistrict.setViewAdapter(new ArrayWheelAdapter<String>(mContext, areas));
        idDistrict.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = idProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        idCity.setViewAdapter(new ArrayWheelAdapter<String>(mContext, cities));
        idCity.setCurrentItem(0);
        updateAreas();
    }
}
