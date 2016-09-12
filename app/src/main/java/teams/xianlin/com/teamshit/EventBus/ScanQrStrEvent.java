package teams.xianlin.com.teamshit.EventBus;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ScanQrStrEvent {
    String resultStr;

    public ScanQrStrEvent(String resultStr) {
        this.resultStr = resultStr;
    }

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }
}
