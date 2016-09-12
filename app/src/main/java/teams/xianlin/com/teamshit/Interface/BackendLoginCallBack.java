package teams.xianlin.com.teamshit.Interface;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;

/**
 * Created by Administrator on 2016/7/16.
 */
public interface BackendLoginCallBack {
    void BackendLoginsuccess();

    void BackendLoginFail(ErrorMsg errorMsg);
}
