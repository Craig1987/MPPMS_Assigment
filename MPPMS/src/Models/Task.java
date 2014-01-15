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

public class Task {
    private static SetOfTasks allTasks = null;
    
    private final int id;
    private final ReportType reportType;
    
    private SetOfUsers assignedTo;
    private int status;
    private int priority;
    private Report report;
    
    public enum ReportType {
        QC,
        Build
    }
    
    public Task(int id, ReportType reportType) {
        this.id = id;
        this.reportType = reportType;
    }
    
    public int getID() {
        return this.id;
    }

    public SetOfUsers getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(SetOfUsers assignedTo) {
        this.assignedTo = assignedTo;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
    
    public static SetOfTasks getAllTasks() {
        if (allTasks == null) {
            populateTasks();
        }
        return allTasks;
    }
    
    public static Task getTaskByID(int id) {
        for (Task task : getAllTasks()) {
            if (task.getID() == id) {
                return task;
            }
        }
        return null;
    }
    
    private static void populateTasks() {
        try 
        {
            allTasks = new SetOfTasks();
            String pathToFile = User.class.getResource("/Data/Tasks.xml").getPath();
            pathToFile = pathToFile.replaceAll("%20", " ");
            File fXmlFile = new File(pathToFile);
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(fXmlFile);

            // Prevent unwanted behaviour resulting from poorly formatted XML
            doc.getDocumentElement().normalize();
            
            // Get the Tasks
            NodeList taskNodes = doc.getElementsByTagName("Task");
            for (int i = 0; i < taskNodes.getLength(); i++)
            {
                Node node = taskNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element taskElement = (Element)node;
                    int id = Integer.parseInt(taskElement.getElementsByTagName("ID").item(0).getTextContent());
                    int status = Integer.parseInt(taskElement.getElementsByTagName("Status").item(0).getTextContent());
                    int priority = Integer.parseInt(taskElement.getElementsByTagName("Priority").item(0).getTextContent());
                    int reportID = Integer.parseInt(taskElement.getElementsByTagName("ReportID").item(0).getTextContent());
                    Task.ReportType reportType = Task.ReportType.valueOf(taskElement.getElementsByTagName("Type").item(0).getTextContent());
                    
                    // Get 'AssignedTo' users
                    NodeList assignedNodes = taskElement.getElementsByTagName("AssignedTo");
                    SetOfUsers assignedTo = new SetOfUsers();
                    for (int x = 0; x < assignedNodes.getLength(); x++)
                    {
                        Node assignedNode = assignedNodes.item(x);
                        if (assignedNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element assignedElement = (Element)assignedNode;
                            String username = assignedElement.getElementsByTagName("Username").item(0).getTextContent();
                            assignedTo.add(User.getUserByUsername(username));
                        }
                    }
                    
                    Task task = new Task(id, reportType);
                    task.setAssignedTo(assignedTo);
                    task.setPriority(priority);
                    task.setReport(Report.getReportByID(reportID));
                    task.setStatus(status);
                    
                    allTasks.add(task);
                }
            }
        }
        catch (ParserConfigurationException | SAXException | IOException | DOMException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
