package chat;

/**
 * Created by shi on 13.11.16.
 */
public class UserProfile {
    private String userName;
    private String color;

    public UserProfile(String userName, String color) {
        this.userName = userName;
        this.color = color;
    }

    public String getUserName() {
        return userName;
    }

    public String getColor() {
        return color;
    }
}
