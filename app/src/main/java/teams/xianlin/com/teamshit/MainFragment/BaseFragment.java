package teams.xianlin.com.teamshit.MainFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import io.rong.eventbus.EventBus;

/**
 * Created by Administrator on 2016/7/15.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    public Context mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
        mActivity = context;
    }

    protected abstract void initialView();

    protected abstract void initialData();

    protected abstract void inflateView();

    @Override
    public void onClick(View view) {

    }

    public void onEventMainThread(EventBus event) {


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
