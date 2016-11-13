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
    private UserProfiles userProfiles;

    public RegisterHttpServlet() {
        userProfiles = UserProfiles.getInstance();
        userProfiles.add("1", "one");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        String username = req.getParameter("username");
        userProfiles.add(sessionId, username);
    }
}
