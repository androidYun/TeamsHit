package teams.xianlin.com.teamshit.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import teams.xianlin.com.teamshit.EventBus.OperatorFriendEvent;
import teams.xianlin.com.teamshit.MainFragment.ConversationListStaticFragment;
import teams.xianlin.com.teamshit.MainFragment.FindFragment;
import teams.xianlin.com.teamshit.MainFragment.FriendFragment;
import teams.xianlin.com.teamshit.MainFragment.MineFragment;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.RongYun.UserInfoEngine;
import teams.xianlin.com.teamshit.widget.Popwindow.SelectorChatWindow;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, RongIM.UserInfoProvider {
    @Bind(R.id.img_chat)
    ImageView imgChat;
    @Bind(R.id.framelayout)
    FrameLayout framelayout;
    @Bind(R.id.rbtn_team)
    RadioButton rbtnTeam;
    @Bind(R.id.rbtn_find)
    RadioButton rbtnFind;
    @Bind(R.id.rbtn_friend)
    RadioButton rbtnFriend;
    @Bind(R.id.rbtn_mine)
    RadioButton rbtnMine;
    @Bind(R.id.main_rgroup)
    RadioGroup mainRgroup;
    private FragmentTransaction fragmentTransaction;

    private MineFragment mineFragment;

    private FriendFragment friendFragment;

    private FindFragment findFragment;

    private Fragment mConversationListFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        contactRongYunService();
    }

    public void contactRongYunService() {
        if (RongIM.getInstance() != null && RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED)) {
            SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
            String token = sp.getString("loginToken", "");
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    initialView();
                    initialData();
                    inflateView();
                }

                @Override
                public void onSuccess(String s) {

                }

                @Override
                public void onError(RongIMClient.ErrorCode e) {

                }
            });
        } else {
            initialView();
            initialData();
            inflateView();
        }
    }

    @Override
    public void initialView() {
        mainRgroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initialData() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mConversationListFragment = ConversationListStaticFragment.getInstance();
        fragmentTransaction.replace(R.id.framelayout, mConversationListFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void inflateView() {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (checkId) {
            case R.id.rbtn_team:
                changgeNaviState(rbtnTeam);
                if (mConversationListFragment == null) {
                    mConversationListFragment = ConversationListStaticFragment.getInstance();
                }
                fragmentTransaction.replace(R.id.framelayout, mConversationListFragment);
                break;
            case R.id.rbtn_find:
                changgeNaviState(rbtnFind);
                if (findFragment == null) {
                    findFragment = FindFragment.getInstance();
                }
                fragmentTransaction.replace(R.id.framelayout, findFragment);
                break;
            case R.id.rbtn_friend:
                changgeNaviState(rbtnFriend);
                // if (friendFragment == null) {
                friendFragment = FriendFragment.getInstance();
                // }
                fragmentTransaction.replace(R.id.framelayout, friendFragment);
                break;
            case R.id.rbtn_mine:
                changgeNaviState(rbtnMine);
                if (mineFragment == null) {
                    mineFragment = MineFragment.getInstance();
                }
                fragmentTransaction.replace(R.id.framelayout, mineFragment);
                break;
        }
        fragmentTransaction.commit();
    }

    public void changgeNaviState(RadioButton radioButton) {
        rbtnTeam.setTextColor(getResources().getColor(R.color.alway_text_gray));
        rbtnFind.setTextColor(getResources().getColor(R.color.alway_text_gray));
        rbtnFriend.setTextColor(getResources().getColor(R.color.alway_text_gray));
        rbtnMine.setTextColor(getResources().getColor(R.color.alway_text_gray));
        radioButton.setTextColor(getResources().getColor(R.color.selector_text_color));
    }

    @OnClick({R.id.img_chat})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_chat:
                SelectorChatWindow selectorChatWindow = new SelectorChatWindow(MainActivity.this);
                selectorChatWindow.showPopupWindow(imgChat);
                break;
        }
    }

    @Override
    public UserInfo getUserInfo(String s) {
        UserInfo userInfo = UserInfoEngine.getInstance(MainActivity.this).startEngine(Long.valueOf(s));
        return userInfo;
    }

    public void onEventMainThread(OperatorFriendEvent event) {
        if (event.getFriendType() == 1) {

        } else if (event.getFriendType() == 2) {

        }
    }

}
