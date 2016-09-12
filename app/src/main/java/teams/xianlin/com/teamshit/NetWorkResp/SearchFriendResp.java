package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/8/9.
 */
public class SearchFriendResp extends PacketResp implements Serializable {
    @Expose
    private String IconUrl;//头像
    @Expose
    private String NickName;//昵称
    @Expose
    private Long UserId;//用户Id
    @Expose
    private String DisplayName;//备注
    @Expose
    private String Phone;//手机号
    @Expose
    private String Address;//地址
    @Expose
    private boolean IsFriend;//是否为好友
    @Expose
    private List<GalleryUrl> GalleryList;

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public boolean isFriend() {
        return IsFriend;
    }

    public void setFriend(boolean friend) {
        IsFriend = friend;
    }

    public List<GalleryUrl> getGalleryList() {
        return GalleryList;
    }

    public void setGalleryList(List<GalleryUrl> galleryList) {
        GalleryList = galleryList;
    }

    public String getIconUrl() {
        return IconUrl;
    }

    public void setIconUrl(String iconUrl) {
        IconUrl = iconUrl;
    }

    public class GalleryUrl implements Serializable {
        @Expose
        private String ImgUrl;

        public String getImgUrl() {
            return ImgUrl;
        }

        public void setImgUrl(String imgUrl) {
            ImgUrl = imgUrl;
        }
    }
}
