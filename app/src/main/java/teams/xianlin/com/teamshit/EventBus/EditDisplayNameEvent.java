package teams.xianlin.com.teamshit.EventBus;

/**
 * Created by Administrator on 2016/8/19.
 */
public class EditDisplayNameEvent {

    private String DisplayName;

    public EditDisplayNameEvent(String displayName) {
        DisplayName = displayName;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }
}
