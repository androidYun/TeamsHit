package teams.xianlin.com.teamshit.EventBus;

/**
 * Created by Administrator on 2016/8/22.
 */
public class OperatorFriendEvent {
    private int friendType;//1 是添加好友   2  是删除好友

    private long TargetId;//好友ID

    public OperatorFriendEvent(int friendType, long targetId) {
        this.friendType = friendType;
        TargetId = targetId;
    }

    public int getFriendType() {
        return friendType;
    }

    public void setFriendType(int friendType) {
        this.friendType = friendType;
    }

    public long getTargetId() {
        return TargetId;
    }

    public void setTargetId(long targetId) {
        TargetId = targetId;
    }
}
