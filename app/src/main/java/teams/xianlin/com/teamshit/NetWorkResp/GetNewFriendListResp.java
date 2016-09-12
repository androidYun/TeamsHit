package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import java.security.PrivateKey;
import java.util.List;

import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/8/12.
 */
public class GetNewFriendListResp extends PacketResp {
    @Expose
    private List<Friend> NewFriendList;

    public List<Friend> getFriendList() {
        return NewFriendList;
    }

    public void setFriendList(List<Friend> NewFriendList) {
        NewFriendList = NewFriendList;
    }

    public class Friend {


        @Expose
        private long ApplyId;//发起申请编号添加好友 或者拒绝好友的时候用
        @Expose
        private String Message;//信息
        @Expose
        private int Status;//1 好友, 2 别人邀请添加你为好友, 3 请求添加别人为好友
        @Expose
        private long UserId;//用户id
        @Expose
        private String Nickname;//昵称
        @Expose
        private String PortraitUri;//头像

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
        }

        public long getUserId() {
            return UserId;
        }

        public void setUserId(long userId) {
            UserId = userId;
        }

        public String getNickname() {
            return Nickname;
        }

        public void setNickname(String nickname) {
            Nickname = nickname;
        }

        public String getPortraitUri() {
            return PortraitUri;
        }

        public void setPortraitUri(String portraitUri) {
            PortraitUri = portraitUri;
        }

        public long getApplyId() {
            return ApplyId;
        }

        public void setApplyId(long applyId) {
            ApplyId = applyId;
        }
    }
}
