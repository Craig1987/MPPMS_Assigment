package Models;

import Application.AppObservable;
import Data.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Report {
    private static SetOfReports allReports = null;
    
    private int id;
    private SetOfComments comments;
    
    public Report(int id) {
        this.id = id;
        this.comments = new SetOfComments();
    }

    Report() {
        this.id = 0;
        this.comments = new SetOfComments();
    }
    
    public int getId() {
        return id;
    }
    
    public void addComment(Comment comment) {
        comments.add(comment);
    }
    
    public void removeComment(Comment comment) {
        comments.remove(comment);
    }
    
    @Override
    public String toString() {
        String result = this.comments.size() + " comments";        
        if (this.comments.size() > 0) {
            Date lastEditDate = this.comments.get(this.comments.size() - 1).getDate();
            result += " (last edited " + new SimpleDateFormat("dd MMM yyyy").format(lastEditDate) + ")";
        }
        return result;
    }
    
    public SetOfComments getComments() {
        return this.comments;
    }
    
    public void save() {
        if (id == 0){
            id = getAllReports().get(getAllReports().size() - 1).getId();
        }

        // TODO: Implement XML Persistance of Project
        System.out.println("TODO: Implement XML Persistance of Project | Models/Project.java:146");
        
        if (allReports != null) {
            allReports.clear();
        }
        allReports = null;
        AppObservable.getInstance().notifyObserversToRefresh();
    }
    
    public static SetOfReports getAllReports() {
        if (allReports == null) {
            populateReports();
        }
        return allReports;
    }
    
    public static Report getReportByID(int id) {
        for (Report report: getAllReports()) {
            if (report.getId() == id) {
                return report;
            }
        }
        return null;
    }
    
    private static void populateReports() {
        try {
            allReports = new SetOfReports();
            DatabaseConnector dbConn = new DatabaseConnector();
            ResultSet reports = dbConn.selectQuery("SELECT * FROM REPORTS");
            
            while (reports.next()) {
                Report report = new Report(reports.getInt("ID"));
                
                DatabaseConnector dbConn2 = new DatabaseConnector();
                ResultSet reportComments = dbConn2.selectQuery("SELECT * FROM REPORTCOMMENTS WHERE REPORTID = " + report.getId());
                
                while (reportComments.next()) {
                    report.addComment(Comment.getCommentByID(reportComments.getInt("COMMENTID")));
                }
                dbConn2.dispose();
                
                allReports.add(report);
            }
            dbConn.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
        
