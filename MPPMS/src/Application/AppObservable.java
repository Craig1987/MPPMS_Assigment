package Application;

import Models.Asset;
import Models.Comment;
import Models.Component;
import Models.Project;
import Models.Report;
import Models.Task;
import Models.User;
import java.util.Observable;

public class AppObservable extends Observable
{
    private static AppObservable instance = null;
    
    public static AppObservable getInstance() {
        if (instance == null) {
            instance = new AppObservable();
        }
        return instance;
    }
    
    private AppObservable() {
        // Private constructor - Singleton pattern.
    }
    
    public void notifyObserversToRefresh() {
        Asset.clearAndNullifyAll();
        Comment.clearAndNullifyAll();
        Component.clearAndNullifyAll();
        Project.clearAndNullifyAll();
        Report.clearAndNullifyAll();
        Task.clearAndNullifyAll();
        User.clearAndNullifyAll();
        
        setChanged();
        notifyObservers();
    }
}