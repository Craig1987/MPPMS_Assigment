package Models;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Asset {
    private static SetOfAssets allAssets = null;
    
    private final int id;
    private final int length;
    private final AssetType assetType;
    
    public enum AssetType {
        Audio,
        Video,
        Subtitles,
        Menu
    }
    
    public Asset(int id, int length, AssetType assetType) {
        this.id = id;
        this.length = length;
        this.assetType = assetType;
    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public AssetType getAssetType() {
        return assetType;
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
                    
                    Asset asset = new Asset(id, length, assetType);
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
