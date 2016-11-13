package chat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shi on 12.11.16.
 */
public class UserProfiles {
    private Map<String, String> userprofile;
    private static UserProfiles instance;

    public static synchronized UserProfiles getInstance() {
        if (instance == null) {
            instance = new UserProfiles();
        }
        return instance;
    }
    private UserProfiles() {
        userprofile = new HashMap<>();
    }


    public void add(String session, String user) {
        userprofile.put(session, user);
    }


    public Map<String, String> getUserprofile() {
        return userprofile;
    }
}
