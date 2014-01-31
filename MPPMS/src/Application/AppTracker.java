package Application;

import java.util.ArrayList;

/**
 * This singleton class is used for keeping track of which Users are logged in to 
 * the system.
 */
public class AppTracker {
    private static AppTracker instance = null;
    
    private final ArrayList<String> loggedInUsers = new ArrayList();
    
    private AppTracker() {
        // Private constructor - Singletone pattern.
    }
    
    public static AppTracker getInstance() {
        if (instance == null) {
            instance = new AppTracker();
        }
        return instance;
    }
    
    /**
     * Indicate that a specific User has logged in to the system.
     *
     * @param username The username of the User who has just logged in.
     */
    public void userLoggedIn(String username) {
        loggedInUsers.add(username);
    }
    
    /**
     * Indicates that a specific User has just logged out of the system.
     *
     * @param username The username of the User who has just logged out.
     */
    public void userLoggedOut(String username) {
        loggedInUsers.remove(username);
    }
    
    /**
     * Checks if a User is already logged in to the system (and thus is not allowed 
     * to log in again).
     *
     * @param username The username of the User you wish to check is logged in or not.
     * @return true if the User is already logged in to the system, else false.
     */
    public boolean isUserLoggedIn(String username) {
        return loggedInUsers.contains(username);
    }
}
