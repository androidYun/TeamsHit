package teams.xianlin.com.teamshit.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teams.xianlin.com.teamshit.Activity.FriendCircleActivity;
import teams.xianlin.com.teamshit.EventBus.FriendCircleMessageEvent;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.db.DBManager;

/**
 * Created by Administrator on 2016/7/15.
 */
public class FindFragment extends BaseFragment {
    @Bind(R.id.layout_friend_circle)
    LinearLayout layoutFriendCircle;
    private static FindFragment findFragment;
    @Bind(R.id.tv_unread)
    TextView tvUnread;

    private View findView;

    private long mCount;//新消息数量


    public static FindFragment getInstance() {
        if (findFragment == null) {
            findFragment = new FindFragment();
        }
        return findFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        findView = inflater.inflate(R.layout.fragment_find_layout, container, false);
        ButterKnife.bind(this, findView);
        initialView();
        initialData();
        inflateView();
        return findView;
    }

    @Override
    protected void initialView() {
        initialUbRead();
    }

    @Override
    protected void initialData() {

    }

    @Override
    protected void inflateView() {

    }

    public void initialUbRead() {
        mCount = DBManager.getInstance(mActivity).getDaoSession().getMessageUserDao().count();
        if (mCount > 0) {
            tvUnread.setVisibility(View.VISIBLE);
            tvUnread.setText("" + mCount);
        }
    }

    @OnClick({R.id.layout_friend_circle})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_unread:

                break;
            case R.id.re_chatroom:

                break;
            case R.id.contact_me_item:

                break;
            case R.id.layout_friend_circle:
                Intent friendCircleIntent = new Intent(mActivity, FriendCircleActivity.class);
                startActivity(friendCircleIntent);
                break;
        }
    }

    public void onEventMainThread(FriendCircleMessageEvent event) {
        initialUbRead();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
