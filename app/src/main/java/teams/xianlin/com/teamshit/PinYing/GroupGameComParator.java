package teams.xianlin.com.teamshit.PinYing;

import java.util.Comparator;

import teams.xianlin.com.teamshit.db.Groups;


/**
 * Created by Administrator on 2016/9/8.
 */
public class GroupGameComParator implements Comparator<Groups> {

    public static GroupGameComParator instance = null;

    public static GroupGameComParator getInstance() {
        if (instance == null) {
            instance = new GroupGameComParator();
        }
        return instance;
    }

    public int compare(Groups o1, Groups o2) {
        if (o1.getGroupType()
                >= o2.getGroupType()) {
            return -1;
        } else {
            return 1;
        }
    }
}
