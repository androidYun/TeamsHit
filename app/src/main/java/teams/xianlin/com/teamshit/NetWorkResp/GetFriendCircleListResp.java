package teams.xianlin.com.teamshit.NetWorkResp;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;

import java.util.List;

import teams.xianlin.com.teamshit.Bean.CommentItemBean;
import teams.xianlin.com.teamshit.Bean.FavoriteListBean;
import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.NetWork.PacketResp;
import teams.xianlin.com.teamshit.Utils.LogUtil;

/**
 * Created by Administrator on 2016/8/25.
 */
public class GetFriendCircleListResp extends PacketResp {
    @Expose
    private int AllCur;
    @Expose
    private int AllCount;
    @Expose
    public List<FriendCircleItem> FriendCircleList;

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

    public List<FriendCircleItem> getFriendCircleList() {
        return FriendCircleList;
    }

    public void setFriendCircleList(List<FriendCircleItem> friendCircleList) {
        FriendCircleList = friendCircleList;
    }

    public class FriendCircleItem {
        @Expose
        private UserBean User;
        @Expose
        private long TakeId;
        @Expose
        private String TakeContent;
        @Expose
        private String PhotoLists;
        @Expose
        private long CreateTime;
        @Expose
        private List<FavoriteListBean> FavoriteLists;
        @Expose
        private List<CommentItemBean> CommentLists;

        private int messageNumber;//消息的数量 只有有消息的时候使用

        public UserBean getUser() {
            return User;
        }

        public void setUser(UserBean User) {
            this.User = User;
        }

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

        public String getPhotoLists() {
            return PhotoLists;
        }

        public void setPhotoLists(String photoLists) {
            PhotoLists = photoLists;
        }

        public long getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(long createTime) {
            CreateTime = createTime;
        }

        public int getMessageNumber() {
            return messageNumber;
        }

        public void setMessageNumber(int messageNumber) {
            this.messageNumber = messageNumber;
        }

        public List<FavoriteListBean> getFavoriteLists() {
            return FavoriteLists;
        }

        public void setFavoriteLists(List<FavoriteListBean> favoriteLists) {
            FavoriteLists = favoriteLists;
        }

        public List<CommentItemBean> getCommentLists() {
            return CommentLists;
        }

        public void setCommentLists(List<CommentItemBean> commentLists) {
            CommentLists = commentLists;
        }

        public boolean hasFavort() {//是否有点赞功能
            if (FavoriteLists != null && FavoriteLists.size() > 0) {
                return true;
            }
            return false;
        }

        public boolean hasComment() {//是否有评论
            if (CommentLists != null && CommentLists.size() > 0) {
                return true;
            }
            return false;
        }

        public String getCurUserFavortId(String curUserId) {
            String favortid = "";
            if (!TextUtils.isEmpty(curUserId) && hasFavort()) {
                for (FavoriteListBean item : FavoriteLists) {
                    String userId = String.valueOf(item.getUser().getUserId());
                    if (curUserId.equals(userId)) {
                        favortid = String.valueOf(item.getFavoriteId());
                        return favortid;
                    }
                }
            }
            LogUtil.d("知道" + "      " + favortid + "     " + curUserId);
            return favortid;
        }

        /**
         * 返回点赞功能
         *
         * @param curUserID
         * @return
         */
        public FavoriteListBean getFavoriteBeanForFavoriteId(String curUserID) {
            if (!TextUtils.isEmpty(curUserID) && hasFavort()) {
                for (FavoriteListBean item : FavoriteLists) {
                    String userId = String.valueOf(item.getUser().getUserId());
                    if (userId.equals(curUserID)) {
                        return item;
                    }
                }
            }
            return null;
        }

        /**
         * 返回点赞功能
         *
         * @param curCommentId
         * @return
         */
        public CommentItemBean getCommentBeanForCommentId(String curCommentId) {
            if (!TextUtils.isEmpty(curCommentId) && hasComment()) {
                for (CommentItemBean item : CommentLists) {
                    String CommentId = String.valueOf(item.getCommentId());
                    if (curCommentId.equals(CommentId)) {
                        return item;
                    }
                }
            }
            return null;
        }
    }
}
