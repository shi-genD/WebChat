package chat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shi on 12.11.16.
 */
public class UserService {
    private Map<String, UserProfile> userService;
    private static UserService instance;

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    private UserService() {
        userService = new HashMap<>();
    }


    public void add(String session, UserProfile user) {
        userService.put(session, user);
    }

    public UserProfile getUser(String session) {
        return userService.get(session);
    }

}
