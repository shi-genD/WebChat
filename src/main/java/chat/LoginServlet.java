package chat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by shi on 12.11.16.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String color = req.getParameter("color");
        HttpSession session = req.getSession();

        session.setAttribute("userprofile", new UserProfile(username, color, session));
        session.setMaxInactiveInterval(30*60);

        Cookie userName = new Cookie("user", username);
        userName.setMaxAge(30*60);
        req.setCharacterEncoding("UTF-8");
        resp.addCookie(userName);
        System.out.println(username);
        resp.sendRedirect("chat.jsp");
    }
}
