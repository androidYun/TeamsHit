package teams.xianlin.com.teamshit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2016/7/28.
 */
public abstract class AbstractAdapter<T> extends android.widget.BaseAdapter {
    protected Context context;

    protected List<T> listData;

    protected LayoutInflater layoutInflater;

    public AbstractAdapter(Context context, List<T> listData) {
        this.context = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData == null ? null : listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getListData() {
        return listData;
    }

    public void setListData(List<T> listData) {
        this.listData = listData;
    }


    public View inflate(int layoutId) {
        return layoutInflater.inflate(layoutId, null,false);
    }
}
