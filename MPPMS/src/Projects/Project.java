package Projects;

import Components.SetOfComponents;
import Tasks.SetOfTasks;
import Users.Client;
import Users.SetOfUsers;
import Users.StaffMember;
import Users.User;
import java.util.Date;

public class Project
{
    final private String title;
    final private Date creationDate;
    final private User createdBy;
    private SetOfTasks tasks;
    private SetOfUsers staff;
    private SetOfComponents components;
    private StaffMember coordinator;
    private int priority;
    private Client client;
    private Date deadline;
    private String authoringTechnology;
    private String navigationRequirements;
    private String projectNotes;
    private String assesNotes;
    private boolean isCostAcceptedByClient;
    
    public Project(String title, Date creationDate, User createdBy)
    {
        this.title = title;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
    }
    
    public String getTitle()
    {
        return this.title;
    }
    
    public Date getCreationDate()
    {
        return this.creationDate;
    }
    
    public User getCreatedBy()
    {
        return this.createdBy;
    }
}
