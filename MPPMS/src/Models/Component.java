package Models;

import Application.AppObservable;
import Data.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Component extends Model {
    private static SetOfComponents allComponents = null;
    
    private SetOfAssets assets = new SetOfAssets();    
    private int id;
    private String description;
    
    public Component() {
        this.id = 0;
        this.description = "";
    }
    
    public Component(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getNumberOfAssets() {
        return assets.size();
    }
    
    public SetOfAssets getAssets() {
        return assets;
    }
    
    public void setAssets(SetOfAssets assets) {
        this.assets = assets;
    }
    
    public void addAsset(Asset asset) {
        this.assets.add(asset);
    }
    
    public static void clearAndNullifyAll() {
        if (allComponents != null) {
            allComponents.clear();
            allComponents = null;
        }
    }
    
    @Override
    public boolean save() {
        DatabaseConnector dbConn = new DatabaseConnector();
        boolean success;
        
        if (this.id == 0) {
            this.id = Component.getNextAvailableId();
            success = dbConn.insertQuery(getAttributesAndValues(false));
        }
        else {
            success = dbConn.updateQuery(getAttributesAndValues(true));
        }
        
        success &= dbConn.deleteAndInsertQuery(getInnerAttributesAndValues(), "COMPONENT");
                
        if (success) {
            AppObservable.getInstance().notifyObserversToRefresh();
        }
        
        return success;
    }

    @Override
    protected HashMap<String, String> getAttributesAndValues(final boolean includeId) {
        return new HashMap<String, String>() {{
            put("TABLENAME", "COMPONENTS");
            if (includeId) put("ID", "" + getId());
            put("DESCRIPTION", wrapInSingleQuotes(getDescription()));
        }};
    }
    
    @Override
    protected ArrayList<HashMap<String, Object>> getInnerAttributesAndValues() {
        ArrayList<HashMap<String, Object>> attrVals = new ArrayList();
        attrVals.add(new HashMap<String, Object>() {{
            put("TABLENAME", "COMPONENTASSETS");
            put("COMPONENTID", "" + getId());            
            ArrayList<String> assetIds = new ArrayList();
            for (Asset asset : getAssets()) {
                assetIds.add("" + asset.getId());
            }
            put("ASSETID", assetIds);
        }});
        return attrVals;
    }
    
    @Override
    public String toString() {
        return "(ID: " + getId() + ") " + getDescription();
    }
    
    public static SetOfComponents getAllComponents() {
        if (allComponents == null) {
            populateComponents();
        }
        return allComponents;
    }
    
    public static Component getComponentByID(int id) {
        for (Component component : getAllComponents()) {
            if (component.getId() == id) {
                return component;
            }
        }
        return null;
    }
    
    private static int getNextAvailableId() {
        int greatestId = 0;
        for (Component component : getAllComponents()) {
            greatestId = Math.max(greatestId, component.getId());
        }
        return greatestId + 1;
    }
    
    private static void populateComponents() {
        try {
            allComponents = new SetOfComponents();
            DatabaseConnector dbConn = new DatabaseConnector();
            ResultSet components = dbConn.selectQuery("SELECT * FROM COMPONENTS");
            
            while (components.next()) {
                Component component = new Component(components.getInt("ID"), components.getString("DESCRIPTION"));
                DatabaseConnector dbConn2 = new DatabaseConnector();
                ResultSet componentAssets = dbConn2.selectQuery("SELECT * FROM COMPONENTASSETS WHERE COMPONENTID = " + component.getId());
                
                while (componentAssets.next()) {
                    component.addAsset(Asset.getAssetByID(componentAssets.getInt("ASSETID")));
                }                
                dbConn2.dispose();
                
                allComponents.add(component);
            }                    
            dbConn.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Component.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}