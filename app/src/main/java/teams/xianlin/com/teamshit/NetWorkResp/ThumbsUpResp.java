package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/8/27.
 */
public class ThumbsUpResp extends PacketResp {
    @Expose
    private long FavoriteId;

    public long getFavoriteId() {
        return FavoriteId;
    }

    public void setFavoriteId(long favoriteId) {
        FavoriteId = favoriteId;
    }
}
