package Registry;

import Projects.Project;
import Projects.SetOfProjects;
import Users.Client;
import Users.ProjectCoordinator;
import Users.ProjectManager;
import Users.QATeamLeader;
import Users.QATeamMember;
import Users.SetOfUsers;
import Users.StaffMember;
import Users.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UserRegistry
{
    private static UserRegistry instance = null;
    private SetOfUsers users = new SetOfUsers();
    
    /**
     * Private constructor - this class uses the Singleton pattern.
     * <p>
     * Details of Users are persisted using the UserRegistry.xml file in the 
     * Registry package so the UserRegistry constructor reads from this file and 
     * instantiates all of the persisted Users.
     * </p>
     */
    private UserRegistry() throws FileNotFoundException, XMLStreamException
    {
        try 
        {
            String pathToFile = getClass().getResource("/Registry/UserRegistry.xml").getPath();
            pathToFile = pathToFile.replaceAll("%20", " ");
            File fXmlFile = new File(pathToFile);
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.parse(fXmlFile);

            // Prevent unwanted behaviour resulting from poorly formatted XML
            doc.getDocumentElement().normalize();
            
            // Get the persisted Users
            NodeList nodes = doc.getElementsByTagName("User");
            
            // For each User, create an instance and add to the SetOfUsers
            for (int i = 0; i < nodes.getLength(); i++)
            {
                Node node = nodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element)node;
                    String username = element.getElementsByTagName("Username").item(0).getTextContent();
                    String password = element.getElementsByTagName("Password").item(0).getTextContent();
                    String forename = element.getElementsByTagName("Forename").item(0).getTextContent();
                    String surname = element.getElementsByTagName("Surname").item(0).getTextContent();
                    String name = element.getElementsByTagName("Name").item(0).getTextContent();
                    String type = element.getElementsByTagName("Type").item(0).getTextContent();
                    
                    User user = null;
                    
                    switch (type)
                    {
                        case "General":
                            user = new StaffMember(username, password, forename, surname);
                            break;
                        case "QATeamMember":
                            user = new QATeamMember(username, password, forename, surname);
                            break;
                        case "QATeamLeader":
                            user = new QATeamLeader(username, password, forename, surname);
                            break;
                        case "ProjectCoordinator":
                            user = new ProjectCoordinator(username, password, forename, surname);
                            break;
                        case "ProjectManager":
                            user = new ProjectManager(username, password, forename, surname);
                            break;
                        case "Client":
                            user = (forename.isEmpty() ? new Client(name) : new Client(forename, surname));
                            break;
                        default:
                            user = new User("");
                    }
                    
                    // Get the persisted Projects for this user
                    NodeList projectNodes = element.getElementsByTagName("Project");

                    // For each Project, create an instance and add to the User's set of projects
                    SetOfProjects projects = new SetOfProjects();
                    for (int x = 0; x < projectNodes.getLength(); x++)
                    {
                        Node projectNode = projectNodes.item(x);

                        if (projectNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element projectElement = (Element)projectNode;
                            String title = projectElement.getElementsByTagName("Title").item(0).getTextContent();
                            User createdBy = new User(projectElement.getElementsByTagName("CreatedBy").item(0).getTextContent());
                            Date creationDate;
                            try
                            {
                                creationDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(projectElement.getElementsByTagName("CreationDate").item(0).getTextContent());
                            }
                            catch (ParseException pEx)
                            {
                                creationDate = new Date();
                            }                            
                            projects.add(new Project(title, creationDate, createdBy));
                        }
                    }
                    user.setProjects(projects);
                    users.add(user);
                }
            }
        }
        catch (ParserConfigurationException | SAXException | IOException | DOMException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Gets of the UserRegistry instance (instantiates with the first call
     * and simply returns the instance on subsequent calls).
     * @return The single instance of the UserRegistry.
     */
    public static UserRegistry getInstance()
    {
        if (instance == null)
        {
            try
            {
                instance = new UserRegistry();
            }
            catch (FileNotFoundException | XMLStreamException ex)
            {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
        return instance;
    }
    
    /**
     * This method checks given credentials within the stored registry in order 
     * to identify individual users.
     * @param username The username of the User attempting to access the 
     * system
     * @param password The password to verify that the User attempting to 
     * access the system is who they say they are.
     * @return User object if a match is found and the credentials are valid; 
     * null if validation fails.
     */
    public User authenticate(String username, String password)
    {
        for (User user : users)
        {
            if (user instanceof StaffMember)
            {
                StaffMember member = (StaffMember)user;
                if (member.getUsername().equals(username) && 
                        member.getPassword().equals(password))
                {
                    return user;
                }
            }
        }
        return null;
    }
}