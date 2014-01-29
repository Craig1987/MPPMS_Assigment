package Models;

import Application.AppObservable;
import Data.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    
    @Override
    public boolean save() {
        if (id == 0) {
            id = getAllComponents().get(getAllComponents().size() - 1).getId() + 1;
        }
            
        System.out.println("TODO: Implement persistence to XML | Models/Component.java:91");
        
        if (allComponents != null) {
            allComponents.clear();
        }
        allComponents = null;
        AppObservable.getInstance().notifyObserversToRefresh();
        
        return false;
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
            Logger.getLogger(Asset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}