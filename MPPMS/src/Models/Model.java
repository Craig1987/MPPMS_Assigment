package Models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract super class for any model (Task, Component etc.) which is part of this application.
 *
 * @author Craig
 */
public abstract class Model {
    
    /**
     * This abstract method prepares the object for saving and then make
     * a call to DatabaseConnector to update the database. If the save is successful,
     * a call to AppObservable will be made to update all observers.
     * 
     * @author Craig - TC B2c: Real time updates && TC B4: Persistence
     * @return true if saving to the database was successful, false if an error occurred.
     * @see DatabaseConnector
     * @see AppObservable
     */
    public abstract boolean save();
    
    /**
     * Populates and returns a HashMap of all fields and values required for modifying 
     * the database. Used by the save method.
     * 
     * @author Craig - TC B4: Persistence
     * @param includeId indicates whether or not the ID field / value is added to 
     * the HashMap (typically true for update queries and false for insert queries).
     * @return Hashmap of <String, String> (Database field name and value) for use in 
     * DatabaseConnector. 
     * @see #save()
     * @see DatabaseConnector
     */
    protected abstract HashMap<String, String> getAttributesAndValues(final boolean includeId);
    
    /**
     * Populates and returns HashMaps of fields and values for inner Models (e.g. 
     * Comments inside a Report). Used by the save method.
     * 
     * @author Craig - TC B4: Persistence
     * @return ArrayList of HashMaps of <String, Object> containing all necessary 
     * fields and values for updating database link tables.
     * @see #save()
     * @see DatabaseConnector
     */
    protected abstract ArrayList<HashMap<String, Object>> getInnerAttributesAndValues();
    
    /**
     * Utility method to wrap a string in single quotes (place a single quote both 
     * at the beginning and at the wend of the string).
     * 
     * @author Craig - TC B4: Persistence
     * @param str The string to surround with single quotes
     * @return The original string surrounded by single quotes.
     */
    protected String wrapInSingleQuotes(String str) {
        return "'" + str + "'";
    }
}
