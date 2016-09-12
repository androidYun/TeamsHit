package teams.xianlin.com.teamshit.Bean;

/**
 * Created by Administrator on 2016/7/6.
 */
public class Param {
    public Param(String phoneNumber) {
    }

    public Param(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String key;
    public Object value;

}
