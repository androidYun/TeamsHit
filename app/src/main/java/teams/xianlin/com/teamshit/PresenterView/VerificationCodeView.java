package teams.xianlin.com.teamshit.PresenterView;

import teams.xianlin.com.teamshit.Bean.ErrorMsg;
import teams.xianlin.com.teamshit.NetWorkResp.VerificationCodeResp;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface VerificationCodeView {
    void onLoadSucess(VerificationCodeResp verificationCodeResp);

    void onLoadFail(ErrorMsg errorMsg);
}
