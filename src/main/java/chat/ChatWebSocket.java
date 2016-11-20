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
    public void onOpen(Session session, EndpointConfig config) {
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.session = session;
        this.userProfile = (UserProfile) httpSession.getAttribute("userprofile");
        chatService.add(this, httpSession);
        try {
            final String action = "присоединяется к чату";
            final String jsonMessage = systemJsonMessage(action);
            chatService.sendMessage(jsonMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String data, Session session) {
        try {
            final String jsonMessage = userJsonMessage(data);
            chatService.sendMessage(jsonMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        chatService.remove(this, httpSession);
        try {
            final String action = "покидает чат";
            final String jsonMessage = systemJsonMessage(action);
            chatService.sendMessage(jsonMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void sendString(String data) {
        try {
            session.getBasicRemote().sendText(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String systemJsonMessage(String action) throws JSONException {
        JSONObject js = new JSONObject();
        js.put("type", "System");
        js.put("mtime", LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        js.put("username", "System");
        js.put("color", "#BDBDBD");
        js.put("onlinelist", ChatService.getInstance().getOnlineList());
        js.put("message", userProfile.getUserName() + " " + action);
        return js.toString();
    }

    private String userJsonMessage(String data) throws JSONException {
        String name = userProfile.getUserName();
        String color = userProfile.getColor();
        JSONObject js = new JSONObject();
        js.put("type", "User");
        js.put("username", name);
        js.put("message", data);
        js.put("color", color);
        js.put("mtime", LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
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
