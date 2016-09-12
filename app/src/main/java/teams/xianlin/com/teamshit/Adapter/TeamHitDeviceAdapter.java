package teams.xianlin.com.teamshit.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.Bean.DeviceDetailBean;
import teams.xianlin.com.teamshit.R;

/**
 * Created by Administrator on 2016/8/8.
 */
public class TeamHitDeviceAdapter extends BaseRecyclerAdapter<DeviceDetailBean> {
    public TeamHitDeviceAdapter(Context context, List<DeviceDetailBean> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder viewHolder, final DeviceDetailBean deviceDetailBean, final int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        if (deviceDetailBean.getState() == 0) {//设置设备名称  和设备状态
            holder.txt_name.setText("" + deviceDetailBean.getDeviceName() + "(在线)");
        } else if (deviceDetailBean.getState() == 1) {
            holder.txt_name.setText("" + deviceDetailBean.getDeviceName() + "(缺纸)");
        } else if (deviceDetailBean.getState() == 2) {
            holder.txt_name.setText("" + deviceDetailBean.getDeviceName() + "(温度保护报警)");
        } else if (deviceDetailBean.getState() == 3) {
            holder.txt_name.setText("" + deviceDetailBean.getDeviceName() + "(忙碌)");
        } else if (deviceDetailBean.getState() == 4) {
            holder.txt_name.setText("" + deviceDetailBean.getDeviceName() + "(离线)");
        }
        Drawable buzzer_drawable = null;
        if (deviceDetailBean.getBuzzer() == 0) {//设置蜂鸣器 的开关
            buzzer_drawable = getCompoundDrawables(R.id.txt_buzzer_switch, 0);
        } else if (deviceDetailBean.getBuzzer() == 1) {
            buzzer_drawable = getCompoundDrawables(R.id.txt_buzzer_switch, 1);
        }
        holder.txt_buzzer_switch.setCompoundDrawables(null, buzzer_drawable, null, null);
        Drawable light_drawable = null;
        if (deviceDetailBean.getIndicator() == 0) {//设置指示灯图片
            light_drawable = getCompoundDrawables(R.id.txt_light_switch, 0);
        } else if (deviceDetailBean.getIndicator() == 1) {
            light_drawable = getCompoundDrawables(R.id.txt_light_switch, 1);
        }
        holder.txt_light_switch.setCompoundDrawables(null, light_drawable, null, null);
        holder.txt_buzzer_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(holder.txt_buzzer_switch, position, deviceDetailBean);
            }
        });
        holder.txt_light_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(holder.txt_light_switch, position, deviceDetailBean);
            }
        });
        holder.txt_handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(holder.txt_handler, position, deviceDetailBean);
            }
        });
        holder.txt_modify_device_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(holder.txt_modify_device_name, position, deviceDetailBean);
            }
        });
        holder.txt_config_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view, position, deviceDetailBean);
            }
        });
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View mView = inflateView(R.layout.list_item_team_hit, parent);
        ViewHolder viewHolder = new ViewHolder(mView);
        return viewHolder;
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.txt_name)
        public TextView txt_name;
        @Bind(R.id.txt_handler)
        public TextView txt_handler;
        @Bind(R.id.txt_config_wifi)
        public TextView txt_config_wifi;
        @Bind(R.id.txt_modify_device_name)
        public TextView txt_modify_device_name;
        @Bind(R.id.txt_buzzer_switch)
        public TextView txt_buzzer_switch;
        @Bind(R.id.txt_light_switch)
        public TextView txt_light_switch;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public Drawable getCompoundDrawables(int id, int switchState) {
        Drawable buzzer_dra = null;
        Drawable light_dra = null;
        switch (id) {
            case R.id.txt_buzzer_switch:
                if (switchState == 0) {
                    buzzer_dra = mContext.getResources().getDrawable(R.drawable.icon_buzzer_close);
                } else if (switchState == 1) {
                    buzzer_dra = mContext.getResources().getDrawable(R.drawable.icon_buzzer_open);
                }
                buzzer_dra.setBounds(0, 0, buzzer_dra.getMinimumWidth(), buzzer_dra.getMinimumHeight());
                return buzzer_dra;

            case R.id.txt_light_switch:
                if (switchState == 0) {
                    light_dra = mContext.getResources().getDrawable(R.drawable.icon_light_close);
                } else if (switchState == 1) {
                    light_dra = mContext.getResources().getDrawable(R.drawable.icon_light_open);
                }
                light_dra.setBounds(0, 0, light_dra.getMinimumWidth(), light_dra.getMinimumHeight());
                return light_dra;
        }

        return null;
    }
}
