package Models;

import java.util.Date;

public class Comment {
    private final int id;
    private final Date date;
    private final User user;
    private final String content;
    
    public Comment(int id, Date date, User user, String content) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "ID: " + getId() + " - " + getContent();
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public User getUser() {
        return this.user;
    }
    
    public Date getDate() {
        return this.date;
    }
}
