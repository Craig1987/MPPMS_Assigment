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
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    
    public ResultSet selectQuery(String queryString) {
        createConnection();
        executeSelectStatement(queryString);        
        return results;
    }
    
    private void createConnection() {
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String path = getClass()
                        .getClassLoader()
                        .getResource("Data/MPPMSDatabase")
                        .getPath()
                        .replaceAll("%20", " ")
                        .replaceAll("build/classes", "src");
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void executeSelectStatement(String queryString) {
        try {
            statement = (Statement) connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            results = statement.executeQuery(queryString);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dispose() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
