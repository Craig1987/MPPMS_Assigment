package Models;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Project {
    private static SetOfProjects allProjects = null;
    
    private final int id;
    private final Date creationDate;
    
    private String title;    
    private User manager;
    private User coordinator;
    private SetOfUsers team;
    private Date deadline;
    private int priority;
    private SetOfTasks tasks;
    private SetOfComponents components;
    
    public Project(int id, Date creationDate) {
        this.id = id;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public User getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(User coordinator) {
        this.coordinator = coordinator;
    }

    public SetOfUsers getTeam() {
        return team;
    }

    public void setTeam(SetOfUsers team) {
        this.team = team;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public SetOfTasks getTasks() {
        return tasks;
    }

    public void setTasks(SetOfTasks tasks) {
        this.tasks = tasks;
    }

    public SetOfComponents getComponents() {
        return components;
    }

    public void setComponents(SetOfComponents components) {
        this.components = components;
    }
    
    public static SetOfProjects getAllProjects() {
        if (allProjects == null) {
            populateProjects();
        }
        return allProjects;
    }
    
    public static SetOfProjects getProjectsForUser(User user) {
        SetOfProjects projects = new SetOfProjects();
        for (Project project : getAllProjects()) {
            if (project.getManager() == user || project.getCoordinator() == user || project.getTeam().contains(user)) {
                projects.add(project);
            }
        }
        return projects;
    }
    
    private static void populateProjects() {
        try 
        {
            allProjects = new SetOfProjects();
            String pathToFile = Project.class.getResource("/Data/Projects.xml").getPath();
            pathToFile = pathToFile.replaceAll("%20", " ");
            File fXmlFile = new File(pathToFile);
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(fXmlFile);

            // Prevent unwanted behaviour resulting from poorly formatted XML
            doc.getDocumentElement().normalize();
            
            // Get the persisted Projects
            NodeList projectNodes = doc.getElementsByTagName("Project");
            for (int i = 0; i < projectNodes.getLength(); i++)
            {
                Node node = projectNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element)node;
                    int id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                    int priority = Integer.parseInt(element.getElementsByTagName("Priority").item(0).getTextContent());
                    String title = element.getElementsByTagName("Title").item(0).getTextContent();
                    String managerUsername = element.getElementsByTagName("Manager").item(0).getTextContent();
                    String coordinatorUsername = element.getElementsByTagName("Coordinator").item(0).getTextContent();
                    Date creationDate;
                    Date deadline;
                    try {
                        creationDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(element.getElementsByTagName("CreationDate").item(0).getTextContent());
                        deadline = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(element.getElementsByTagName("Deadline").item(0).getTextContent());
                    }
                    catch (ParseException pEx) {
                        System.out.println(pEx.getMessage());
                        creationDate = new Date();
                        deadline = new Date();
                    }
                    
                    // Get the project team
                    NodeList teamNodes = element.getElementsByTagName("Team");
                    SetOfUsers team = new SetOfUsers();
                    for (int x = 0; x < teamNodes.getLength(); x++)
                    {
                        Node teamNode = teamNodes.item(x);
                        if (teamNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element teamElement = (Element)teamNode;
                            String username = teamElement.getElementsByTagName("Username").item(0).getTextContent();
                            team.add(User.getUserByUsername(username));
                        }
                    }
                    
                    // Get the project tasks
                    NodeList taskNodes = element.getElementsByTagName("Tasks");
                    SetOfTasks tasks = new SetOfTasks();
                    for (int x = 0; x < taskNodes.getLength(); x++)
                    {
                        Node taskNode = taskNodes.item(x);
                        if (taskNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element taskElement = (Element)taskNode;
                            int taskID = Integer.parseInt(taskElement.getElementsByTagName("TaskID").item(0).getTextContent());
                            tasks.add(Task.getTaskByID(taskID));
                        }
                    }
                    
                    // Get the project components
                    NodeList componentNodes = element.getElementsByTagName("Components");
                    SetOfComponents components = new SetOfComponents();
                    for (int x = 0; x < componentNodes.getLength(); x++)
                    {
                        Node componentNode = componentNodes.item(x);
                        if (componentNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element componentElement = (Element)componentNode;
                            int componentID = Integer.parseInt(componentElement.getElementsByTagName("ComponentID").item(0).getTextContent());
                            components.add(Component.getComponentByID(componentID));
                        }
                    }
                    
                    Project project = new Project(id, creationDate);
                    project.setTitle(title);
                    project.setDeadline(deadline);
                    project.setPriority(priority);
                    project.setManager(User.getUserByUsername(managerUsername));
                    project.setCoordinator(User.getUserByUsername(coordinatorUsername));
                    project.setTeam(team);
                    project.setTasks(tasks);
                    project.setComponents(components);
                    
                    allProjects.add(project);
                }
            }
        }
        catch (ParserConfigurationException | SAXException | IOException | DOMException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
