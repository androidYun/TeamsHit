package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/7/6.
 */
public class LoginResp extends PacketResp {
    @Expose
    private String UserToken;
    @Expose
    private String RongToken;// 是否分销商0否，1是
    @Expose
    private Boolean IsCompleteInfor;//是否补全资料

    @Expose
    private long UserId;//用户id


    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public String getRongToken() {
        return RongToken;
    }

    public void setRongToken(String rongToken) {
        RongToken = rongToken;
    }

    public Boolean getCompleteInfor() {
        return IsCompleteInfor;
    }

    public void setCompleteInfor(Boolean completeInfor) {
        IsCompleteInfor = completeInfor;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }
}
