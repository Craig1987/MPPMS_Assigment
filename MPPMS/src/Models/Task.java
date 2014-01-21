package Models;

import java.io.File;
import java.io.IOException;
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

public class Task extends Model {
    private static SetOfTasks allTasks = null;
    
    private final int id;
    private final TaskType taskType;
    
    private String title;
    private SetOfUsers assignedTo;
    private Status status;
    private Priority priority;

    private Report report;
    
    public enum TaskType {
        QC,
        Build
    }
    
    public enum Status {
        New,
        Ongoing,
        Completed
    }
    public enum Priority {
        Highest,
        High,
        Normal,
        Low,
        Lowest
    }

    
    public Task(int id, TaskType taskType) {
        this.id = id;
        this.taskType = taskType;
    }
    
    public int getId() {
        return this.id;
    }
    
    public TaskType getTaskType() {
        return this.taskType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SetOfUsers getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(SetOfUsers assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Priority getPriority() {
        return priority;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
    
    @Override
    public String toString() {
        return "(ID: " + getId() + ") " + getTaskType() + " Task - " + getTitle();
    }
    
    public static SetOfTasks getAllTasks() {
        if (allTasks == null) {
            populateTasks();
        }
        return allTasks;
    }
    
    public static Task getTaskByID(int id) {
        for (Task task : getAllTasks()) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
    
    public static SetOfTasks getTasksForUser(User user) {
        SetOfTasks tasks = new SetOfTasks();
        for (Task task : getAllTasks()) {
            if (task.getAssignedTo().contains(user)) {
                tasks.add(task);
            }
        }
        return tasks;
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
            
            XPathFactory xpathfactory = XPathFactory.newInstance();
            XPath xpath = xpathfactory.newXPath();
            XPathExpression expr;
            Object result;
  
            // Get the persisted Projects
            try
            {
                expr = xpath.compile("/root/Task");
                result = expr.evaluate(doc, XPathConstants.NODESET);
                NodeList allTaskNodes = (NodeList) result;
            
                if (allTaskNodes != null)
                {
                    for (int i = 0; i < allTaskNodes.getLength(); i++) 
                    {
                        Node individualTaskNode = allTaskNodes.item(i);
                        
                        if (individualTaskNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element individualTaskElement = (Element)individualTaskNode;
                            int id = Integer.parseInt(individualTaskElement.getElementsByTagName("ID").item(0).getTextContent());
                            Status status = Status.valueOf(individualTaskElement.getElementsByTagName("Status").item(0).getTextContent());
                            Priority priority = Priority.valueOf(individualTaskElement.getElementsByTagName("Priority").item(0).getTextContent());
                            int reportID = Integer.parseInt(individualTaskElement.getElementsByTagName("ReportID").item(0).getTextContent());
                            String title = individualTaskElement.getElementsByTagName("Title").item(0).getTextContent();
                            TaskType taskType = TaskType.valueOf(individualTaskElement.getElementsByTagName("Type").item(0).getTextContent());

                            // Get 'AssignedTo' users
                            expr = xpath.compile("AssignedTo");
                            result = expr.evaluate(individualTaskNode, XPathConstants.NODE);
                            NodeList assignedToNodes = (NodeList) result;

                            SetOfUsers assignedTo = new SetOfUsers();

                            for (int x = 0; x < assignedToNodes.getLength(); x++) 
                            {
                                Node userNode = assignedToNodes.item(x);

                                if (userNode.getNodeType() == Node.ELEMENT_NODE)
                                {
                                    Element userElement = (Element)userNode;
                                    String username = userElement.getTextContent();
                                    assignedTo.add(User.getUserByUsername(username));
                                }
                            }
                            
                            Task task = new Task(id, taskType);
                            task.setTitle(title);
                            task.setAssignedTo(assignedTo);
                            task.setPriority(priority);
                            task.setReport(Report.getReportByID(reportID));
                            task.setStatus(status);

                            allTasks.add(task);
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