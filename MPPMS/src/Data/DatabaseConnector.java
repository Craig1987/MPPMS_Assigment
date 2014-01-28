package Data;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Craig
 */
public class DatabaseConnector {
    private static DatabaseConnector instance = null;
    
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    
    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }
    
    private DatabaseConnector() { }
    
    public ResultSet selectQuery(String queryString) {
        createConnection();
        executeSelectStatement(queryString);
        
        return results;
    }
    
    private void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/MPPMSDataStore;create=true;user=sa;password=password");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void executeSelectStatement(String queryString) {
        try {
            statement = (Statement) connection.createStatement();
            results = statement.executeQuery(queryString);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
