package Application;

import java.util.ArrayList;

public class AppTracker {
    private static AppTracker instance = null;
    
    private final ArrayList<String> loggedInUsers = new ArrayList();
    
    private AppTracker() {}
    
    public static AppTracker getInstance() {
        if (instance == null) {
            instance = new AppTracker();
        }
        return instance;
    }
    
    public void userLoggedIn(String username) {
        loggedInUsers.add(username);
    }
    
    public void userLoggedOut(String username) {
        loggedInUsers.remove(username);
    }
    
    public boolean isUserLoggedIn(String username) {
        return loggedInUsers.contains(username);
    }
}
