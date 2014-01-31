package Application;

import Models.Asset;
import Models.Comment;
import Models.Component;
import Models.Project;
import Models.Report;
import Models.Task;
import Models.User;
import java.util.Observable;

/**
 * This singleton class is the core of the Observer pattern used throughout this
 * application. Controllers may register themselves as Observers of this class
 * and implement an Update method to update the data shown in their respective views.
 * 
 * @author Craig - TC B2c: Real time updates
 * @see java.util.Observable
 */
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
    
    /**
     * Clears all 'in-memory' data (which originates from the database) and then
     * notifies all observers (Controllers) to update their views.
     * 
     * @author Craig - TC B2c: Real time updates
     */
    public void notifyObserversToRefresh() {
        /**
         * 
         * Each model has a static 'SetOfModels' which contains all constructed 
         * objects using database content. Here they are cleared and hence forced
         * to query the database again the next time they are required. This is 
         * necessary because this method is called after the database has been 
         * modified in some way.
         */
        Asset.clearAndNullifyAll();
        Comment.clearAndNullifyAll();
        Component.clearAndNullifyAll();
        Project.clearAndNullifyAll();
        Report.clearAndNullifyAll();
        Task.clearAndNullifyAll();
        User.clearAndNullifyAll();
        
        // Indicate that a change has occurred and notify all observers.
        setChanged();
        notifyObservers();
    }
}