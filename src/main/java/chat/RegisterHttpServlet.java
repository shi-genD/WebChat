package chat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shi on 12.11.16.
 */
public class RegisterHttpServlet extends HttpServlet {
    private UserService userProfiles;

    @Override
    public void init() throws ServletException {
        super.init();
        userProfiles = UserService.getInstance();
    }

    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        String username = req.getParameter("username");
        userProfiles.add(sessionId, username);
        resp.sendRedirect("chat.html");
    }*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        String username = req.getParameter("username");
        String color = req.getParameter("color");
        userProfiles.add(sessionId, new UserProfile(username, color));
        resp.sendRedirect("chat.html");
    }
}
