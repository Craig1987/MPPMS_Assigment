package Users;

//import Projects.SetOfProjects;

import Projects.SetOfProjects;


/**
 * This is the superclass for all representations of persons within the system 
 * {StaffMembers and Clients).
 * 
 * @author Craig Lewin (b0012760@my.shu.ac.uk)
 * @since 1.0
 */
public class User
{
    protected String forename = "";
    protected String surname = "";
    protected String name = "";
    protected SetOfProjects projects = new SetOfProjects();
    protected boolean canCreateProject = false;
    
    //protected SetOfProjects currentProjects = new SetOfProjects();
    
    /**
     * Constructor for a User with a singular name (Client).
     * @param name 
     */
    public User(String name)
    {
        this.setName(name);
    }
    
    /**
     * Constructor for a user with a forename and surname (StaffMember - called
     * only from within the StaffMember constructor).
     * @param forename
     * @param surname 
     */
    protected User(String forename, String surname)
    {
        this.setName(forename, surname);
    }

    /**
     * Gets the User's name.
     * @return "Forename Surname" if setName(String, String) was used
     * or "Name" if setName(String) was used.
     */
    public String getName()
    {
        if (this.forename.isEmpty())
        {
            return this.name;
        }
        return String.format("%s %s", this.forename, this.surname);
    }

    /**
     * Sets the User's singular name.
     * @param name The singular name for this User.
     */
    public final void setName(String name)
    {
        this.name = name;
        this.forename = "";
        this.surname = "";
    }
    
    /**
     * Sets the User's forename and surname.
     * @param forename 
     * @param surname 
     */
    public final void setName(String forename, String surname)
    {
        this.forename = forename;
        this.surname = surname;
    }
    
    public boolean getCanCreateProject()
    {
        return this.canCreateProject;
    }
    
    public SetOfProjects getProjects()
    {
        return this.projects;
    }
    
    public void setProjects(SetOfProjects p)
    {
        this.projects = p;
    }
    
    @Override
    public String toString()
    {
        return this.getName();
    }
}
