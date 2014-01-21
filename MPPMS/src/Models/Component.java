package Models;

import java.io.File;
import java.io.IOException;
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
import javax.xml.xpath.*;

public class Component {
    private static SetOfComponents allComponents = null;
    
    private Vector<Asset> assets = new Vector<>();
    
    private final int id;
    private String description;
    
    public Component(int id, String description) {
        this.id = id;
        this.description = description;
    }
    
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
            
            XPathFactory xpathfactory = XPathFactory.newInstance();
            XPath xpath = xpathfactory.newXPath();
            XPathExpression expr;
            Object result;
  
            // Get the persisted Projects
            try
            {
                expr = xpath.compile("/root/Component");
                result = expr.evaluate(doc, XPathConstants.NODESET);
                NodeList allComponentNodes = (NodeList) result;
            
                if (allComponentNodes != null)
                {
                    for (int i = 0; i < allComponentNodes.getLength(); i++) 
                    {
                        Node individualComponentNode = allComponentNodes.item(i);
                        
                        if (individualComponentNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element individualComponentElement = (Element)individualComponentNode;
                            int id = Integer.parseInt(individualComponentElement.getElementsByTagName("ID").item(0).getTextContent());
                            String description = individualComponentElement.getElementsByTagName("Description").item(0).getTextContent();

                            Component component = new Component(id, description);

                            // Get the assets which make up this component
                            // Get 'AssignedTo' users
                            expr = xpath.compile("Assets");
                            result = expr.evaluate(individualComponentNode, XPathConstants.NODE);
                            NodeList assetNodes = (NodeList) result;

                            for (int x = 0; x < assetNodes.getLength(); x++) 
                            {
                                Node assetNode = assetNodes.item(x);

                                if (assetNode.getNodeType() == Node.ELEMENT_NODE)
                                {
                                    Element assetElement = (Element)assetNode;
                                    int assetID = Integer.parseInt(assetElement.getTextContent());
                                    component.addAsset(Asset.getAssetByID(assetID));
                                }
                            }
                            
                            allComponents.add(component); 
                        }
                    }
                 }
            }
            catch (XPathExpressionException xe)
            {
                System.out.println("Error reading Projects.xml: " + xe.toString());
            }
        }
        catch (ParserConfigurationException | SAXException | IOException | DOMException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
