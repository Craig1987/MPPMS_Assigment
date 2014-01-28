package Models;

import Data.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User extends Model {
    private static SetOfUsers allUsers = null;
    
    private final String username;
    private final String password;
    private final String forename;
    private final String surname;
    private final String name;
    private final Role role;
    
    public enum Role {
        ProjectCoordinator,
        ProjectManager,
        QCTeamLeader,
        QCTeamMember,
        Client
    }
    
    public User(Role role, String username, String password, String forename, String surname, String name) {
        this.username = username;
        this.password = password;
        this.forename = (forename == null ? "" : forename);
        this.surname = (surname == null ? "" : surname);
        this.name = (name == null ? "" : name);
        this.role = role;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
    
    public String getName()
    {
        if (this.forename.isEmpty())
        {
            return this.name;
        }
        return String.format("%s %s", this.forename, this.surname);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    public static SetOfUsers getAllUsers() {
        if (allUsers == null) {
            populateUsers();
        }
        return allUsers;
    }
    
    public static User getUserByUsername(String username) {
        for (User user : getAllUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    public static SetOfUsers getUsersByRole(Role role) {
        SetOfUsers users = new SetOfUsers();
        for (User user : getAllUsers()) {
            if (user.getRole() == role) {
                users.add(user);
            }
        }
        return users;
    }
    
    public static boolean authenticate(String username, String password) {
        for (User user : getAllUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    private static void populateUsers() {
        try 
        {
            allUsers = new SetOfUsers();
            DatabaseConnector dbConn = DatabaseConnector.getInstance();
            ResultSet users = dbConn.selectQuery("SELECT * FROM USERS");
            while(users.next()) {
                User user = new User(Role.valueOf(users.getString("Role")),
                                    users.getString("Username"),
                                    users.getString("Password"),
                                    users.getString("Forename"),
                                    users.getString("Surname"),
                                    users.getString("Name"));
                allUsers.add(user);
            }
            dbConn.closeConnection();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
