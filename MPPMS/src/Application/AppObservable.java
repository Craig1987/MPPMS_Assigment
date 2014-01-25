package Application;

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
        setChanged();
        notifyObservers();
    }
}