package Models;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Component {
    private static SetOfComponents allComponents = null;
    
    private Vector<Asset> assets = new Vector<>();
    
    private final int id;
    
    public Component(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public Vector<Asset> getAssets() {
        return assets;
    }
    
    public void addAsset(Asset a) {
        assets.add(a);
    }
    
    public void removeAsset(Asset a) {
        assets.remove(a);
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
    
    private static void populateComponents() {
        try 
        {
            allComponents = new SetOfComponents();
            String pathToFile = Project.class.getResource("/Data/Components.xml").getPath();
            pathToFile = pathToFile.replaceAll("%20", " ");
            File fXmlFile = new File(pathToFile);
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(fXmlFile);

            // Prevent unwanted behaviour resulting from poorly formatted XML
            doc.getDocumentElement().normalize();
            
            // Get the persisted Components
            NodeList componentNodes = doc.getElementsByTagName("Component");
            for (int i = 0; i < componentNodes.getLength(); i++)
            {
                Node node = componentNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element)node;
                    int id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                    
                    Component component = new Component(id);
                                        
                    // Get the assets which make up this component
                    NodeList assetNodes = element.getElementsByTagName("Assets");
                    for (int x = 0; x < assetNodes.getLength(); x++)
                    {
                        Node assetNode = assetNodes.item(x);
                        if (assetNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element assetElement = (Element)assetNode;
                            int assetID = Integer.parseInt(assetElement.getElementsByTagName("AssetID").item(0).getTextContent());
                            component.addAsset(Asset.getAssetByID(assetID));
                        }
                    }
                    
                    allComponents.add(component);
                }
            }
        }
        catch (ParserConfigurationException | SAXException | IOException | DOMException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
