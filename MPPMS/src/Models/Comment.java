package Models;

import Data.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Comment {
    private static SetOfComments allComments = null;
    
    private final int id;
    
    private Date date;
    private User user;
    private String content;
    
    public Comment(int id, Date date, User user, String content) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.content = content;
    }
    
    @Override
    public String toString() {
        return new SimpleDateFormat("dd MMM yyy").format(date) + " by " + user.toString();
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
    
    private static void populateComments() {
        try {
            allComments = new SetOfComments();
            DatabaseConnector dbConn = new DatabaseConnector();
            ResultSet comments = dbConn.selectQuery("SELECT * FROM COMMENTS");
            
            while (comments.next()) {
                Comment comment = new Comment(comments.getInt("ID"), 
                                                new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(comments.getString("COMMENTDATE")), 
                                                User.getUserByUsername(comments.getString("USERNAME")), 
                                                comments.getString("CONTENT"));
                allComments.add(comment);
            }
            dbConn.dispose();
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
