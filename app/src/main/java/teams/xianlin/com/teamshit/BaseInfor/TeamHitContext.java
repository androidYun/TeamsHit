package teams.xianlin.com.teamshit.BaseInfor;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Map;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import teams.xianlin.com.teamshit.Bean.UserBean;
import teams.xianlin.com.teamshit.Bean.UserInforBean;
import teams.xianlin.com.teamshit.R;
import teams.xianlin.com.teamshit.Utils.GlideImageLoader;
import teams.xianlin.com.teamshit.Utils.LogUtil;
import teams.xianlin.com.teamshit.Utils.SPUtils;
import teams.xianlin.com.teamshit.Utils.TextUtils.StringUtils;
import teams.xianlin.com.teamshit.Utils.UILPauseOnScrollListener;

/**
 * Created by Administrator on 2016/7/11.
 */
public class TeamHitContext {
    private static TeamHitContext teamHitContext;

    private static Context mContext;

    private CoreConfig coreConfig = null;//图片处理配置

    private static FunctionConfig functionConfig;//图片配置

    public String mRongToken;//融云Toekn

    public String mUserToken;//用户Token

    private String mAccount;//账号

    private String mPwd;//密码

    private Long UserId;

    public static Map<String, Long> map;//定时器变量保存


    private UserInforBean userInforBean;//好友

    private UserBean userBean;

    public static void initial(Context conetxt) {
        teamHitContext = new TeamHitContext(conetxt);
    }

    public static TeamHitContext getInstance() {
        if (teamHitContext == null) {
            teamHitContext = new TeamHitContext();
        }
        return teamHitContext;
    }

    public TeamHitContext(Context mContext) {
        this.mContext = mContext;
        initialGalleryFinal();
        FileUtils.initFiles();//创建文件夹
    }

    public TeamHitContext() {
    }

    private void initialGalleryFinal() {

        //配置功能
        ThemeConfig theme = new ThemeConfig.Builder().setTitleBarBgColor(mContext.getResources().getColor(R.color.selector_bg_color))
                .setCropControlColor(mContext.getResources().getColor(R.color.selector_bg_color))
                .build();
        //配置功能
        functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(false)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(8)
                .build();
        coreConfig = new CoreConfig.Builder(mContext, new GlideImageLoader(), theme)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new UILPauseOnScrollListener(false, true))
                .build();
        GalleryFinal.init(coreConfig);
    }

    public static FunctionConfig getFunctionConfig() {
        return functionConfig;
    }

    public static FunctionConfig getCropFunctionConfig() {
        FunctionConfig cropFunctionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropHeight(150)
                .setCropWidth(150)
                .setForceCrop(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(1)
                .setCropSquare(false)
                .build();
        return cropFunctionConfig;
    }

    public static void getGelideConfig() {
        LayoutInflater.from(mContext).inflate(R.layout.list_item_adapter_photo, null);
    }

    /**
     * 缓存图片的显示图片
     *
     * @param context
     * @param imageUrl
     * @param imageView
     */
    public static void with(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context).load(imageUrl).into(imageView);
    }

    /**
     * 拼接url
     */
    public static String getBaseUrl(String baseUrl) {
        String Appid = Constant.App_Id;
        String appSecrest = Constant.Appsecret;
        String timestamp = StringUtils.getCurrentTime();
        String nonce = StringUtils.getRandom();
        String signature = StringUtils.getMd5String(appSecrest, timestamp, nonce).toLowerCase();
        String url = baseUrl + "appid=" + Appid + "&timestamp=" + timestamp + "&nonce=" + nonce + "&signature=" + signature;
        return url.toLowerCase();
    }

    public String getTokenUrl(String baseUrl) {
        return baseUrl + getUserToken();
    }

    /**
     * 获取融云Token
     *
     * @return
     */
    public String getRongToken() {
        if (StringUtils.isBlank(mRongToken)) {
            mRongToken = (String) SPUtils.get(mContext, Constant.Rong_Token, "");
        }
        return mRongToken;
    }

    public void setmRongToken(String mRongToken) {
        this.mRongToken = mRongToken;
    }

    /**
     * 获取用户Token
     *
     * @return
     */
    public String getUserToken() {
        if (StringUtils.isBlank(mRongToken)) {
            mUserToken = (String) SPUtils.get(mContext, Constant.User_Token, " ");
        }
        return mUserToken;
    }

    public void setmUserToken(String mUserToken) {
        this.mUserToken = mUserToken;
    }

    public static TeamHitContext getTeamHitContext() {
        return teamHitContext;
    }

    public static void setTeamHitContext(TeamHitContext teamHitContext) {
        TeamHitContext.teamHitContext = teamHitContext;
    }

    public String getmPwd() {
        return mPwd;
    }

    public void setmPwd(String mPwd) {
        this.mPwd = mPwd;
    }

    public String getmAccount() {
        return mAccount;
    }

    public void setmAccount(String mAccount) {
        this.mAccount = mAccount;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public UserInforBean getUserInforBean() {
        if (userInforBean == null) {
            String nickName = (String) SPUtils.get(mContext, Constant.User_Nick_Name, "");
            String userPorUri = (String) SPUtils.get(mContext, Constant.User_PortraitUri, "");
            long userId = (Long) SPUtils.get(mContext, Constant.User_Id, Long.valueOf("0"));
            String userName = (String) SPUtils.get(mContext, Constant.User_Name, "");
            String user_BirDate = (String) SPUtils.get(mContext, Constant.User_BirthDate, "");
            String userCity = (String) SPUtils.get(mContext, Constant.User_City, "");
            String userGender = (String) SPUtils.get(mContext, Constant.User_Gender, "");
            userInforBean = new UserInforBean(userId, nickName, userPorUri, userName, userGender, userCity, user_BirDate);
        }
        return userInforBean;
    }

    public void putUserInforBean(UserInforBean userInforBean) {
        SPUtils.put(mContext, Constant.User_Nick_Name, userInforBean.getNickname());
        SPUtils.put(mContext, Constant.User_PortraitUri, userInforBean.getPortraitUri());
        SPUtils.put(mContext, Constant.User_Id, userInforBean.getUserId());
        SPUtils.put(mContext, Constant.User_Name, userInforBean.getUserName());
        SPUtils.put(mContext, Constant.User_BirthDate, userInforBean.getBirthDate());
        SPUtils.put(mContext, Constant.User_City, userInforBean.getCity());
    }

    public UserBean getUserBean() {
        if (userBean == null) {
            String nickName = (String) SPUtils.get(mContext, Constant.User_Nick_Name, "");
            String userPorUri = (String) SPUtils.get(mContext, Constant.User_PortraitUri, "");
            long userId = (Long) SPUtils.get(mContext, Constant.User_Id, Long.valueOf("0"));
            userBean.setUserId(userId);
            userBean.setDisplayName(nickName);
            userBean.setPortraitUri(userPorUri);
        }
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
