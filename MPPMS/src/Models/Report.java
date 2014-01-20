package Models;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Report {
    private static SetOfReports allReports = null;
    
    private final int id;
    private SetOfComments comments = new SetOfComments();
    
    public Report(int id) {
        this.id = id;
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
        return "(ID: " + getId() + ") " + this.comments.size() + " comments";
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
            
            // Get the persisted Reports
            NodeList reportNodes = doc.getElementsByTagName("Report");
            for (int i = 0; i < reportNodes.getLength(); i++)
            {
                Node node = reportNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element)node;
                    int id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                    
                    Report report = new Report(id);
                                        
                    // Get the comments which belong to this report
                    NodeList commentNodes = element.getElementsByTagName("Comments");
                    for (int x = 0; x < commentNodes.getLength(); x++)
                    {
                        Node commentNode = commentNodes.item(x);
                        if (commentNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element commentElement = (Element)commentNode;
                            String username = commentElement.getElementsByTagName("Username").item(0).getTextContent();
                            String content = commentElement.getElementsByTagName("Content").item(0).getTextContent();
                            Date date;
                            try {
                                date = new SimpleDateFormat("dd/MM/yyyy").parse(commentElement.getElementsByTagName("Date").item(0).getTextContent());
                            }
                            catch (ParseException pEx) {
                                date = new Date();
                            }
                            
                            report.addComment(new Comment(date, User.getUserByUsername(username), content));
                        }
                    }
                    
                    allReports.add(report);
                }
            }
        }
        catch (ParserConfigurationException | SAXException | IOException | DOMException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
