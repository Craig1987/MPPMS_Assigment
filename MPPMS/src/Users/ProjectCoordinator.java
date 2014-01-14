package Users;

/**
 * This class represents the type of StaffMember responsible for supervising the
 * projects and ensuring that project tasks are prioritised.
 * 
 * @author Craig Lewin (b0012760@my.shu.ac.uk)
 * @see StaffMember
 * @since 1.0
 */
public class ProjectCoordinator extends StaffMember
{
    public ProjectCoordinator(String username, String password, String forename, String surname)
    {
        super(username, password, forename, surname);
    }
}