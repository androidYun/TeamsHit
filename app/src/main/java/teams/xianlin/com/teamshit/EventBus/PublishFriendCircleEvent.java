package teams.xianlin.com.teamshit.EventBus;

import teams.xianlin.com.teamshit.NetWorkResp.GetFriendCircleListResp;

/**
 * Created by Administrator on 2016/8/30.
 * 发表说说
 */
public class PublishFriendCircleEvent {

    public PublishFriendCircleEvent(GetFriendCircleListResp.FriendCircleItem friendCircleItem) {
        this.friendCircleItem = friendCircleItem;
    }

    private GetFriendCircleListResp.FriendCircleItem friendCircleItem;

    public GetFriendCircleListResp.FriendCircleItem getFriendCircleItem() {
        return friendCircleItem;
    }

    public void setFriendCircleItem(GetFriendCircleListResp.FriendCircleItem friendCircleItem) {
        this.friendCircleItem = friendCircleItem;
    }
}
