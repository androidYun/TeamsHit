package teams.xianlin.com.teamshit.Bean;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2016/8/25.
 */
public class FavoriteListBean {
    @Expose
    private long FavoriteId;//等删除点赞的时候用到
    @Expose
    private UserBean User;


    public UserBean getUser() {
        return User;
    }

    public void setUser(UserBean user) {
        User = user;
    }

    public void setFavoriteId(long favoriteId) {
        FavoriteId = favoriteId;
    }

    public long getFavoriteId() {
        return FavoriteId;
    }

}
