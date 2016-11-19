package chat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shi on 12.11.16.
 */


public class ChatService {
    private Set<ChatWebSocket> webSockets;
    private Set<HttpSession> httpSessions;
    private static ChatService instance;

    public static synchronized ChatService getInstance() {
        if (instance == null) {
            instance = new ChatService();
        }
        return instance;
    }

    private ChatService() {
        this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.httpSessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
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

    public void add(ChatWebSocket webSocket, HttpSession httpSession) {
        webSockets.add(webSocket);
        httpSessions.add(httpSession);
    }

    public boolean containsSession(HttpSession httpSession) {
        return httpSessions.contains(httpSession);
    }

    public void remove(ChatWebSocket webSocket, HttpSession httpSession) {
        webSockets.remove(webSocket);
        httpSessions.remove(httpSession);
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
