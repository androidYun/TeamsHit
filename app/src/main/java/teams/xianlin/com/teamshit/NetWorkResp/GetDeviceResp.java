package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import java.util.List;

import teams.xianlin.com.teamshit.Bean.DeviceDetailBean;
import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/8/2.
 */
public class GetDeviceResp extends PacketResp {

    @Expose

    private List<DeviceDetailBean> DeviceList;

    public List<DeviceDetailBean> getDeviceList() {
        return DeviceList;
    }

    public void setDeviceList(List<DeviceDetailBean> deviceList) {
        DeviceList = deviceList;
    }
}
