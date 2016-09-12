package teams.xianlin.com.teamshit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import teams.xianlin.com.teamshit.Adapter.StartGroupChatAdapter;
import teams.xianlin.com.teamshit.BaseInfor.Constant;
import teams.xianlin.com.teamshit.Bean.SelectorGroupChatFriendBean;
import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.Interface.OnItemClickListener;
import teams.xianlin.com.teamshit.PinYing.CharacterParser;
import teams.xianlin.com.teamshit.PinYing.Friend;
import teams.xianlin.com.teamshit.PinYing.SelectorGroupChatPinyinComparator;
import teams.xianlin.com.teamshit.PinYing.SideBar;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.db.DBManager;
import teams.xianlin.com.teamshit.widget.TitleNavi;

/**
 * Created by Administrator on 2016/8/24.
 */
public class StartGroupChatActivity extends BaseActivity implements OnItemClickListener<SelectorGroupChatFriendBean> {
    @Bind(R.id.navi_top)
    TitleNavi naviTop;
    @Bind(R.id.lvi_friend)
    ListView lviFriend;
    @Bind(R.id.txt_dialog)
    TextView txtDialog;
    @Bind(R.id.dis_sidrbar)
    SideBar disSidrbar;
    @Bind(R.id.edit_search)
    EditText editSearch;
    @Bind(R.id.txt_empty_view)
    TextView txtEmptyView;

    private List<Friend> dataLsit = new ArrayList();//存放好友列表

    private List<SelectorGroupChatFriendBean> sourceDataList = new ArrayList<>();//通过因为编译的列表

    private List<SelectorGroupChatFriendBean> tempList = new ArrayList<>();//通过搜索获取的列表

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private SelectorGroupChatPinyinComparator selectorGroupChatPinyinComparator;

    private StartGroupChatAdapter startGroupChatAdapter;

    private boolean isFilter;//是否是帅选的数据


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_group_chat);
        ButterKnife.bind(this);
        initialView();
        initialData();
        inflateView();
    }

    @Override
    public void initialView() {
        characterParser = new CharacterParser();
        selectorGroupChatPinyinComparator = SelectorGroupChatPinyinComparator.getInstance();
        editSearch.addTextChangedListener(new TextWecherClass());
    }

    @Override
    protected void initialData() {
        if (dataLsit != null) {
            dataLsit.clear();
        }
        if (sourceDataList != null) {
            sourceDataList.clear();
        }
        List<teams.xianlin.com.teamshit.db.Friend> list = DBManager.getInstance(mActivity).getDaoSession().getFriendDao().loadAll();
        if (list != null && list.size() > 0) {
            dataLsit.clear();
            for (teams.xianlin.com.teamshit.db.Friend friend : list) {
                dataLsit.add(new Friend(friend.getUserId(), friend.getName(), friend.getPortraitUri(), friend.getDisplayName(), null, null));
            }
        }
        if (dataLsit != null && dataLsit.size() != 0) {
            sourceDataList.addAll(filledData(dataLsit));
        }
        //还原除了带字母字段的其他数据
        for (int i = 0; i < dataLsit.size(); i++) {
            sourceDataList.get(i).setName(dataLsit.get(i).getName());
            sourceDataList.get(i).setUserId(dataLsit.get(i).getUserId());
            sourceDataList.get(i).setPortraitUri(dataLsit.get(i).getPortraitUri());
            sourceDataList.get(i).setDisplayName(dataLsit.get(i).getDisplayName());
            sourceDataList.get(i).setSlector(false);
        }
        // 根据a-z进行排序源数据
        Collections.sort(sourceDataList, selectorGroupChatPinyinComparator);
    }

    @Override
    protected void inflateView() {
        naviTop.setBackTitle("选择群组成员");
        naviTop.setFinish("确定");
        lviFriend.setEmptyView(txtEmptyView);
        naviTop.setClickCallBack(this);
        startGroupChatAdapter = new StartGroupChatAdapter(mActivity, sourceDataList);
        startGroupChatAdapter.setOnItemClickListener(this);
        lviFriend.setAdapter(startGroupChatAdapter);
    }

    /**
     * 为ListView填充数据
     *
     * @param
     * @return
     */
    private List<SelectorGroupChatFriendBean> filledData(List<Friend> lsit) {
        List<SelectorGroupChatFriendBean> mFriendList = new ArrayList<SelectorGroupChatFriendBean>();
        for (int i = 0; i < lsit.size(); i++) {
            SelectorGroupChatFriendBean friendModel = new SelectorGroupChatFriendBean();
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
        List<SelectorGroupChatFriendBean> filterDateList = new ArrayList<SelectorGroupChatFriendBean>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = sourceDataList;
        } else {
            filterDateList.clear();
            for (SelectorGroupChatFriendBean friendModel : sourceDataList) {
                String name = friendModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(friendModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, selectorGroupChatPinyinComparator);
        isFilter = true;
        tempList = filterDateList;
        if (filterDateList != null || filterDateList.size() > 0) {
            startGroupChatAdapter.updateListView(filterDateList);
        }
    }

    @Override
    public void onItemClick(View view, int position, SelectorGroupChatFriendBean model) {
        model.setSlector(!model.getSlector());//如果为true  就设置为false
        sourceDataList.set(position, model);//更新数据列表
        startGroupChatAdapter.updateProgressPartly(lviFriend, position);//更新单挑目录
    }

    @Override
    public void onItemLongClick(View view, int position, SelectorGroupChatFriendBean model) {

    }

    @Override
    public void onRightClick() {
        StringBuilder builder = new StringBuilder();
        UserBean userBean = getUserBean();
        if(userBean!=null){
            builder.append(userBean.getUserId()+",");
        }
        for (SelectorGroupChatFriendBean chatFriendBean :
                sourceDataList) {
            if (chatFriendBean.getSlector()) {
                builder.append(chatFriendBean.getUserId() + ",");
            }
        }
        Intent createGroupIntent = new Intent(StartGroupChatActivity.this, CreateGroupActivity.class);
        createGroupIntent.putExtra(Constant.MemberIds, builder.toString());
        startActivity(createGroupIntent);
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
}
