package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/8/15.
 */
public class GetUserDeatilInforResp extends PacketResp {
    @Expose
    private DetailUserInforBean DetailInfor;

    public DetailUserInforBean getDetailInfor() {
        return DetailInfor;
    }

    public void setDetailInfor(DetailUserInforBean detailInfor) {
        this.DetailInfor = detailInfor;
    }

    public class DetailUserInforBean {
        @Expose
        Long UserId;
        @Expose
        String NickName;
        @Expose
        String PortraitUri;
        @Expose
        String UserName;
        @Expose
        String Gender;
        @Expose
        String City;
        @Expose
        String BirthDate;

        public Long getUserId() {
            return UserId;
        }

        public void setUserId(Long userId) {
            UserId = userId;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String nickName) {
            NickName = nickName;
        }

        public String getPortraitUri() {
            return PortraitUri;
        }

        public void setPortraitUri(String portraitUri) {
            PortraitUri = portraitUri;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getBirthDate() {
            return BirthDate;
        }

        public void setBirthDate(String birthDate) {
            BirthDate = birthDate;
        }
    }
}
