package teams.xianlin.com.teamshit.EventBus;

/**
 * Created by Administrator on 2016/9/11.
 */
public class SocketMessageEvent {

    public final static int Socket_Close = 1;//长连接断开

    public final static int Socket_Message = 2;//推送消息
    private int GameCommand;//消息类型

    private String Message;


    public SocketMessageEvent(String message) {
        Message = message;
    }

    public SocketMessageEvent(int gameCommand, String message) {
        GameCommand = gameCommand;
        Message = message;
    }

    public int getGameCommand() {
        return GameCommand;
    }

    public void setGameCommand(int gameCommand) {
        GameCommand = gameCommand;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
