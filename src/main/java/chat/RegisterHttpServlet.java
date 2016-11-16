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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String color = req.getParameter("color");
        req.getSession().setAttribute("userprofile", new UserProfile(username, color));
        resp.sendRedirect("chat.html");
    }
}
