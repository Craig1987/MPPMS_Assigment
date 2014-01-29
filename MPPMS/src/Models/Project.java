package Models;

import Application.AppObservable;
import Data.DatabaseConnector;
import Models.User.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Project {
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
    
    @Override
    public String toString() {
        return "(ID: " + getId() + ") " + getTitle();
    }
    
    public boolean save() {
        if (id == 0){
            id = getAllProjects().get(getAllProjects().size() - 1).getId();
        }

        // TODO: Implement XML Persistance of Project
        System.out.println("TODO: Implement XML Persistance of Project | Models/Project.java:146");
        
        if (allProjects != null) {
            allProjects.clear();
        }
        allProjects = null;
        
        AppObservable.getInstance().notifyObserversToRefresh();
        
        return false;
    }
    
    private static void populateProjects() {
        try {
            allProjects = new SetOfProjects();
            DatabaseConnector dbConn = new DatabaseConnector();
            ResultSet projects = dbConn.selectQuery("SELECT * FROM PROJECTS");
            
            while (projects.next()) {
                Project project = new Project(projects.getInt("ID"), new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(projects.getString("CREATIONDATE")));
                project.setClient(User.getUserByUsername(projects.getString("CLIENT")));
                project.setCoordinator(User.getUserByUsername(projects.getString("COORDINATOR")));
                project.setManager(User.getUserByUsername(projects.getString("MANAGER")));
                project.setDeadline(new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(projects.getString("DEADLINEDATE")));
                project.setPriority(Priority.valueOf(projects.getString("PRIORITY")));
                project.setTitle(projects.getString("TITLE"));
                
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
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(Asset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
