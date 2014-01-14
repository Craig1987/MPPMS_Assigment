package Users;

/**
 * This class represents the type of StaffMember responsible for creating and 
 * assigning projects and tasks. He/she would also deal with assigning 
 * responsibilities.
 * 
 * @author Craig Lewin (b0012760@my.shu.ac.uk)
 * @see StaffMember
 * @since 1.0
 */
public class ProjectManager extends StaffMember
{
    public ProjectManager(String username, String password, String forename, String surname)
    {
        super(username, password, forename, surname);
        this.canCreateProject = true;
    }
}