package Models;

import XmlMapper.XmlSaver;
import Application.AppObservable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
    
    public void save() {
        if (id == 0) {
            id = getAllAssets().get(getAllAssets().size() - 1).getId() + 1;
        }
        
        System.out.println("TODO: Implement persistence to XML | Models/Asset.java:81");
        
        if (allAssets != null) {
            allAssets.clear();
        }
        allAssets = null;
        AppObservable.getInstance().notifyObserversToRefresh();
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
    
    public boolean save() {
        if (id == 0)
            id = getAllAssets().get(getAllAssets().size() - 1).getId() + 1;
        
        return new XmlSaver(getXmlFilePath(), getSaveableAttributes()).save();
    }
    
    /**
     * 
     * @return String - The path to the XML file representing this object.
     */
    private String getXmlFilePath() {
        return getClass()
                   .getClassLoader()
                   .getResource("Data/Assets.xml")
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
    private Map getSaveableAttributes() {
        return new HashMap<String, String>() {{
            put("Node",        "Asset");
            put("SaveBy",      "ID");
            put("ID",          "" + id);
            put("Type",        "" + assetType.name());
            put("Length",      "" + length);
            put("Description", "" + description);
        }};
    }
    
    private static void populateAssets() {
        try 
        {
            allAssets = new SetOfAssets();
            String pathToFile = User.class.getResource("/Data/Assets.xml").getPath();
            pathToFile = pathToFile.replaceAll("%20", " ");
            File fXmlFile = new File(pathToFile);
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(fXmlFile);

            // Prevent unwanted behaviour resulting from poorly formatted XML
            doc.getDocumentElement().normalize();
            
            // Get the assets
            NodeList nodes = doc.getElementsByTagName("Asset");
            for (int i = 0; i < nodes.getLength(); i++)
            {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element)node;
                    int id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                    int length = Integer.parseInt(element.getElementsByTagName("Length").item(0).getTextContent());
                    AssetType assetType = AssetType.valueOf(element.getElementsByTagName("Type").item(0).getTextContent());
                    String description = element.getElementsByTagName("Description").item(0).getTextContent();
                    
                    Asset asset = new Asset(id, length, assetType, description);
                    allAssets.add(asset);
                }
            }
        }
        catch (ParserConfigurationException | SAXException | IOException | DOMException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
