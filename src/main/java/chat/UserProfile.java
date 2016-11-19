package chat;

import javax.servlet.http.HttpSession;

/**
 * Created by shi on 13.11.16.
 */
public class UserProfile {
    private String userName;
    private String color;
    private HttpSession httpSession;

    public UserProfile(String userName, String color, HttpSession httpSession) {
        this.userName = userName;
        this.color = color;
        this.httpSession = httpSession;
    }

    public String getUserName() {
        return userName;
    }

    public String getColor() {
        return color;
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfile that = (UserProfile) o;

        return httpSession.equals(that.httpSession);

    }

    @Override
    public int hashCode() {
        return httpSession.hashCode();
    }
}
