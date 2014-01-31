package Models;

import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    private User user;
    
    public UserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public String testUsername(){ return "mrbinks"; }
    public String testPassword(){ return "pa$$word"; }
    public String testFirstname(){ return "Paul"; }
    public String testSurname(){ return "Binkster"; }
    public String testName(){ return "ClientName"; }
    
    @Before
    public void setUp() {
        user = new User(User.Role.ProjectManager, testUsername(), testPassword(), testFirstname(), testSurname(), testName());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getUsername method, of class User.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        
        assertEquals(testUsername(), user.getUsername());
    }

    /**
     * Test of getPassword method, of class User.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        
        assertEquals(testPassword(), user.getPassword());
    }

    /**
     * Test of getName method, of class User.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        
        assertEquals(testFirstname() + " " + testSurname(), user.getName());
    }

    /**
     * Test of toString method, of class User.
     */
    @Test
    public void testToString() {
        System.out.println("toString"); 
        
        // toString must match getName
        assertEquals(user.getName() + " (" + user.getUsername() + ")", user.toString());
    }
    
    /**
     * Test of getAllUsers method, of class User.
     */
    @Test
    public void testGetAllUsers() {
        System.out.println("getAllUsers");
        
        assertThat(User.getAllUsers(), instanceOf(SetOfUsers.class));
    }

    /**
     * Test of getUserByUsername method, of class User.
     */
    @Test
    public void testGetUserByUsername() {
        System.out.println("getUserByUsername");
        
        User testUser = User.getUserByUsername("Joe90");
        
        assertEquals(testUser.getUsername(), "Joe90");
        assertThat(testUser, instanceOf(User.class));
    }

    /**
     * Test of authenticate method, of class User.
     */
    @Test
    public void testAuthenticate() {
        System.out.println("authenticate");
        
        assertTrue(User.authenticate("Joe90", "password"));
    }
}
