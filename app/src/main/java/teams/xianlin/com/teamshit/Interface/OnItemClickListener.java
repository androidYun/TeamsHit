package teams.xianlin.com.teamshit.Interface;

import android.view.View;

/**
 * Created by Administrator on 2016/8/12.
 */
public interface OnItemClickListener<T> {
    // 点击事件接口

    void onItemClick(View view, int position, T model);

    void onItemLongClick(View view, int position, T model);

}
