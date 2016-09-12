package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import java.util.List;

import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/7/16.
 */
public class GetGroupListResp extends PacketResp {
    @Expose
    private List<Group> GroupList;

    public List<GetGroupListResp.Group> getGroupList() {
        return GroupList;
    }

    public void setGroupList(List<GetGroupListResp.Group> groupList) {
        GroupList = groupList;
    }

    public class Group {
        @Expose
        String GroupId;
        @Expose
        String GroupName;
        @Expose
        String PortraitUri;
        @Expose
        int Number;
        @Expose
        int MaxNumber;
        @Expose
        String Introduce;
        @Expose
        String CreatorId;
        @Expose
        long CreatorTime;
        @Expose
        int Role;//0 是群主  1  是群员
        @Expose
        boolean IsDismiss;
        @Expose
        private int GroupType;

        public String getGroupId() {
            return GroupId;
        }

        public void setGroupId(String groupId) {
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

        public String getIntroduce() {
            return Introduce;
        }

        public void setIntroduce(String introduce) {
            Introduce = introduce;
        }

        public String getCreatorId() {
            return CreatorId;
        }

        public void setCreatorId(String creatorId) {
            CreatorId = creatorId;
        }

        public long getCreatorTime() {
            return CreatorTime;
        }

        public void setCreatorTime(long creatorTime) {
            CreatorTime = creatorTime;
        }

        public int getRole() {
            return Role;
        }

        public void setRole(int role) {
            Role = role;
        }

        public boolean isDismiss() {
            return IsDismiss;
        }

        public void setDismiss(boolean dismiss) {
            IsDismiss = dismiss;
        }

        public int getGroupType() {
            return GroupType;
        }

        public void setGroupType(int groupType) {
            GroupType = groupType;
        }
    }
}
