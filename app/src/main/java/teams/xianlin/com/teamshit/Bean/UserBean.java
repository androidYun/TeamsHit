package teams.xianlin.com.teamshit.Bean;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2016/8/25.
 */
public class UserBean {
    @Expose
    private long UserId;
    @Expose
    private String DisplayName;
    @Expose
    private String PortraitUri;

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getPortraitUri() {
        return PortraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        PortraitUri = portraitUri;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "UserId=" + UserId +
                ", DisplayName='" + DisplayName + '\'' +
                ", PortraitUri='" + PortraitUri + '\'' +
                '}';
    }
}
