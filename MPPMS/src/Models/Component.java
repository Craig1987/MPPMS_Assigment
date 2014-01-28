package Models;

import Application.AppObservable;
import Data.DatabaseConnector;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            DatabaseConnector dbConn = DatabaseConnector.getInstance();
            ResultSet components = dbConn.selectQuery("SELECT C.ID, C.DESCRIPTION, CA.ASSETID FROM COMPONENTS C INNER JOIN COMPONENTASSETS CA ON C.ID = CA.COMPONENTID");
            
            int componentID = 1;
            String componentDescription = "";
            SetOfAssets componentAssets = new SetOfAssets();
            
            while (components.next()) {
                if (componentID != components.getInt("ID")) {
                    // We have collected all assets for this Component, so create and save it
                    Component component = new Component(componentID, componentDescription);
                    component.setAssets((SetOfAssets)componentAssets.clone());
                    allComponents.add(component);
                    componentAssets.clear();
                }
                
                componentID = components.getInt("ID");
                componentDescription = components.getString("DESCRIPTION");
                componentAssets.add(Asset.getAssetByID(components.getInt("ASSETID")));
            }
            
            // We have collected all assets for this Component, so create and save it
            Component component = new Component(componentID, componentDescription);
            component.setAssets((SetOfAssets)componentAssets.clone());
            allComponents.add(component);
            componentAssets.clear();
                    
            dbConn.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Asset.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        /*
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

                            SetOfAssets assets = new SetOfAssets();
                            
                            for (int x = 0; x < assetNodes.getLength(); x++) 
                            {
                                Node assetNode = assetNodes.item(x);
                                
                                if (assetNode.getNodeType() == Node.ELEMENT_NODE)
                                {
                                    Element assetElement = (Element)assetNode;
                                    int assetID = Integer.parseInt(assetElement.getTextContent());
                                    assets.add(Asset.getAssetByID(assetID));
                                }
                            }
                            
                            component.setAssets(assets);
                            
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
                */
    }
}