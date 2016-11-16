package chat;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

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
    public void onOpen(Session session, EndpointConfig config){
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        chatService.add(this, httpSession);
        this.session = session;
        this.userProfile = (UserProfile) httpSession.getAttribute("userprofile");
        chatService.sendMessage("System: Welcome user " + userProfile.getUserName());
    }

    @OnMessage
    public void onMessage(String data, Session session) {
        String name = userProfile.getUserName();
        String color = userProfile.getColor();
        chatService.sendMessage(name + data + " [" + color + "]");
    }

    @OnClose
    public void onClose(Session session) {
        chatService.sendMessage("System: Bye user " + userProfile.getUserName());
        chatService.remove(this, httpSession);
    }

    public void sendString(String data) {
        try {
            session.getBasicRemote().sendText(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
}
