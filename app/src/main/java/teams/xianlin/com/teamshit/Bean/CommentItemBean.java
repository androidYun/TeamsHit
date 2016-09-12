package teams.xianlin.com.teamshit.Bean;

import com.google.gson.annotations.Expose;

import java.net.ContentHandler;

/**
 * Created by Administrator on 2016/8/25.
 */
public class CommentItemBean {
    @Expose
    private long CommentId;
    @Expose
    private String Content;
    @Expose
    private UserBean User;
    @Expose
    private ToReplyUserBean ToReplyUser;

    public long getCommentId() {
        return CommentId;
    }

    public void setCommentId(long commentId) {
        CommentId = commentId;
    }

    public UserBean getUser() {
        return User;
    }

    public void setUser(UserBean user) {
        User = user;
    }

    public ToReplyUserBean getToReplyUser() {
        return ToReplyUser;
    }

    public void setToReplyUser(ToReplyUserBean toReplyUser) {
        ToReplyUser = toReplyUser;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
