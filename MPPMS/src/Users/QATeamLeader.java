package Users;

/**
 * This class represents the type of StaffMember responsible for moderating 
 * completed QATasks.
 * 
 * @author Craig Lewin (b0012760@my.shu.ac.uk)
 * @see StaffMember
 * @since 1.0
 */
public class QATeamLeader extends StaffMember
{
    public QATeamLeader(String username, String password, String forename, String surname)
    {
        super(username, password, forename, surname);
    }
}