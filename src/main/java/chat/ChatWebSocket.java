package chat;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Created by shi on 12.11.16.
 */


@SuppressWarnings("UnusedDeclaration")
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatWebSocket {
    private ChatService chatService;
    private UserProfile userProfile;
    private Session session;
    private HttpSession httpSession;

    public ChatWebSocket() {
        this.chatService = ChatService.getInstance();
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws JSONException {
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        chatService.add(this, httpSession);
        this.session = session;
        this.userProfile = (UserProfile) httpSession.getAttribute("userprofile");
        chatService.sendMessage(systemJsonMessage("enters the chat"));
    }

    @OnMessage
    public void onMessage(String data, Session session) throws JSONException {
        String name = userProfile.getUserName();
        String color = userProfile.getColor();
        JSONObject js = new JSONObject();
        js.put("type", "User");
        js.put("username", name);
        js.put("message", data);
        js.put("color", color);
        js.put("mtime", LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        chatService.sendMessage(js.toString());
    }

    @OnClose
    public void onClose(Session session) throws JSONException {
        chatService.remove(this, httpSession);
        chatService.sendMessage(systemJsonMessage("leaves the chat"));
    }

    public void sendString(String data) {
        try {
            session.getBasicRemote().sendText(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String systemJsonMessage(String action) throws JSONException {
        JSONObject js = new JSONObject();
        js.put("type", "System");
        js.put("mtime", LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        js.put("username", "System");
        js.put("color", "#BDBDBD");
        js.put("onlinelist", ChatService.getInstance().getOnlineList());
        js.put("message", userProfile.getUserName() + " " + action);
        return js.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatWebSocket that = (ChatWebSocket) o;
        return httpSession.equals(that.httpSession);
    }

    @Override
    public int hashCode() {
        return httpSession.hashCode();
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}
