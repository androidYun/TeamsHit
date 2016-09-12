package teams.xianlin.com.teamshit.MainFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.rong.imkit.RongIM;
import teams.xianlin.com.teamshit.Activity.FriendDetailActivity;
import teams.xianlin.com.teamshit.Activity.GroupListActivity;
import teams.xianlin.com.teamshit.Activity.NewFriendActivity;
import teams.xianlin.com.teamshit.Adapter.FriendAdapter;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.BaseInfor.TeamHitContext;
import teams.xianlin.com.teamshit.BroadCastService.BroadcastManager;
import teams.xianlin.com.teamshit.EventBus.OperatorFriendEvent;
import teams.xianlin.com.teamshit.PinYing.CharacterParser;
import teams.xianlin.com.teamshit.PinYing.Friend;
import teams.xianlin.com.teamshit.PinYing.PinyinComparator;
import teams.xianlin.com.teamshit.PinYing.SideBar;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.RongYun.SealAppContext;
import teams.xianlin.com.teamshit.Utils.ImageUtisl.ImageLoadUtils;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.ToastUtil;
import teams.xianlin.com.teamshit.db.DBManager;
import teams.xianlin.com.teamshit.httpService.NetUtils;
import teams.xianlin.com.teamshit.widget.SelectableRoundedImageView;

/**
 * Created by Administrator on 2016/7/15.
 */
public class FriendFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private View friendView;

    private View headView;

    private RelativeLayout re_newfriends;

    private RelativeLayout re_group;

    private RelativeLayout seal_me;

    private EditText edit_search;

    private ListView listview;//好友列表

    private TextView txt_my_name;//我的姓名

    private ImageView img_my_head;//我的头像

    private TextView group_dialog;

    private TextView mNoFriends;//如果没有朋友的时候显示

    private TextView unread;//没有读取的信息

    private SideBar sidrbar;

    private FriendAdapter friendAdapter;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private List<Friend> dataLsit = new ArrayList();//存放好友列表

    private List<Friend> sourceDataList = new ArrayList<>();//通过因为编译的列表

    private List<Friend> tempList = new ArrayList<>();//通过搜索获取的列表

    private boolean isFilter;//判断是否过滤没有

    public static FriendFragment getInstance() {
        return new FriendFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        friendView = inflater.inflate(R.layout.fragment_friend_layout, null, false);
        initialView();
        initialData();
        inflateView();
        inflateData();
        refreshUIListener();
        return friendView;
    }

    @Override
    protected void initialView() {
        headView = LayoutInflater.from(mActivity).inflate(R.layout.item_contact_list_header, null, false);
        re_newfriends = (RelativeLayout) headView.findViewById(R.id.re_newfriends);
        re_group = (RelativeLayout) headView.findViewById(R.id.re_chatroom);
        seal_me = (RelativeLayout) headView.findViewById(R.id.contact_me_item);
        edit_search = (EditText) friendView.findViewById(R.id.edit_search);
        listview = (ListView) friendView.findViewById(R.id.listview);
        group_dialog = (TextView) friendView.findViewById(R.id.group_dialog);
        mNoFriends = (TextView) friendView.findViewById(R.id.show_no_friend);
        unread = (TextView) headView.findViewById(R.id.tv_unread);
        sidrbar = (SideBar) friendView.findViewById(R.id.sidrbar);
        img_my_head = (SelectableRoundedImageView) headView.findViewById(R.id.contact_me_img);
        txt_my_name = (TextView) headView.findViewById(R.id.contact_me_name);
        characterParser = new CharacterParser();
        pinyinComparator = PinyinComparator.getInstance();
        listview.addHeaderView(headView);
        edit_search.addTextChangedListener(new TextWecherClass());
        listview.setOnItemClickListener(this);
        seal_me.setOnClickListener(this);
        re_group.setOnClickListener(this);
        re_newfriends.setOnClickListener(this);
    }

    @Override
    protected void initialData() {
        if (dataLsit != null) {
            dataLsit.clear();
        }
        if (sourceDataList != null) {
            sourceDataList.clear();
        }
        List<teams.xianlin.com.teamshit.db.Friend> list = DBManager.getInstance(getActivity()).getDaoSession().getFriendDao().loadAll();
        if (list != null && list.size() > 0) {
            dataLsit.clear();
            for (teams.xianlin.com.teamshit.db.Friend friend : list) {
                dataLsit.add(new Friend(friend.getUserId(), friend.getName(), friend.getPortraitUri(), friend.getDisplayName(), null, null));
            }
        }
        if (dataLsit != null && dataLsit.size() != 0) {
            //  sourceDataList = filledData(dataLsit);
            sourceDataList.addAll(filledData(dataLsit));
        }
        //还原除了带字母字段的其他数据
        for (int i = 0; i < dataLsit.size(); i++) {
            sourceDataList.get(i).setName(dataLsit.get(i).getName());
            sourceDataList.get(i).setUserId(dataLsit.get(i).getUserId());
            sourceDataList.get(i).setPortraitUri(dataLsit.get(i).getPortraitUri());
            sourceDataList.get(i).setDisplayName(dataLsit.get(i).getDisplayName());
        }

        // 根据a-z进行排序源数据
        Collections.sort(sourceDataList, pinyinComparator);
    }

    /**
     * 为ListView填充数据
     *
     * @param
     * @return
     */
    private List<Friend> filledData(List<Friend> lsit) {
        List<Friend> mFriendList = new ArrayList<Friend>();
        for (int i = 0; i < lsit.size(); i++) {
            Friend friendModel = new Friend();
            friendModel.setName(lsit.get(i).getName());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(lsit.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                friendModel.setLetters(sortString.toUpperCase());
            } else {
                friendModel.setLetters("#");
            }

            mFriendList.add(friendModel);
        }
        return mFriendList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<Friend> filterDateList = new ArrayList<Friend>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = sourceDataList;
        } else {
            filterDateList.clear();
            for (Friend friendModel : sourceDataList) {
                String name = friendModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(friendModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        isFilter = true;
        tempList = filterDateList;
        friendAdapter.updateListView(filterDateList);
    }

    @Override
    protected void inflateView() {
        txt_my_name.setText("李桂云");
        ImageLoadUtils.getInstance().with(mActivity, "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96", img_my_head);
    }

    public void inflateData() {
        if (friendAdapter == null) {
            friendAdapter = new FriendAdapter(mActivity, sourceDataList);
            listview.setAdapter(friendAdapter);
        } else {
            friendAdapter.updateListView(sourceDataList);
        }
        displayUnread();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.re_newfriends:
                unread.setVisibility(View.GONE);
                TeamHitContext instance = TeamHitContext.getInstance();//清除新朋友提醒
                DBManager.getInstance(mActivity).getDaoSession().getNewFriendDao().deleteAll();
                Intent newFriendIntent = new Intent(mActivity, NewFriendActivity.class);
                startActivity(newFriendIntent);
                break;
            case R.id.re_chatroom:
                Intent chatroomIntent = new Intent(mActivity, GroupListActivity.class);
                startActivity(chatroomIntent);
                break;
            case R.id.contact_me_item:
                RongIM.getInstance().startPrivateChat(mActivity, "160534", "马传播");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (!NetUtils.getinStance().isNetworkAvailable(mActivity)) {
            ToastUtil.show(mActivity, Constant.Not_NetWork_error);
            return;
        }
        if (RongIM.getInstance() == null) {
            ToastUtil.show(mActivity, "没有连接服务器");
            return;
        }
        Intent friendDetailIntent = new Intent(mActivity, FriendDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.Friend_Detail_Type, 2);//传递2  是一个UserId 如果是1  则是传递的一个详情类

        if (isFilter) {
            Friend bean = tempList.get(position - 1);
            bundle.putString(Constant.Target_Id, bean.getUserId());
        } else {
            Friend bean = sourceDataList.get(position - 1);
            bundle.putString(Constant.Target_Id, bean.getUserId());
        }
        friendDetailIntent.putExtras(bundle);
        startActivity(friendDetailIntent);
    }

    class TextWecherClass implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            filterData(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private void refreshUIListener() {//接收广播然后刷新UI
        BroadcastManager.getInstance(getActivity()).addAction(SealAppContext.Updater_Friend, new BroadcastReceiver() {//更新朋友说亮信息
            @Override
            public void onReceive(Context context, Intent intent) {
                String command = intent.getAction();
                if (!TextUtils.isEmpty(command)) {
                    updateUI();
                }
            }
        });

        BroadcastManager.getInstance(getActivity()).addAction(SealAppContext.Updater_Eddot, new BroadcastReceiver() {//更新未读取信息的数量
            @Override
            public void onReceive(Context context, Intent intent) {
                String command = intent.getAction();
                String mTargetId = intent.getStringExtra(Constant.Intent_Result);
                if (!TextUtils.isEmpty(command)) {
                    displayUnread();
                }

            }
        });
        BroadcastManager.getInstance(getActivity()).addAction(Constant.CHANGEINFO, new BroadcastReceiver() {//更新个人信息
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
            }
        });

    }

    /**
     * 显示新朋友红点点
     */
    private void displayUnread() {
        long count = DBManager.getInstance(mActivity).getDaoSession().getNewFriendDao().count();
        if (count == 0) {
            unread.setVisibility(View.GONE);
        } else {
            unread.setVisibility(View.VISIBLE);
            unread.setText(count + "");
        }
    }

    private void updateUI() {
        List<teams.xianlin.com.teamshit.db.Friend> list = DBManager.getInstance(getActivity()).getDaoSession().getFriendDao().loadAll();
        if (list != null) {
            if (dataLsit != null) {
                dataLsit.clear();
            }
            if (sourceDataList != null) {
                sourceDataList.clear();
            }
            for (teams.xianlin.com.teamshit.db.Friend friend : list) {
                dataLsit.add(new Friend(friend.getUserId(), friend.getName(), friend.getPortraitUri()));
            }
        }
        if (dataLsit != null) {
            sourceDataList.addAll(filledData(dataLsit)); //过滤数据为有字母的字段  现在有字母 别的数据没有
        } else {
            mNoFriends.setVisibility(View.VISIBLE);
        }
        //还原除了带字母字段的其他数据
        for (int i = 0; i < dataLsit.size(); i++) {
            sourceDataList.get(i).setName(dataLsit.get(i).getName());
            sourceDataList.get(i).setUserId(dataLsit.get(i).getUserId());
            sourceDataList.get(i).setPortraitUri(dataLsit.get(i).getPortraitUri());
            sourceDataList.get(i).setDisplayName(dataLsit.get(i).getDisplayName());
        }
        // 根据a-z进行排序源数据
        Collections.sort(sourceDataList, pinyinComparator);
        friendAdapter.updateListView(sourceDataList);
    }

    /**
     * 添加好友 和删除好友   更新好友列表
     *
     * @param event
     */
    public void onEventMainThread(OperatorFriendEvent event) {
        initialData();
        inflateData();
    }

}
