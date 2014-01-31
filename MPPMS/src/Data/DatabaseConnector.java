package Data;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    private boolean success = false;
    
    public DatabaseConnector() {
        createConnection();
    }
    
    public ResultSet selectQuery(String queryString) {
        executeSelectStatement(queryString);        
        return results;
    }
    
    public boolean insertQuery(HashMap<String, String> attributesAndValues) {
        String fields = "";
        String values = "";
        String table = "";
        
        for (Map.Entry<String, String> attrVal : attributesAndValues.entrySet()) {
            if (attrVal.getKey().equals("TABLENAME")) {
                table = attrVal.getValue();
            }
            else {
                fields += attrVal.getKey() + ", ";
                values += attrVal.getValue() + ", ";
            }
        }

        String queryString = "";
        queryString += "INSERT INTO ";
        queryString += table;
        queryString += " ( ";
        queryString += fields.substring(0, fields.length() - 2);
        queryString += " ) VALUES ( ";
        queryString += values.substring(0, values.length() - 2);
        queryString += " )";

        executeInsertStatement(queryString);
            
        return success;
    }
    
    public boolean updateQuery(HashMap<String, String> attributesAndValues) {
        String assignments = "";
        String table = "";
        String where = "";
        
        for (Map.Entry<String, String> attrVal : attributesAndValues.entrySet()) {
            switch (attrVal.getKey()) {
                case "TABLENAME":
                    table = attrVal.getValue();
                    break;
                case "ID":
                    where = attrVal.getKey() + " = " + attrVal.getValue();
                    break;
                default:
                    assignments += attrVal.getKey() + " = " + attrVal.getValue() + ", ";
                    break;
            }
        }

        String queryString = "";
        queryString += "UPDATE ";
        queryString += table;
        queryString += " SET ";
        queryString += assignments.substring(0, assignments.length() - 2);
        queryString += " WHERE ";
        queryString += where;

        executeUpdateStatement(queryString);
            
        return success;
    }
    
    public boolean deleteAndInsertQuery(ArrayList<HashMap<String, Object>> attributesAndValues, String parentModelName) {
        boolean savedOK = true;
        
        for (HashMap<String, Object> hMap : attributesAndValues) {
            String table = (String)hMap.get("TABLENAME");
            String thisIdFieldName = parentModelName + "ID";
            String thisId = (String)hMap.get(thisIdFieldName);
            
            executeUpdateStatement("DELETE FROM " + table + " WHERE " + thisIdFieldName + " = " + thisId);
            savedOK &= success;
            
            for (Map.Entry<String, Object> attrVal : hMap.entrySet()) {
                if (!attrVal.getKey().equals("TABLENAME") && !attrVal.getKey().equals(thisIdFieldName)) {
                    for (String linkId : (ArrayList<String>)attrVal.getValue()) {
                        executeInsertStatement("INSERT INTO " + table + " VALUES ( " + thisId + ", " + linkId + " )");
                        savedOK &= success;
                    }
                }
            }
        }
        return savedOK;
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
    
    private void executeInsertStatement(String queryString) {
        try {
            statement = (Statement) connection.createStatement();
            statement.execute(queryString);
            success = true;
        } catch (SQLException ex) {
            success = false;
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void executeUpdateStatement(String queryString) {
        try {
            statement = (Statement) connection.createStatement();
            statement.executeUpdate(queryString);
            success = true;
        } catch (SQLException ex) {
            success = false;
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
