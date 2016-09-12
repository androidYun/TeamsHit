package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import java.util.List;

import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/7/16.
 */
public class GetFriendsListResp extends PacketResp {
    @Expose
    private List<Friend> FriendList;

    public List<Friend> getFriendList() {
        return FriendList;
    }

    public void setFriendList(List<Friend> friendList) {
        FriendList = friendList;
    }

    public class Friend {
        @Expose
        private String DisplayName;//备注显示的名字
        @Expose
        private String Message;//信息
        @Expose
        private String UpdatedAt;//添加时间
        @Expose
        private int Status;
        /**
         * 1 好友, 2 请求添加, 3 请求被添加, 4 请求被拒绝, 5 我被对方删除
         */
        @Expose
        private long TargetId;
        @Expose
        private String NickName;//昵称
        @Expose
        private String PortraitUri;//头像


        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        public String getUpdatedAt() {
            return UpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            UpdatedAt = updatedAt;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
        }

        public long getTargetId() {
            return TargetId;
        }

        public void setTargetId(long targetId) {
            TargetId = targetId;
        }

        public String getPortraitUri() {
            return PortraitUri;
        }

        public void setPortraitUri(String portraitUri) {
            PortraitUri = portraitUri;
        }

        public String getDisplayName() {
            return DisplayName;
        }

        public void setDisplayName(String displayName) {
            DisplayName = displayName;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String nickName) {
            NickName = nickName;
        }

        @Override
        public String toString() {
            return "Friend{" +
                    "DisplayName='" + DisplayName + '\'' +
                    ", Message='" + Message + '\'' +
                    ", UpdatedAt='" + UpdatedAt + '\'' +
                    ", Status=" + Status +
                    ", TargetId=" + TargetId +
                    ", NickName='" + NickName + '\'' +
                    ", PortraitUri='" + PortraitUri + '\'' +
                    '}';
        }
    }

}
