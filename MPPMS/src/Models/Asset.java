package Models;

import Application.AppObservable;
import Data.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Asset extends Model {
    private static SetOfAssets allAssets = null;
    
    private int id;    
    private AssetType assetType;    
    private int length;
    private String description;
    
    public enum AssetType {
        Audio,
        Video,
        Subtitles,
        Menu
    }
    
    public Asset() {
        this.id = 0;
        this.length = 0;
        this.assetType = AssetType.Audio;
        this.description = "";
    }
    
    public Asset(int id, int length, AssetType assetType, String description) {
        this.id = id;
        this.length = length;
        this.assetType = assetType;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public String getLengthAsString() {
        return "" + this.length;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String desc) {
        this.description = desc;
    }

    public AssetType getAssetType() {
        return assetType;
    }
    
    public void setAssetType(AssetType type) {
        this.assetType = type;
    }
    
    @Override
    public boolean save() {
        DatabaseConnector dbConn = new DatabaseConnector();
        boolean success;
        
        if (this.id == 0) {
            this.id = Asset.getNextAvailableId();
            success = dbConn.insertQuery(getAttributesAndValues(false));
        }
        else {
            success = dbConn.updateQuery(getAttributesAndValues(true));
        }
             
        if (success) {
            if (allAssets != null) {
                allAssets.clear();
            }
            allAssets = null;
            AppObservable.getInstance().notifyObserversToRefresh();
        }
        
        return success;
    }
    
    @Override
    protected HashMap<String, String> getAttributesAndValues(final boolean includeId) {
        return new HashMap<String, String>() {{
            put("TABLENAME", "ASSETS");
            if (includeId) put("ID", "" + getId());
            put("ASSETTYPE", wrapInSingleQuotes(getAssetType().toString()));
            put("ASSETLENGTH", "" + getLength());
            put("DESCRIPTION", wrapInSingleQuotes(getDescription()));
        }};
    }

    @Override
    protected ArrayList<HashMap<String, Object>> getInnerAttributesAndValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return "(ID: " + getId() + ") " + getAssetType() + " asset (Length: " + getLengthAsString() + ")";
    }
    
    public static SetOfAssets getAllAssets() {
        if (allAssets == null) {
            populateAssets();
        }
        return allAssets;
    }
    
    public static Asset getAssetByID(int id) {
        for (Asset asset : getAllAssets()) {
            if (asset.getId() == id) {
                return asset;
            }
        }
        return null;
    }
    
    private static int getNextAvailableId() {
        int greatestId = 0;
        for (Asset asset : getAllAssets()) {
            greatestId = Math.max(greatestId, asset.getId());
        }
        return greatestId + 1;
    }
        
    private static void populateAssets() {
        try {
            allAssets = new SetOfAssets();
            DatabaseConnector dbConn = new DatabaseConnector();
            ResultSet assets = dbConn.selectQuery("SELECT * FROM ASSETS");
            
            while (assets.next()) {
                Asset asset = new Asset(assets.getInt("ID"), 
                                        assets.getInt("AssetLength"), 
                                        AssetType.valueOf(assets.getString("AssetType")), 
                                        assets.getString("Description"));
                allAssets.add(asset);
            }
            dbConn.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Asset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
