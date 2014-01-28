package XmlMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author ryantk
 */
public class XmlSaver {
    
    String pathToXml;
    Map<String, String> attributes = new HashMap<>();
    Document document;
    XPath xpath;
    
    public XmlSaver(String path, Map attrs) {
        pathToXml  = path;
        attributes = attrs;
    }
    
    public boolean save() {
        Document doc = getDocument();
        Node node    = getNode();
        Node root    = doc.getFirstChild();
        Transformer transformer = getTransformer();
        
        if (doc == null || node == null || root == null || transformer == null)
            return false;
        
        // Clear any data from before
        while (node.hasChildNodes())
            node.removeChild(node.getFirstChild());
        
        // Update Node's Data
        for (Map.Entry<String, String> entry : getAttributes().entrySet())
            if (isSavableAttribute(entry.getKey()))
                node.appendChild(doc.createElement(entry.getKey())).setTextContent(entry.getValue());
        
        // Append new Child to root
        root.appendChild(node);
        
        // Input (XML DOM) / Output (File.xml)
        Result output = new StreamResult(new File(pathToXml));
        Source input  = new DOMSource(doc);
        
        // Attempt to transform DOM to XML file
        try {
            transformer.transform(input, output);
        } catch (TransformerException ex) {
            Logger.getLogger(XmlSaver.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        // Success
        return true;
    }
    
    private boolean isSavableAttribute(String key) {
        return ! (key.equals("SaveBy") || key.equals("Node"));
    }
    
    private Transformer getTransformer() {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            tf.setAttribute("indent-number", new Integer(4)); // Fix output formatting
            
            Transformer t = tf.newTransformer();
            // Fix output formatting
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            return t;
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XmlSaver.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private Document getDocument() {
        if (document == null){
            try {
                document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new FileInputStream(pathToXml));

            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(XmlSaver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return document;
    }
    
    private String buildExpression() {
        String saveByNode  = attributes.get("SaveBy");
        String saveByValue = attributes.get(saveByNode);
        String node        = attributes.get("Node");
        
        return "//" + node + "" + "["  + saveByNode + "=" + saveByValue + "]";
    }
    
    private Node getNode() {
        try {
            Node node = (Node) getXPath().evaluate(buildExpression(), getDocument(), XPathConstants.NODE);
            
            // New Child
            if (node == null)
                node = getDocument().createElement(attributes.get("Node"));
            
            return node;            
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XmlSaver.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private XPath getXPath() {
        if (xpath == null)
            xpath = XPathFactory.newInstance().newXPath();
        
        return xpath;
    }
    
    public String getPathToXml() {
        return pathToXml;
    }

    public void setPathToXml(String pathToXml) {
        this.pathToXml = pathToXml;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }    
}
