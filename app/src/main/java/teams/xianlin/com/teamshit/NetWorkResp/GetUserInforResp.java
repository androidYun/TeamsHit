package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/8/13.
 */
public class GetUserInforResp extends PacketResp {

    @Expose
    private UserInforBean UserInfor;

    public UserInforBean getUserInfor() {
        return UserInfor;
    }

    public void setUserInfor(UserInforBean userInfor) {
        UserInfor = userInfor;
    }


    public  class UserInforBean {
        @Expose
        Long UserId;
        @Expose
        String Nickname;
        @Expose
        String PortraitUri;

        public Long getUserId() {
            return UserId;
        }

        public void setUserId(Long userId) {
            UserId = userId;
        }

        public String getNickname() {
            return Nickname;
        }

        public void setNickname(String nickname) {
            Nickname = nickname;
        }

        public String getPortraitUri() {
            return PortraitUri;
        }

        public void setPortraitUri(String portraitUri) {
            PortraitUri = portraitUri;
        }
    }
}

