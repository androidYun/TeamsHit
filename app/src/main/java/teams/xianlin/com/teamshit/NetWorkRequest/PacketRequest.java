package teams.xianlin.com.teamshit.NetWorkRequest;


import com.google.gson.annotations.Expose;

import teams.xianlin.com.teamshit.NetWork.Packet;

//请求类
@SuppressWarnings("serial")
public abstract class PacketRequest extends Packet {
    @Expose
    String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
