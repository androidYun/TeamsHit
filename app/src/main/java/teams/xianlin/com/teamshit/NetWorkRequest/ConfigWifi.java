package teams.xianlin.com.teamshit.NetWorkRequest;

import com.google.gson.annotations.Expose;

import teams.xianlin.com.teamshit.NetWork.Packet;

/**
 * Created by Administrator on 2016/8/2.
 */
public class ConfigWifi extends PacketRequest {
    @Expose
    private String ssid;
    @Expose
    private int security;
    @Expose
    private String key;
    @Expose
    private int ip;

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSecurity() {
        return security;
    }

    public void setSecurity(int security) {
        this.security = security;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
