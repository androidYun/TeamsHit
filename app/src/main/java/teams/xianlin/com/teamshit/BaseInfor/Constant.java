package teams.xianlin.com.teamshit.BaseInfor;

import android.app.Activity;
import android.net.Uri;
import android.os.Environment;

import java.net.URI;

import teams.xianlin.com.teamshit.Utils.Encrypt;

/**
 * Created by Administrator on 2016/7/6.
 */
public class Constant {

    public final static String TEST = "http://www.api.mstching.com/api/";// 测试版的
    public final static String Formal = "http://www.api.mstching.com/api/";
    public final static String UpImageUrl = "http://www.api.mstching.com/uploadimg.aspx?savetype=";
    public static final String BASE_URL = TEST;
    public static final String Base_Foot_Url = "?token=";

    public static final String Md5_key = Encrypt.MD5("131139");

    public static final String Request_url = BASE_URL + "?md5=" + Md5_key;

    public static final String App_Id = "627515567417";

    public static final String Appsecret = "aQmo3zbxT6bhsmaZweCd";

    public static final URI socketUri = URI.create("ws://192.168.1.111:8181/");


    public static final int Camera_Code = 1001;//照相码

    public static final int Photo_Gallery_Code = 1002;//相册

    public static final int Crop_Photo_Code = 1003;//剪切图片

    public static final int Selector_Photo_Code = 1004;//用GinallFinal 选择图片

    public static final int Scan_Qr_Code = 1005;//扫描二维码

    /**
     * 错误码
     */


    public final static int Not_NetWork_error_Code = 10000;//网络为空
    public final static int Param_error_Code = 1002;
    public final static int Not_Complete_Infor_Code = 10003;//没有补全资料
    public final static int Update_Img_error = 10004;//上传图片失败


    /**
     * 常量
     */
    public final static String Not_NetWork_error = "请链接网络";//网络为空
    public final static String Load_NetWork_error = "链接服务器失败，请稍后再试";
    public final static String Thumb_Img_Url = ".w150.png";
    /**
     * 编码格式
     */
    public final static String ENCODING = "UTF-8";

    public final static String User_Info = "user_Info";
    /**
     * 加密格式
     */
    public static final String MD5_KEY = "vwBYVr6n";

    /**
     * SharePrefernece
     */
    public static final String Veriction_Code_Time = "Veriction_Code_Time";

    public static final String User_Token = "User_Token";//用户Token

    public static final String Rong_Token = "Rong_Token";//融云Token

    public static final String User_Pwd = "User_Pwd";//用户密码

    public static final String User_Account = "User_Account";//用户账号

    public static final String User_Nick_Name = "User_Nick_Name";//昵称

    public static final String User_PortraitUri = "User_PortraitUri";//用户头像

    public static final String User_Gender = "User_Gender";//用户性别

    public static final String User_City = "User_City";//地址

    public static final String User_BirthDate = "User_BirthDate";//用户生日

    public static final String User_Name = "";//用户名

    /*Intent  传值Key
    * */
    public final static String Intent_Result = "Intent_Result";//广播传送String类型的值
    public final static String Img_Path = "Img_Path";//图片传值Key
    public final static String ScanResult = "ScanResult";//Wifi参数
    public final static String WifiPwd = "WifiPwd";//Wifi密码
    public final static String Wifi_Ssid = "Wifi_Ssid";//wifi的Ssid
    public final static String Device_Mac = "Device_Mac";//设备Mac
    public final static String Scan_Title_Name = "Scan_Title_Name";//拍照标题
    public final static String Friend_Deatil_Bean = "Friend_Deatil_Bean";//好友详情实体类
    public final static String Group_List_Bean = "Group_List_Bean";//群列表数据
    public final static String Phone_Number = "Phone_Number";//手机号
    public final static String User_Id = "User_Id";//用户id
    public final static String Target_Id = "targetId";//用户id//因为融云传递都是targetId
    public final static String connection_title = "title";//聊天类型
    public final static String Friend_Detail_Type = "Friend_Detail_Type";//跳转到详情的类型，如果是1 则是传递的详情类，如果是2 则传递的是一个UserId，用于请求获取详情类
    public final static String Conversation_Type = "Conversation_Type";//会话类型
    public final static String DisPlay_Name = "Conversation_Type";//会话类型
    public final static String Favorite = "Favorite";//点赞
    public final static String Cancle_Favorite = "Cancle_Favorite";//取消点赞
    public final static String Comment = "Comment";//取消点赞
    public final static String Comment_Reply = "Comment_Reply";//取消点赞
    public final static String MemberIds = "MemberIds";//会员Id
    public final static String GroupId = "GroupId";//群组Id
    public final static String GroupBean = "GroupBean";//群对象
    /*BroadCast  中的Action标识
    *
    * */
    public static final String CHANGEINFO = "CHANGEINFO";//更改个人信息的常量标识


    /**
     * 融云系统消息类型
     */
    private static final String TAG = "ContactNotificationMessage";
    public static final String CONTACT_OPERATION_REQUEST = "Request";
    public static final String CONTACT_OPERATION_ACCEPT_RESPONSE = "AcceptResponse";
    public static final String CONTACT_OPERATION_REJECT_RESPONSE = "RejectResponse";
    public static final String Delete_OperaTion_Respose = "DelFriend";//删除好友通知
    public static final String Friend_Circle_Message_OperaTion_Respose = "LatestFriendCircleMessage";//朋友圈动态信息

    /**
     * File  路径
     */
    /**
     * 发送图片保存地址
     */
    public static final String FILE_IMG_SEND = Environment.getExternalStorageDirectory() + "/teamshit/images/send/";

    /**
     * 截取图片保存地址
     */
    public static final String FILE_IMG_SHOT = Environment.getExternalStorageDirectory() + "/teamshit/images/shot/";

    /**
     * 图片缓存地址
     */
    public static final String FILE_IMG_CACHE = Environment.getExternalStorageDirectory() + "/teamshit/images/cache/";
    /**
     * 拍照上传图片的缓存地址
     */
    public static final String FILE_IMG_CACHE_CACHE = Environment.getExternalStorageDirectory() + "/teamshit/images/cache/feedback/";
    /**
     * 语音缓存地址（发送）
     */
    public static final String FILE_VOICE_CACHE_SEND = Environment.getExternalStorageDirectory() + "/teamshit/voice/send/";

    /**
     * 语音缓存地址（接收）
     */
    public static final String FILE_VOICE_CACHE_RECEIVE = Environment.getExternalStorageDirectory() + "/teamshit/voice/receive/";

    /**
     * 文件下载保存地址
     */
    public static final String FILE_DOWNLOAD = Environment.getExternalStorageDirectory() + "/teamshit/download/";
    /**
     * 欢迎页图片
     */
    public static final String FILE_IMG_WELCOME = Environment.getExternalStorageDirectory() + "/teamshit/images/welcome_img";
    /**
     * 广告页图片
     */
    public static final String FILE_IMG_AD = Environment.getExternalStorageDirectory() + "/teamshit/images/ad_img";

    /**
     * 广播Action
     */
    public static final String teams_xianlin_com_teamshit_Activity_Action = "teams_xianlin_com_teamshit_Activity_Action";//当wifi链接成功之后发送广播

}
