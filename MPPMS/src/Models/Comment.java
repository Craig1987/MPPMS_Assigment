package Models;

import Application.AppObservable;
import Data.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Comment extends Model {
    private static SetOfComments allComments = null;
    
    private int id;
    
    private Date date;
    private User user;
    private String content;
    
    public Comment() {
        this.id = 0;
        this.date = new Date();
        this.user = new User(User.Role.QCTeamMember, "", "", "", "", "");
        this.content = "";
    }
    
    public Comment(int id, Date date, User user, String content) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.content = content;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }    
    
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public static void clearAndNullifyAll() {
        if (allComments != null) {
            allComments.clear();
            allComments = null;
        }
    }

    @Override
    public boolean save() {
        DatabaseConnector dbConn = new DatabaseConnector();
        boolean success;
        
        if (this.id == 0) {
            this.id = Comment.getNextAvailableID();
            success = dbConn.insertQuery(getAttributesAndValues(false));
        }
        else {
            success = dbConn.updateQuery(getAttributesAndValues(true));
        }
            
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
            put("TABLENAME", "COMMENTS");
            if (includeId) put("ID", "" + getId());
            put("USERNAME", wrapInSingleQuotes(getUser().getUsername()));
            put("COMMENTDATE", wrapInSingleQuotes(new SimpleDateFormat("dd MMM yyyy").format(getDate())));
            put("CONTENT", wrapInSingleQuotes(getContent()));
        }};
    }

    @Override
    protected ArrayList<HashMap<String, Object>> getInnerAttributesAndValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return new SimpleDateFormat("dd MMM yyy").format(date) + " by " + user.toString();
    }
    
    public static SetOfComments getAllComments() {
        if (allComments == null) {
            populateComments();
        }
        return allComments;
    }
    
    public static Comment getCommentByID(int commentId) {
        for (Comment comment : getAllComments()) {
            if (comment.getId() == commentId) {
                return comment;
            }
        }
        return null;
    }
    
    private static int getNextAvailableID() {
        int greatestId = 0;
        for (Comment comment : getAllComments()) {
            greatestId = Math.max(greatestId, comment.getId());
        }
        return greatestId + 1;
    }
    
    private static void populateComments() {
        try {
            allComments = new SetOfComments();
            DatabaseConnector dbConn = new DatabaseConnector();
            ResultSet comments = dbConn.selectQuery("SELECT * FROM COMMENTS");
            
            while (comments.next()) {
                Comment comment;
                try {
                    comment = new Comment(comments.getInt("ID"), 
                                            new SimpleDateFormat("dd MMM yyyy").parse(comments.getString("COMMENTDATE")),
                                            User.getUserByUsername(comments.getString("USERNAME")),
                                            comments.getString("CONTENT"));
                    allComments.add(comment);
                } catch (ParseException ex) {
                    Logger.getLogger(Comment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            dbConn.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Comment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
