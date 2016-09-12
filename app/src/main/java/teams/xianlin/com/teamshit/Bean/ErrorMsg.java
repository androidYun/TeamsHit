package teams.xianlin.com.teamshit.Bean;

/**
 * Created by Administrator on 2016/7/6.
 */
public class ErrorMsg {

    private int respCode;

    private String errorMsg;

    private int ErrorCode;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }
}
