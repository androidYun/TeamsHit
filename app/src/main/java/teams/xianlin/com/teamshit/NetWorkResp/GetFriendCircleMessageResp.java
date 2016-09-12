package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import java.util.List;

import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/9/1.
 */
public class GetFriendCircleMessageResp extends PacketResp {

    @Expose
    private int AllCur;
    @Expose
    private int AllCount;
    @Expose
    private List<FriendCircleMessageItem> FriendCircleMessageList;

    public int getAllCur() {
        return AllCur;
    }

    public void setAllCur(int allCur) {
        AllCur = allCur;
    }

    public int getAllCount() {
        return AllCount;
    }

    public void setAllCount(int allCount) {
        AllCount = allCount;
    }

    public List<FriendCircleMessageItem> getFriendCircleMessageList() {
        return FriendCircleMessageList;
    }

    public void setFriendCircleMessageList(List<FriendCircleMessageItem> friendCircleMessageList) {
        FriendCircleMessageList = friendCircleMessageList;
    }

    public class FriendCircleMessageItem {
        @Expose
        private long TakeId;
        @Expose
        private String TakeContent;
        @Expose
        private String TakePhoto;//返回图片第一张
        @Expose
        private long CreateTime;//评论或者点赞创建时间
        @Expose
        private int IsFavoriteAndComenmt;
        @Expose
        private String CommentContent;//评论的内容
        @Expose
        private UserBean User;

        public long getTakeId() {
            return TakeId;
        }

        public void setTakeId(long takeId) {
            TakeId = takeId;
        }

        public String getTakeContent() {
            return TakeContent;
        }

        public void setTakeContent(String takeContent) {
            TakeContent = takeContent;
        }

        public String getTakePhoto() {
            return TakePhoto;
        }

        public void setTakePhoto(String takePhoto) {
            TakePhoto = takePhoto;
        }

        public long getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(long createTime) {
            CreateTime = createTime;
        }

        public int getIsFavoriteAndComenmt() {
            return IsFavoriteAndComenmt;
        }

        public void setIsFavoriteAndComenmt(int isFavoriteAndComenmt) {
            IsFavoriteAndComenmt = isFavoriteAndComenmt;
        }

        public String getCommentContent() {
            return CommentContent;
        }

        public void setCommentContent(String commentContent) {
            CommentContent = commentContent;
        }

        public UserBean getUser() {
            return User;
        }

        public void setUser(UserBean user) {
            User = user;
        }
    }
}
