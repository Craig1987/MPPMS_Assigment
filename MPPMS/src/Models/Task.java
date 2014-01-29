package Models;

import Application.AppObservable;
import Data.DatabaseConnector;
import Models.User.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Task extends Model {
    private static SetOfTasks allTasks = null;
    
    private int id;
    private TaskType taskType;    
    private String title;
    private SetOfUsers assignedTo = new SetOfUsers();
    private SetOfAssets assets = new SetOfAssets();
    private Status status;
    private Priority priority;
    private Report report;
    
    public enum TaskType {
        QA,
        QC,
        Build
    }
    
    public enum Status {
        New,
        Assigned,
        In_Progress,
        Completed
    }
    public enum Priority {
        Highest,
        High,
        Normal,
        Low,
        Lowest
    }
    
    public Task() {
        this.id = 0;
        this.taskType = TaskType.QC;
        this.title = "";
        this.status = Status.New;
        this.priority = Priority.Normal;
        this.report = new Report();
    }

    
    public Task(int id, TaskType taskType) {
        this.id = id;
        this.taskType = taskType;
    }
    
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public TaskType getTaskType() {
        return this.taskType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SetOfUsers getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(SetOfUsers assignedTo) {
        this.assignedTo = assignedTo;
    }
    
    public void addAssignedTo(User user) {
        this.assignedTo.add(user);
    }
    
    public SetOfAssets getAssets(){
        return assets;
    }
    
    public void setAssets(SetOfAssets assets){
        this.assets = assets;
    }
    
    public void addAsset(Asset asset) {
        this.assets.add(asset);
    }

    public Priority getPriority() {
        return priority;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
    
    @Override
    public String toString() {
        return "(ID: " + getId() + ") " + getTaskType() + " Task - " + getTitle();
    }
    
    public static SetOfTasks getAllTasks() {
        if (allTasks == null) {
            populateTasks();
        }
        return allTasks;
    }
    
    public static Task getTaskByID(int id) {
        for (Task task : getAllTasks()) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
    
    public static SetOfTasks getTasksForUser(User user) {
        SetOfTasks tasks = new SetOfTasks();
        for (Task task : getAllTasks()) {
            if (task.getAssignedTo().contains(user) || user.getRole() == Role.ProjectManager || user.getRole() == Role.ProjectCoordinator) {
                tasks.add(task);
            }
        }
        return tasks;
    }
    
    public static SetOfTasks getTasksByStatus(Status status) {
        SetOfTasks tasks = new SetOfTasks();
        for (Task task : getAllTasks()) {
            if (task.getStatus() == status) {
                tasks.add(task);
            }
        }
        return tasks;
    }
    
    @Override
    public boolean save() {
        if (id == 0) {
            id = getAllTasks().get(getAllTasks().size() - 1).getId() + 1;
        }
        
        
        
        System.out.println("TODO: Implement persistance to XML | Models/Task.java:136" + id + " " + title);
        
        if (allTasks != null) {
            allTasks.clear();
        }
        allTasks = null;
        AppObservable.getInstance().notifyObserversToRefresh();
        
        return false;
    }

    
    private static void populateTasks() {
        try {
            allTasks = new SetOfTasks();
            DatabaseConnector dbConn = new DatabaseConnector();
            ResultSet tasks = dbConn.selectQuery("SELECT * FROM TASKS");
            
            while (tasks.next()) {
                Task task = new Task(tasks.getInt("ID"), TaskType.valueOf(tasks.getString("TASKTYPE")));
                task.setPriority(Priority.valueOf(tasks.getString("PRIORITY")));
                task.setReport(Report.getReportByID(tasks.getInt("REPORTID")));
                task.setStatus(Status.valueOf(tasks.getString("STATUS")));
                task.setTaskType(TaskType.valueOf(tasks.getString("TASKTYPE")));
                task.setTitle(tasks.getString("TITLE"));
                
                DatabaseConnector dbConn2 = new DatabaseConnector();
                ResultSet taskAssignedTo = dbConn2.selectQuery("SELECT * FROM TASKASSIGNEDTO WHERE TASKID = " + task.getId());
                
                while (taskAssignedTo.next()) {
                    task.addAssignedTo(User.getUserByUsername(taskAssignedTo.getString("USERNAME")));
                }
                dbConn2.dispose();
                
                DatabaseConnector dbConn3 = new DatabaseConnector();
                ResultSet taskAssets = dbConn3.selectQuery("SELECT * FROM TASKASSETS WHERE TASKID = " + task.getId());
                
                while (taskAssets.next()) {
                    task.addAsset(Asset.getAssetByID(taskAssets.getInt("ASSETID")));
                }
                dbConn3.dispose();
                
                allTasks.add(task);
            }                    
            dbConn.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Asset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}