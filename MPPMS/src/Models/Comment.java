package Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
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
}
