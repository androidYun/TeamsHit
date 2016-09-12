package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/8/10.
 */
public class SearchGroupListResp extends PacketResp implements Serializable {

    @Expose
    public List<GroupList> GroupList;

    public List<SearchGroupListResp.GroupList> getGroupList() {
        return GroupList;
    }

    public void setGroupList(List<SearchGroupListResp.GroupList> groupList) {
        GroupList = groupList;
    }

    public class GroupList implements Serializable {

        @Expose
        String GroupName;
        @Expose
        String GroupIconUrl;
        @Expose
        String GroupNumber;
        @Expose
        String GroupPeople;
        @Expose
        String GroupIntro;

        public String getGroupIconUrl() {
            return GroupIconUrl;
        }

        public void setGroupIconUrl(String groupIconUrl) {
            GroupIconUrl = groupIconUrl;
        }

        public String getGroupNumber() {
            return GroupNumber;
        }

        public void setGroupNumber(String groupNumber) {
            GroupNumber = groupNumber;
        }

        public String getGroupPeople() {
            return GroupPeople;
        }

        public void setGroupPeople(String groupPeople) {
            GroupPeople = groupPeople;
        }

        public String getGroupIntro() {
            return GroupIntro;
        }

        public void setGroupIntro(String groupIntro) {
            GroupIntro = groupIntro;
        }

        public String getGroupName() {
            return GroupName;
        }

        public void setGroupName(String groupName) {
            GroupName = groupName;
        }
    }
}
