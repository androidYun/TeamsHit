package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/7/12.
 */
public class VerificationCodeResp extends PacketResp {

    @Expose
    private String Verifycode;

    public String getVerifynode() {
        return Verifycode;
    }

    public void setVerifynode(String Verifycode) {
        Verifycode = Verifycode;
    }
}
