package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

/**
 * Created by AMing on 16/2/24.
 * Company RongCloud
 */
public class ContactNotificationMessageData {
    @Expose
    private long TargetId;
    @Expose
    private String NickName;//昵称
    @Expose
    private String PortraitUri;//头像

    public long getTargetId() {
        return TargetId;
    }

    public void setTargetId(long targetId) {
        TargetId = targetId;
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
}
