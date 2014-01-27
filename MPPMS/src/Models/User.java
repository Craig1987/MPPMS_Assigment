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

public class User extends Model {
    private static SetOfUsers allUsers = null;
    
    private SetOfProjects currentProjects;
    private final String username;
    private final String password;
    private final String forename;
    private final String surname;
    private final String name;
    private final Role role;
    
    public enum Role {
        ProjectCoordinator,
        ProjectManager,
        QCTeamLeader,
        QCTeamMember,
        Client
    }
    
    public User(Role role, String username, String password, String forename, String surname) {
        this.username = username;
        this.password = password;
        this.forename = forename;
        this.surname = surname;
        this.name = "";
        this.role = role;
    }
    
    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.forename = "";
        this.surname = "";
        this.name = name;
        this.role = Role.Client;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public SetOfProjects getCurrentProjects() {
        return currentProjects;
    }

    public Role getRole() {
        return role;
    }
    
    /**
     * Gets the User's name.
     * @return "Forename Surname" if setName(String, String) was used
     * or "Name" if setName(String) was used.
     */
    public String getName()
    {
        if (this.forename.isEmpty())
        {
            return this.name;
        }
        return String.format("%s %s", this.forename, this.surname);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    public static SetOfUsers getAllUsers() {
        if (allUsers == null) {
            populateUsers();
        }
        return allUsers;
    }
    
    public static User getUserByUsername(String username) {
        for (User user : getAllUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    public static SetOfUsers getUsersByRole(Role role) {
        SetOfUsers users = new SetOfUsers();
        for (User user : getAllUsers()) {
            if (user.getRole() == role) {
                users.add(user);
            }
        }
        return users;
    }
    
    public static boolean authenticate(String username, String password) {
        for (User user : getAllUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    private static void populateUsers() {
        try 
        {
            allUsers = new SetOfUsers();
            String pathToFile = User.class.getResource("/Data/Users.xml").getPath();
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
                    Role role = Role.valueOf(element.getElementsByTagName("Role").item(0).getTextContent());
                    
                    allUsers.add((forename.isEmpty() ? new User(username, password, name) : new User(role, username, password, forename, surname)));
                }
            }
        }
        catch (ParserConfigurationException | SAXException | IOException | DOMException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
