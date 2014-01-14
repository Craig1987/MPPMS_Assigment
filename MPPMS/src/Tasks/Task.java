package Tasks;

import Users.StaffMember;

public abstract class Task
{
    protected String title;
    protected StaffMember assignedTo;
    private int priority;
    protected boolean isComplete;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public StaffMember getAssignedTo()
    {
        return assignedTo;
    }

    public void setAssignedTo(StaffMember assignedTo)
    {
        this.assignedTo = assignedTo;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public boolean isIsComplete()
    {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete)
    {
        this.isComplete = isComplete;
    }
}
