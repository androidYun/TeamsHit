package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import java.util.List;

import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/9/7.
 */
public class GetGroupDetailResp extends PacketResp {

    @Expose
    private int GroupType;//1是吹牛 2，是21点
    @Expose
    private int VerificationType;//1是允许任何人加入，2 不允许任何人加入
    @Expose
    private long GroupId;//群Id
    @Expose
    private String GroupName;//群名称
    @Expose
    private String PortraitUri;//群头像
    @Expose
    private int Number;//目前人数
    @Expose
    private int MaxNumber;//最大人数
    @Expose
    private int Role;//是否是群主 0  是群主 1  不是
    @Expose
    private Boolean IsDismiss;//时候已经解散了
    @Expose
    private int CreatorId;//群主Id
    @Expose
    private String Introduce;//群简介
    @Expose
    private int LeastCoins;//最少碰碰壁

    @Expose
    private int GamePeople;//游戏人数
    @Expose
    private List<UserBean> FriendList;//好友列表

    public int getLeastCoins() {
        return LeastCoins;
    }

    public void setLeastCoins(int leastCoins) {
        LeastCoins = leastCoins;
    }

    public List<UserBean> getFriendList() {
        return FriendList;
    }

    public void setFriendList(List<UserBean> friendList) {
        FriendList = friendList;
    }

    public int getGroupType() {
        return GroupType;
    }

    public void setGroupType(int groupType) {
        GroupType = groupType;
    }

    public int getVerificationType() {
        return VerificationType;
    }

    public void setVerificationType(int verificationType) {
        VerificationType = verificationType;
    }

    public long getGroupId() {
        return GroupId;
    }

    public void setGroupId(long groupId) {
        GroupId = groupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getPortraitUri() {
        return PortraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        PortraitUri = portraitUri;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public int getMaxNumber() {
        return MaxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        MaxNumber = maxNumber;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    public Boolean getDismiss() {
        return IsDismiss;
    }

    public void setDismiss(Boolean dismiss) {
        IsDismiss = dismiss;
    }

    public int getCreatorId() {
        return CreatorId;
    }

    public void setCreatorId(int creatorId) {
        CreatorId = creatorId;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public int getGamePeople() {
        return GamePeople;
    }

    public void setGamePeople(int gamePeople) {
        GamePeople = gamePeople;
    }
}
