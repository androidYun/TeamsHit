package teams.xianlin.com.teamshit.Utils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import teams.xianlin.com.teamshit.NetWork.Packet;
import teams.xianlin.com.teamshit.NetWork.SocketPacket;
import teams.xianlin.com.teamshit.Utils.JsonUtils.JsonMananger;

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

    public void sendMessage(SocketPacket packet) {
        String requestStr = "";//请求数据
        try {
            requestStr = JsonMananger.beanToJson(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.d("你看看就知道了啊"+requestStr);
        this.send(requestStr);
    }

    /*
    链接服务器
     */
    public boolean setConnectBlocking(WebSocketUtils socketUtils) throws InterruptedException {
        boolean blocking = false;
        if (socketUtils != null) {
            blocking = socketUtils.connectBlocking();
        }
        LogUtil.d("设置链接" + blocking);
        return blocking;
    }
//       public static void main(String[] args) {
//    	    try {
//           	WebSocketUtils c = new WebSocketUtils( new URI( "ws://tourace-api.iriding.cc/socket.io/?transport=websocket" ), new Draft_17() );   
//   			c.connectBlocking();
//   			c.send("测试--handshake");  
//   			} catch (InterruptedException e) {
//   			e.printStackTrace();
//   			} catch (URISyntaxException e) {
//   			e.printStackTrace();
//   			} 
//       }

}  