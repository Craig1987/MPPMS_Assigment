package Models;

import Application.AppObservable;
import Data.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Report extends Model {
    private static SetOfReports allReports = null;
    
    private int id;
    private SetOfComments comments;
    private String title;
    
    public Report(int id) {
        this.id = id;
        this.comments = new SetOfComments();
    }

    public Report() {
        this.id = 0;
        this.comments = new SetOfComments();
        this.title = "";
    }
    
    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void addComment(Comment comment) {
        if (comment != null) {
            comments.add(comment);
        }
    }
    
    @Override
    public String toString() {
        // Kirsty - displays number of comments and last edit date, or 'blank report' if there are no comments
        String result;       
        if (this.comments.size() > 0) {
            result = this.comments.size() + " comment" + (this.comments.size() > 1 ? "s" : ""); 
            Date lastEditDate = this.comments.get(this.comments.size() - 1).getDate();
            result += " (last edited " + new SimpleDateFormat("dd MMM yyyy").format(lastEditDate) + ")";
        }
        else
            result = "Blank report";
        return result;
    }
    
    public SetOfComments getComments() {
        return this.comments;
    }
    
    public static void clearAndNullifyAll() {
        if (allReports != null) {
            allReports.clear();
            allReports = null;
        }
    }
    
    @Override
    public boolean save() {
        boolean success = true;
        
        for (Comment comment : getComments()) {
            success &= comment.save();
        }
        
        DatabaseConnector dbConn = new DatabaseConnector();
        
        if (this.id == 0){
            this.id = Report.getNextAvailableID();
            success &= dbConn.insertQuery(getAttributesAndValues(false));
        }
        else {
            success &= dbConn.updateQuery(getAttributesAndValues(true));
        }
        
        success &= dbConn.deleteAndInsertQuery(getInnerAttributesAndValues(), "REPORT");
        
        if (success) {
            /*
            Craig - Database content has changed so we tell the Subject to
            update its Observers
            */
            AppObservable.getInstance().notifyObserversToRefresh();
        }
        
        return success;
    }

    @Override
    protected HashMap<String, String> getAttributesAndValues(final boolean includeId) {
        return new HashMap<String, String>() {{
            put("TABLENAME", "REPORTS");
            if (includeId) put("ID", "" + getId());
            put("TITLE", wrapInSingleQuotes(getTitle()));
        }};
    }

    @Override
    protected ArrayList<HashMap<String, Object>> getInnerAttributesAndValues() {
        ArrayList<HashMap<String, Object>> attrVals = new ArrayList();
        attrVals.add(new HashMap<String, Object>() {{
            put("TABLENAME", "REPORTCOMMENTS");
            put("REPORTID", "" + getId());            
            ArrayList<String> comments = new ArrayList();
            for (Comment comment : getComments()) {
                comments.add("" + comment.getId());
            }
            put("COMMENTID", comments);
        }});
        return attrVals;
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
    
    private static int getNextAvailableID() {
        int greatestId = 0;
        for (Report report : getAllReports()) {
            greatestId = Math.max(greatestId, report.getId());
        }
        return greatestId + 1;
    }
    
    private static void populateReports() {
        try {
            allReports = new SetOfReports();
            DatabaseConnector dbConn = new DatabaseConnector();
            ResultSet reports = dbConn.selectQuery("SELECT * FROM REPORTS");
            
            while (reports.next()) {
                Report report = new Report(reports.getInt("ID"));
                report.setTitle(reports.getString("TITLE"));
                
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
        
