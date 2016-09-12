package teams.xianlin.com.teamshit.PinYing;

import java.util.Comparator;

import teams.xianlin.com.teamshit.Bean.SelectorGroupChatFriendBean;

/**
 * Created by Administrator on 2016/8/24.
 */
public class SelectorGroupChatPinyinComparator implements Comparator<SelectorGroupChatFriendBean> {
    public static SelectorGroupChatPinyinComparator instance = null;

    public static SelectorGroupChatPinyinComparator getInstance() {
        if (instance == null) {
            instance = new SelectorGroupChatPinyinComparator();
        }
        return instance;
    }

    public int compare(SelectorGroupChatFriendBean o1, SelectorGroupChatFriendBean o2) {
        if (o1.getLetters().equals("@")
                || o2.getLetters().equals("#")) {
            return -1;
        } else if (o1.getLetters().equals("#")
                || o2.getLetters().equals("@")) {
            return 1;
        } else {
            return o1.getLetters().compareTo(o2.getLetters());
        }
    }
}
