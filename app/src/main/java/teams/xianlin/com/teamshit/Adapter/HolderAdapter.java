package teams.xianlin.com.teamshit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import teams.xianlin.com.teamshit.Interface.OnItemClickListener;

/**
 * Created by Administrator on 2016/7/29.
 */
public abstract class HolderAdapter<T, H> extends AbstractAdapter<T> {
    public HolderAdapter(Context context, List<T> listData) {
        super(context, listData);
    }

    public OnItemClickListener<T> mOnItemClickListener;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H holder = null;
        T t = listData.get(position);
        if (convertView == null) {
            convertView = buildConvertView(layoutInflater, t, position);
            holder = buildHolder(convertView, t, position);
            convertView.setTag(holder);
        } else {
            holder = (H) convertView.getTag();
        }
        bindViewDatas(holder, t, position);
        return convertView;
    }

    /**
     * 建立convertView
     *
     * @param layoutInflater
     * @return
     */
    public abstract View buildConvertView(LayoutInflater layoutInflater, T t, int position);

    /**
     * 建立视图Holder
     *
     * @param convertView
     * @return
     */
    public abstract H buildHolder(View convertView, T t, int position);

    /**
     * 绑定数据
     *
     * @param holder
     * @param t
     * @param position
     */
    public abstract void bindViewDatas(H holder, T t, int position);


    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }
}
