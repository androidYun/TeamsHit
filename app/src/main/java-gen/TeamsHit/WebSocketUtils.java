package TeamsHit;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import io.rong.eventbus.EventBus;
import teams.xianlin.com.teamshit.EventBus.SocketMessageEvent;
import teams.xianlin.com.teamshit.NetWork.SocketPacketResp;
import teams.xianlin.com.teamshit.Utils.JsonUtils.JsonMananger;
import teams.xianlin.com.teamshit.Utils.LogUtil;

/**
 * Created by Administrator on 2016/9/11.
 */
public class WebSocketUtils extends WebSocketClient {
    public WebSocketUtils(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public WebSocketUtils(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        LogUtil.d("开流--opened connection");
    }

    @Override
    public void onMessage(String message) {
        LogUtil.d("接收--received: " + message);
        SocketPacketResp jsonToBean=null;
        try {
            jsonToBean = JsonMananger.jsonToBean(message, SocketPacketResp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(jsonToBean!=null){
            EventBus.getDefault().post(new SocketMessageEvent(jsonToBean.getGameCommend(),message));
        }

    }

    @Override
    public void onFragment(Framedata fragment) {
        LogUtil.d("片段--received fragment: " + new String(fragment.getPayloadData().array()));
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LogUtil.d("关流--Connection closed by " + (remote ? "remote peer" : "us"));
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        LogUtil.d("出错--Error Exception");
    }

    /*
    链接服务器
     */
    public boolean setConnectBlocking(WebSocketUtils socketUtils) throws InterruptedException {
        boolean blocking = false;
        if (socketUtils != null) {
            blocking = socketUtils.connectBlocking();
        }

        return blocking;
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WebSocketUtils c = new WebSocketUtils(URI.create("ws://192.168.1.111:8181/"), new Draft_17());
                    boolean blocking = c.connectBlocking();
                    LogUtil.d("设置链接" + blocking);
                    c.send("测试--handshake");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
