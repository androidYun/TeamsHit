package teams.xianlin.com.teamshit.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/24.
 */
public class SelectorGroupChatFriendBean implements Serializable {
    private String userId;//用户Id
    private String name;//昵称
    private String portraitUri;//头像
    private String displayName;//显示的名字
    private boolean isSlector;//是否选择
    private String letters;//字符

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean getSlector() {
        return isSlector;
    }

    public void setSlector(boolean slector) {
        isSlector = slector;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }
}
