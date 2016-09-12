package teams.xianlin.com.teamshit.BaseInfor;

/**
 * Created by Administrator on 2016/7/12.
 */
public class HttpSeviceClass {

    public final static String Verification_Code_Url = Constant.BASE_URL + "user/getVerificationCode?";//获取验证码

    public final static String Register_URL = Constant.BASE_URL + "user/register?";//注册

    public final static String Login_Url = Constant.BASE_URL + "user/login?";//登录

    public final static String Comlete_User_Infor_Url = Constant.BASE_URL + "userinfo/completeInformation" + Constant.Base_Foot_Url;//不全信息资料

    public final static String GET_FRIENDS_List_Url = Constant.BASE_URL + "userinfo/getFriendList" + Constant.Base_Foot_Url;//获取好友列表

    public final static String GET_Group_List_Url = Constant.BASE_URL + "userinfo/getGroups" + Constant.Base_Foot_Url;//获取群组列表

    public final static String Opeartion_Friend_List_Url = Constant.BASE_URL + "userinfo/operationFriend" + Constant.Base_Foot_Url;//操作好友

    public final static String Config_Wifi_Url = "http://192.168.10.1/sys/network";//配置wifi

    public final static String Get_Device_List_Url = Constant.BASE_URL + "userinfo/getDeviceList" + Constant.Base_Foot_Url;//获取队队机 设备列表

    public final static String Bind_Team_Hit_Url = Constant.BASE_URL + "userinfo/binddevice" + Constant.Base_Foot_Url;//绑定设备

    public final static String Indicator_Switch_URL = Constant.BASE_URL + "userinfo/IndicatorSwitch" + Constant.Base_Foot_Url;//指示灯开关

    public final static String Buzzer_Switch_Url = Constant.BASE_URL + "userinfo/BuzzerSwitch" + Constant.Base_Foot_Url;//绑定设备

    public final static String Edit_Team_Name_Url = Constant.BASE_URL + "userinfo/ModifyDeviceName" + Constant.Base_Foot_Url;//修改设备名称

    public final static String Search_Friend_Url = Constant.BASE_URL + "userinfo/SearchFriend" + Constant.Base_Foot_Url;//获取好友详情

    public final static String Search_GroupS_Url = Constant.BASE_URL + "userinfo/SearchGroup" + Constant.Base_Foot_Url;//获取群组列表

    public final static String Get_New_Friend_List_Url = Constant.BASE_URL + "userinfo/getNewFriendList" + Constant.Base_Foot_Url;//获取新朋友列表

    public final static String Get_Detail_User_Infor_Url = Constant.BASE_URL + "userinfo/getDetailInfor" + Constant.Base_Foot_Url;//获取好友详情

    public final static String Get_User_Infor_Url = Constant.BASE_URL + "userinfo/getUserInfor" + Constant.Base_Foot_Url;//获取好友信息

    public final static String Set_Display_Name_Url = Constant.BASE_URL + "userinfo/setDisplayName" + Constant.Base_Foot_Url;//修改备注信息

    public final static String Set_Target_Permission_Url = Constant.BASE_URL + "userinfo/setTargetPermission" + Constant.Base_Foot_Url;//修改不朋友圈权限

    public final static String Set_User_Permission_Url = Constant.BASE_URL + "userinfo/setUserPermission" + Constant.Base_Foot_Url;//设置朋友圈权限

    public final static String Get_Friend_Cirle_Permission_Url = Constant.BASE_URL + "userinfo/getPermission" + Constant.Base_Foot_Url;//修改好友朋友圈权限

    public final static String Get_Friend_Cirle_List_Url = Constant.BASE_URL + "news/GetFriendCircleList" + Constant.Base_Foot_Url;//获取朋友圈说说列表

    public final static String Friend_Circle_Thumbs_Url = Constant.BASE_URL + "news/thumbsUp" + Constant.Base_Foot_Url;//点赞

    public final static String Friend_Circle_Cancle_Thumbs_Url = Constant.BASE_URL + "news/canclethumbsUp" + Constant.Base_Foot_Url;//取消点赞

    public final static String Friend_Circle_Publish_Comment_Url = Constant.BASE_URL + "news/publishComment" + Constant.Base_Foot_Url;//评论

    public final static String Friend_Circle_Delete_Take_Url = Constant.BASE_URL + "news/deleteTake" + Constant.Base_Foot_Url;//删除评论

    public final static String Friend_Circle_Publish_Take_Url = Constant.BASE_URL + "news/publishTake" + Constant.Base_Foot_Url;//发布评论

    public final static String Friend_Circle_Delete_Comment_Url = Constant.BASE_URL + "news/deleteComment" + Constant.Base_Foot_Url;//删除评论

    public final static String GET_Friend_Circle_Message_List_Url = Constant.BASE_URL + "news/getFriendCircleMessage" + Constant.Base_Foot_Url;//删除朋友圈消息

    public final static String Delete_Friend_Circle_Message_List_Url = Constant.BASE_URL + "news/deleteFriendCircleMessage" + Constant.Base_Foot_Url;//清空朋友圈消息

    public final static String Create_Group_Url = Constant.BASE_URL + "groups/createGroup" + Constant.Base_Foot_Url;//创建群组

    public final static String Create_Group_Detail_Url = Constant.BASE_URL + "groups/getGroupDetail" + Constant.Base_Foot_Url;//获取群组详情
}