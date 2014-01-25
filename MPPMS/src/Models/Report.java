package Models;

import Application.AppObservable;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Report {
    private static SetOfReports allReports = null;
    
    private int id;
    private SetOfComments comments;
    
    public Report(int id) {
        this.id = id;
        this.comments = new SetOfComments();
    }

    Report() {
        this.id = 0;
        this.comments = new SetOfComments();
    }
    
    public int getId() {
        return id;
    }
    
    public void addComment(Comment comment) {
        comments.add(comment);
    }
    
    public void removeComment(Comment comment) {
        comments.remove(comment);
    }
    
    @Override
    public String toString() {
        String result = this.comments.size() + " comments";        
        if (this.comments.size() > 0) {
            Date lastEditDate = this.comments.get(this.comments.size() - 1).getDate();
            result += " (last edited " + new SimpleDateFormat("dd MMM yyyy").format(lastEditDate) + ")";
        }
        return result;
    }
    
    public SetOfComments getAllComments() {
        return this.comments;
    }
    
    public void save() {
        if (id == 0){
            id = getAllReports().get(getAllReports().size() - 1).getId();
        }

        // TODO: Implement XML Persistance of Project
        System.out.println("TODO: Implement XML Persistance of Project | Models/Project.java:146");
        
        if (allReports != null) {
            allReports.clear();
        }
        allReports = null;
        AppObservable.getInstance().notifyObserversToRefresh();
    }
    
    public static SetOfReports getAllReports() {
        if (allReports == null) {
            populateReports();
        }
        return allReports;
    }
    
    public static Report getReportByID(int id) {
        for (Report report: getAllReports()) {
            if (report.getId() == id) {
                return report;
            }
        }
        return null;
    }
    
    private static void populateReports() {
        try 
        {
            allReports = new SetOfReports();
            String pathToFile = Project.class.getResource("/Data/Reports.xml").getPath();
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
            
            // Get the persisted Reports
            try
            {
                expr = xpath.compile("/root/Report");
                result = expr.evaluate(doc, XPathConstants.NODESET);
                NodeList allReportNodes = (NodeList) result;
                
                if (allReportNodes != null)
                {
                    for (int i = 0; i < allReportNodes.getLength(); i++) 
                    {
                        Node individualReportNode = allReportNodes.item(i);
                        
                        if (individualReportNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                             Element individualReportElement = (Element)individualReportNode;
                             int id = Integer.parseInt(individualReportElement.getElementsByTagName("ReportID").item(0).getTextContent());
                             Report report = new Report(id);
                             
                             // Get report comments
                            expr = xpath.compile("Comments");
                            result = expr.evaluate(individualReportNode, XPathConstants.NODE);
                            NodeList commentsNodes = (NodeList) result;

                            for (int x = 0; x < commentsNodes.getLength(); x++) 
                            {
                                Node commentNode = commentsNodes.item(x);
                               
                                if (commentNode.getNodeType() == Node.ELEMENT_NODE)
                                {
                                    Element commentElement = (Element)commentNode;
                                    int commentId = Integer.parseInt(commentElement.getElementsByTagName("ID").item(0).getTextContent());
                                    String username = commentElement.getElementsByTagName("Username").item(0).getTextContent();
                                    String content = commentElement.getElementsByTagName("Content").item(0).getTextContent();
                                    Date date;
                                    try 
                                    {
                                        date = new SimpleDateFormat("dd/MM/yyyy").parse(commentElement.getElementsByTagName("Date").item(0).getTextContent());
                                    }
                                    catch (ParseException pEx) 
                                    {
                                        date = new Date();
                                    }
                                    
                                    report.addComment(new Comment(commentId, date, User.getUserByUsername(username), content));
                                }
                            }
                            
                            allReports.add(report);
                            
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
        
