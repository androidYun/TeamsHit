package teams.xianlin.com.teamshit.NetWork;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/12.
 */
public abstract class SocketPacket implements Serializable {
    @Expose
    private long UserId;//用户Id

    @Expose
    private int GameCommend;//游戏指令

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public int getGameCommend() {
        return GameCommend;
    }

    public void setGameCommend(int gameCommend) {
        GameCommend = gameCommend;
    }
}
