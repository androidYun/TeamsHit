package teams.xianlin.com.teamshit.NetWorkResp;

import com.google.gson.annotations.Expose;

import teams.xianlin.com.teamshit.NetWork.PacketResp;

/**
 * Created by Administrator on 2016/8/27.
 */
public class PublishTakeResp extends PacketResp {

    @Expose
    private long TakeId;

    @Expose
    private long CreateTime;

    public long getTakeId() {
        return TakeId;
    }

    public void setTakeId(long takeId) {
        TakeId = takeId;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }
}
