package teams.xianlin.com.teamshit.NetWork;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2016/9/12.
 */
public class SocketPacketResp extends Packet {
    @Expose
    int GameCommend;

    public int getGameCommend() {
        return GameCommend;
    }

    public void setGameCommend(int gameCommend) {
        GameCommend = gameCommend;
    }
}
