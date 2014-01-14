package Users;

/**
 * This class represents the type of User who works within the company and is
 * involved with the creation / development / publishing of Projects.
 * 
 * @author Craig Lewin (b0012760@my.shu.ac.uk)
 * @see User
 * @since 1.0
 */
public class StaffMember extends User
{
    protected String username = "";
    protected String password = "";
    
    /**
     * Constructor used when creating an instance of StaffMember.
     * @param username
     * @param password 
     * @param forename
     * @param surname
     */
    public StaffMember(String username, String password, String forename, String surname)
    {
        super(forename, surname);
        this.username = username;
        this.password = password;
    }
    
    /**
     * @return The login username for this StaffMember.
     */
    public String getUsername()
    {
        return this.username;
    }
    
    /**
     * Changes the username for this StaffMember.
     * @param newUsername The new username to use
     * @param currentPassword The password for this StaffMember - required
     * validation so that nobody but this StaffMember can make this change.
     * @return true if the current password was a match and the username was
     * successfully changed; false if the current password was incorrect and the 
     * username remains unchanged.
     */
    public boolean changeUsername(String newUsername, String currentPassword)
    {
        if (currentPassword.equals(this.password))
        {
            this.username = newUsername;
            return true;
        }
        return false;
    }
    
    /**
     * @return The login password for this StaffMember.
     */
    public String getPassword()
    {
        return this.password;
    }
    
    /**
     * Changes the password for this StaffMember.
     * @param newPassword The new password to use
     * @param currentPassword The password for this StaffMember - required
     * validation so that nobody but this StaffMember can make this change.
     * @return true if the current password was a match and the password was
     * successfully changed; false if the current password was incorrect and the 
     * password remains unchanged.
     */
    public boolean changePassword(String newPassword, String currentPassword)
    {
        if (currentPassword.equals(this.password))
        {
            this.password = newPassword;
            return true;
        }
        return false;
    }
}
