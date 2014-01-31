package Models;

import Application.AppObservable;
import Data.DatabaseConnector;
import Models.User.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
        Asset_Retrieval,
        QC,
        Inbound_QA,
        QA_Moderation,        
        Build
    }
    
    public enum Status {
        Created,
        Assigned,
        Assets_Ordered,
        Assets_Arrived,
        QC_In_Progress,
        QC_Completed,
        Moderation_In_Progress,
        In_Fixes,
        Delayed,
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
        this.status = Status.Created;
        this.priority = Priority.Normal;
        this.report = new Report(); // An empty report is always created
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
    
    public static void clearAndNullifyAll() {
        if (allTasks != null) {
            allTasks.clear();
            allTasks = null;
        }
    }
    
    @Override
    public boolean save() {
        this.report.setTitle("Report for Task " + Task.getNextAvailableID());
        boolean success = this.report.save();
        
        DatabaseConnector dbConn = new DatabaseConnector();
        
        if (this.id == 0) {
            this.id = Task.getNextAvailableID();
            success &= dbConn.insertQuery(getAttributesAndValues(false));
        }
        else {
            success &= dbConn.updateQuery(getAttributesAndValues(true));
        }
        
        success &= dbConn.deleteAndInsertQuery(getInnerAttributesAndValues(), "TASK");
        
        if (success) {
            AppObservable.getInstance().notifyObserversToRefresh();
        }
        
        return success;
    }

    @Override
    protected HashMap<String, String> getAttributesAndValues(final boolean includeId) {
        return new HashMap<String, String>() {{
            put("TABLENAME", "TASKS");
            if (includeId) put("ID", "" + getId());
            put("TITLE", wrapInSingleQuotes(getTitle()));
            put("TASKTYPE", wrapInSingleQuotes(getTaskType().toString()));
            put("STATUS", wrapInSingleQuotes(getStatus().toString()));
            put("PRIORITY", wrapInSingleQuotes(getPriority().toString()));
            put("REPORTID", "" + getReport().getId());
        }};
    }

    @Override
    protected ArrayList<HashMap<String, Object>> getInnerAttributesAndValues() {
        ArrayList<HashMap<String, Object>> attrVals = new ArrayList();
        attrVals.add(new HashMap<String, Object>() {{
            put("TABLENAME", "TASKASSIGNEDTO");
            put("TASKID", "" + getId());            
            ArrayList<String> usernames = new ArrayList();
            for (User user : getAssignedTo()) {
                usernames.add(wrapInSingleQuotes(user.getUsername()));
            }
            put("USERNAME", usernames);
        }});
        attrVals.add(new HashMap<String, Object>() {{
            put("TABLENAME", "TASKASSETS");
            put("TASKID", "" + getId());            
            ArrayList<String> assetIds = new ArrayList();
            for (Asset asset : getAssets()) {
                assetIds.add("" + asset.getId());
            }
            put("USERNAME", assetIds);
        }});
        return attrVals;
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
    
    private static int getNextAvailableID() {
        int greatestId = 0;
        for (Task task : getAllTasks()) {
            greatestId = Math.max(greatestId, task.getId());
        }
        return greatestId + 1;
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
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}