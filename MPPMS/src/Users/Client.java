package Users;

/**
 * This class represents the type of User for whom a Project will be for. A 
 * Client may be assigned to multiple Projects.
 * 
 * @author Craig Lewin (b0012760@my.shu.ac.uk)
 * @see User
 * @since 1.0
 */
public class Client extends User
{
    public Client(String name)
    {
       super(name); 
    }
    public Client(String forename, String surname)
    {
       super(forename, surname); 
    }
    
}
