package teams.xianlin.com.teamshit.Bean;

import com.google.gson.annotations.Expose;

import java.util.UUID;

/**
 * Created by Administrator on 2016/8/2.
 */
public class DeviceDetailBean {
    @Expose
    private String Uuid;//Uuid设备的唯一标识
    @Expose
    String DeviceMac;//设备mac

    @Expose
    int Buzzer;//蜂鸣器开关

    @Expose
    int Indicator;//指示灯

    @Expose
    String DeviceName;//设备名称

    @Expose
    int State;//在线状态

    public String getDeviceMac() {
        return DeviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        DeviceMac = deviceMac;
    }

    public int getBuzzer() {
        return Buzzer;
    }

    public void setBuzzer(int buzzer) {
        Buzzer = buzzer;
    }

    public int getIndicator() {
        return Indicator;
    }

    public void setIndicator(int indicator) {
        Indicator = indicator;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String uuid) {
        Uuid = uuid;
    }
}
