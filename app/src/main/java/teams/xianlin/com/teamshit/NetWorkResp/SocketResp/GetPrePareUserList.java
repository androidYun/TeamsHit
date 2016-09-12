package teams.xianlin.com.teamshit.NetWorkResp.SocketResp;

import com.google.gson.annotations.Expose;

import java.util.List;

import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.NetWork.SocketPacketResp;

/**
 * Created by Administrator on 2016/9/12.
 */
public class GetPrePareUserList extends SocketPacketResp {
    @Expose
    private List<UserBean> User;

    public List<UserBean> getUser() {
        return User;
    }

    public void setUser(List<UserBean> user) {
        User = user;
    }
}
