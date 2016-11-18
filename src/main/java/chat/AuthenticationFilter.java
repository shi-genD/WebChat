package chat;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by shi on 15.11.16.
 */
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        if(session == null && (!(uri.endsWith("html") || uri.endsWith("LoginServlet")))){
            res.sendRedirect("login.html");
        } else if (session != null && !(ChatService.getInstance().containsSession(session)) && uri.endsWith("html")) {
            res.sendRedirect("chat.jsp");
        } else if (session != null && ChatService.getInstance().containsSession(session)
                                    && !(uri.endsWith("err.jsp")) && !(uri.endsWith("LogoutServlet"))) {
            res.sendRedirect("err.jsp");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() { }
}
