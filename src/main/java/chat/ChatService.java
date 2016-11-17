package chat;
import javax.servlet.http.HttpSession;
import java.util.Collections;
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

}
