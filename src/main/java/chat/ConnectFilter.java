package chat;


import org.apache.tomcat.websocket.server.WsFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by shi on 15.11.16.
 */
public class ConnectFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        if (ChatService.getInstance().containsSession(session)){
            System.out.println("forward /err.html");
        } else if (servletRequest.getAttribute("user")!=null) {
            System.out.println("forward /chat.html");
            servletRequest.getRequestDispatcher("chat.html").forward(servletRequest, servletResponse);
        } else {
            System.out.println("forward /index.html");
            servletRequest.getRequestDispatcher("index.html").forward(servletRequest, servletResponse);
        }
    }


    @Override
    public void destroy() {

    }
}
