package teams.xianlin.com.teamshit.NetWork;

import com.google.gson.annotations.Expose;

//相应类
@SuppressWarnings("serial")
public abstract class PacketResp extends Packet {


    @Expose
    private int Code = 1;// 返回码
    @Expose
    private String Message;

    public PacketResp() {
        Command = 0;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
