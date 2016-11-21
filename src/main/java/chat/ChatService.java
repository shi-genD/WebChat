package chat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shi on 12.11.16.
 */


public class ChatService {
    private Set<ChatWebSocket> webSockets;
    private static ChatService instance;

    public static synchronized ChatService getInstance() {
        if (instance == null) {
            instance = new ChatService();
        }
        return instance;
    }

    private ChatService() {
        this.webSockets = ConcurrentHashMap.newKeySet();
    }

    public void sendMessage(String data) {
        for (ChatWebSocket user : webSockets) {
            try {
                user.sendString(data);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void add(ChatWebSocket webSocket) {
        webSockets.add(webSocket);
    }

    public boolean containsSession(HttpSession httpSession) {
        for (ChatWebSocket webSocket : webSockets) {
            if (webSocket.getHttpSession().equals(httpSession)) {
                return true;
            }
        }
        return false;
    }

    public void remove(ChatWebSocket webSocket) {
        webSockets.remove(webSocket);
    }

    public JSONArray getOnlineList () throws JSONException {
        JSONArray onlineList = new JSONArray();
        for (ChatWebSocket ws : webSockets) {
            JSONObject jo = new JSONObject();
            jo.put("username", ws.getUserProfile().getUserName());
            jo.put("color", ws.getUserProfile().getColor());
            onlineList.put(jo);
        }
        return onlineList;
    }

}
