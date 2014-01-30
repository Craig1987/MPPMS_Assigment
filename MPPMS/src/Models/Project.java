package Models;

import Application.AppObservable;
import Data.DatabaseConnector;
import Models.User.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Project extends Model {
    private static SetOfProjects allProjects = null;
    
    private final Date creationDate;
    
    private int id;
    private String title;    
    private User manager;
    private User coordinator;
    private SetOfUsers team = new SetOfUsers();
    private Date deadline;
    private Priority priority;
    private SetOfTasks tasks = new SetOfTasks();
    private SetOfComponents components = new SetOfComponents();
    private User client;
    
    public enum Priority {
        Highest,
        High,
        Normal,
        Low,
        Lowest
    }
    
    public Project(int id, Date creationDate) {
        this.id = id;
        this.creationDate = creationDate;
    }
    
    public Project() {
        this.id = 0;
        this.manager = new User(User.Role.ProjectManager, "", "", "", "", "");
        this.coordinator = new User(User.Role.ProjectCoordinator, "", "", "", "", "");
        this.creationDate = new Date();
        this.deadline = new Date(this.creationDate.getTime() + (1000 * 60 * 60 * 24) * 7);
        this.priority = Priority.Normal;
        this.client = new User(Role.Client, "", "", "", "", "");
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Date getCreationDate() {
        return creationDate;
    }
    
    public String getFormattedCreationDate() {
        return new SimpleDateFormat("dd MMM yyyy").format(this.creationDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public User getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(User coordinator) {
        this.coordinator = coordinator;
    }

    public SetOfUsers getTeam() {
        return team;
    }

    public void setTeam(SetOfUsers team) {
        this.team = team;
    }
    
    public void addTeamMember(User user) {
        this.team.add(user);
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public SetOfTasks getTasks() {
        return tasks;
    }

    public void setTasks(SetOfTasks tasks) {
        this.tasks = tasks;
    }
    
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public SetOfComponents getComponents() {
        return components;
    }

    public void setComponents(SetOfComponents components) {
        this.components = components;
    }
    
    public void addComponent(Component component) {
        this.components.add(component);
    }
    
    public static void clearAndNullifyAll() {
        if (allProjects != null) {
            allProjects.clear();
            allProjects = null;
        }
    }
    
    @Override
    public boolean save() {
        DatabaseConnector dbConn = new DatabaseConnector();
        boolean success;
        
        if (this.id == 0) {
            this.id = Project.getNextAvailableID();
            success = dbConn.insertQuery(getAttributesAndValues(false));
        }
        else {
            success = dbConn.updateQuery(getAttributesAndValues(true));
        }
        
        success &= dbConn.deleteAndInsertQuery(getInnerAttributesAndValues(), "PROJECT");
        
        if (success) {
            AppObservable.getInstance().notifyObserversToRefresh();
        }
        
        return success;
    }

    @Override
    protected HashMap<String, String> getAttributesAndValues(final boolean includeId) {
        return new HashMap<String, String>() {{
            put("TABLENAME", "PROJECTS");
            if (includeId) put("ID", "" + getId());
            put("TITLE", wrapInSingleQuotes(getTitle()));
            put("CREATIONDATE", wrapInSingleQuotes(new SimpleDateFormat("dd MMM yyyy").format(getCreationDate())));
            put("DEADLINEDATE", wrapInSingleQuotes(new SimpleDateFormat("dd MMM yyyy").format(getDeadline())));
            put("PRIORITY", wrapInSingleQuotes(getPriority().toString()));
            put("MANAGER", "" + wrapInSingleQuotes(getManager().getUsername()));
            put("COORDINATOR", wrapInSingleQuotes(getCoordinator().getUsername()));
            put("CLIENT", wrapInSingleQuotes(getClient().getUsername()));
        }};
    }

    @Override
    protected ArrayList<HashMap<String, Object>> getInnerAttributesAndValues() {
        ArrayList<HashMap<String, Object>> attrVals = new ArrayList();
        attrVals.add(new HashMap<String, Object>() {{
            put("TABLENAME", "PROJECTTEAM");
            put("PROJECTID", "" + getId());            
            ArrayList<String> usernames = new ArrayList();
            for (User user : getTeam()) {
                usernames.add(wrapInSingleQuotes(user.getUsername()));
            }
            put("USERNAME", usernames);
        }});
        attrVals.add(new HashMap<String, Object>() {{
            put("TABLENAME", "PROJECTTASKS");
            put("PROJECTID", "" + getId());            
            ArrayList<String> taskIds = new ArrayList();
            for (Task task : getTasks()) {
                taskIds.add("" + task.getId());
            }
            put("TASKID", taskIds);
        }});
        attrVals.add(new HashMap<String, Object>() {{
            put("TABLENAME", "PROJECTCOMPONENTS");
            put("PROJECTID", "" + getId());            
            ArrayList<String> componentIds = new ArrayList();
            for (Component component : getComponents()) {
                componentIds.add("" + component.getId());
            }
            put("COMPONENTID", componentIds);
        }});
        return attrVals;
    }
    
    @Override
    public String toString() {
        return "(ID: " + getId() + ") " + getTitle();
    }
    
    public static SetOfProjects getAllProjects() {
        if (allProjects == null) {
            populateProjects();
        }
        return allProjects;
    }
    
    public static SetOfProjects getProjectsForUser(User user) {
        SetOfProjects projects = new SetOfProjects();
        for (Project project : getAllProjects()) {
            if (user.getRole() == Role.ProjectManager || project.getManager() == user 
                    || project.getCoordinator() == user || project.getTeam().contains(user)) {
                projects.add(project);
            }
        }
        return projects;
    }
    
    public static Project getProjectById(int id) {
        for (Project project : getAllProjects()) {
            if (project.getId() == id) {
                return project;
            }
        }
        return null;
    }
    
    private static int getNextAvailableID() {
        int greatestId = 0;
        for (Project project : getAllProjects()) {
            greatestId = Math.max(greatestId, project.getId());
        }
        return greatestId + 1;
    }
    
    private static void populateProjects() {
        try {
            allProjects = new SetOfProjects();
            DatabaseConnector dbConn = new DatabaseConnector();
            ResultSet projects = dbConn.selectQuery("SELECT * FROM PROJECTS");
            
            while (projects.next()) {
                Project project = null;
                try {
                    project = new Project(projects.getInt("ID"), new SimpleDateFormat("dd MMM yyyy").parse(projects.getString("CREATIONDATE")));
                    project.setClient(User.getUserByUsername(projects.getString("CLIENT")));
                    project.setCoordinator(User.getUserByUsername(projects.getString("COORDINATOR")));
                    project.setManager(User.getUserByUsername(projects.getString("MANAGER")));
                    project.setDeadline(new SimpleDateFormat("dd MMM yyyy").parse(projects.getString("DEADLINEDATE")));
                    project.setPriority(Priority.valueOf(projects.getString("PRIORITY")));
                    project.setTitle(projects.getString("TITLE"));
                } catch (ParseException ex) {
                    Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                DatabaseConnector dbConn2 = new DatabaseConnector();
                ResultSet projectTeam = dbConn2.selectQuery("SELECT * FROM PROJECTTEAM WHERE PROJECTID = " + project.getId());
                
                while (projectTeam.next()) {
                    project.addTeamMember(User.getUserByUsername(projectTeam.getString("USERNAME")));
                }
                dbConn2.dispose();
                
                DatabaseConnector dbConn3 = new DatabaseConnector();
                ResultSet projectTasks = dbConn3.selectQuery("SELECT * FROM PROJECTTASKS WHERE PROJECTID = " + project.getId());
                
                while (projectTasks.next()) {
                    project.addTask(Task.getTaskByID(projectTasks.getInt("TASKID")));
                }
                dbConn3.dispose();
                
                DatabaseConnector dbConn4 = new DatabaseConnector();
                ResultSet projectComponents = dbConn4.selectQuery("SELECT * FROM PROJECTCOMPONENTS WHERE PROJECTID = " + project.getId());
                
                while (projectComponents.next()) {
                    project.addComponent(Component.getComponentByID(projectComponents.getInt("COMPONENTID")));
                }
                dbConn4.dispose();
                
                allProjects.add(project);
            }                    
            dbConn.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
