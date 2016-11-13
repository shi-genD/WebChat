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
    private Session session;
    private HttpSession httpSession;

    public ChatWebSocket() {
        this.chatService = ChatService.getInstance();
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        chatService.add(this);
        this.session = session;
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());

    }

    @OnMessage
    public void onMessage(String data, Session session) {
        String name = UserService.getInstance().getUser(httpSession.getId()).getUserName();
        String color = UserService.getInstance().getUser(httpSession.getId()).getColor();
        chatService.sendMessage(name+data+ " ["+color+"]");
    }

    @OnClose
    public void onClose(Session session) {
        chatService.remove(this);
    }

    public void sendString(String data) {
        try {
            session.getBasicRemote().sendText(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
