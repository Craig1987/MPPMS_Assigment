package Models;

import java.util.Date;

public class Comment {
    private final Date date;
    private final User user;
    private final String content;
    
    public Comment(Date date, User user, String content) {
        this.date = date;
        this.user = user;
        this.content = content;
    }
}
