package teams.xianlin.com.teamshit.Bean;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2016/8/15.
 */
public class UserInforBean {

    long UserId;

    String Nickname;

    String PortraitUri;

    String UserName;

    String Gender;

    String City;

    String BirthDate;

    public UserInforBean(long userId, String nickname, String portraitUri, String userName, String gender, String city, String birthDate) {
        UserId = userId;
        Nickname = nickname;
        PortraitUri = portraitUri;
        UserName = userName;
        Gender = gender;
        City = city;
        BirthDate = birthDate;
    }




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
