package Models;

import XmlMapper.XmlSaver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Model {
    
    public boolean save() {
        ArrayList<Boolean> savedOK = new ArrayList<Boolean>(); // Store results of saves
        ArrayList<Model>   toSave  = getInnerObjects(); // Related objects contained within
        
        // Add this to be saved
        toSave.add(this);
        
        // Save each model
        for (Model m : toSave) {
            // Save via XML (this can be swapped out)
            boolean result = new XmlSaver(m.getXmlFilePath(), m.getSaveableAttributes()).save();
            savedOK.add(result);
        }
        
        // false if any model did not successfully save
        return ! savedOK.contains(false);
    }
    
    /**
     * 
     * @return String - The path to the XML file representing this object.
     */
    protected String getXmlFilePath() {
        return getClass()
                   .getClassLoader()
                   .getResource("Data/" + getClass().getSimpleName() + "s" + ".xml")
                   .getPath()
                   .replaceAll("%20", " ")
                   .replaceAll("build/classes", "src"); 
                   // Fix bug on ryan's machine where XML gets saved to Build
    }
    
    /**
     * 
     * This forms a schema for saving a Model to XML. 
     * 
     * This allows the XMLSaver to work dynamically for any Model.
     * 
     * The key of the map is the XML Node tag name (Case Sensitive).
     * The val of the map is the value of the node.
     * 
     * @return Map<String, String>
     */
    protected Map<String, String> getSaveableAttributes() {
        return new HashMap<>();
    }
    
    /**
     * Build an array of all dependant / related objects you wish to save along
     * with main object.
     * 
     * @return ArrayList<Object> the objects which are to be saved when the main
     *  object is saved.
     */
    protected ArrayList<Model> getInnerObjects() {
        return new ArrayList<>();
    }
}
