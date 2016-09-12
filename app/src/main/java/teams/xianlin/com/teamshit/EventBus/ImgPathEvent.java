package teams.xianlin.com.teamshit.EventBus;

/**
 * Created by Administrator on 2016/7/27.
 */
public class ImgPathEvent {
    private String imgPath;

    public ImgPathEvent(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
