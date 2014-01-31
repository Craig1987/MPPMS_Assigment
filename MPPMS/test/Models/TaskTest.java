package Models;

import Models.Task.Priority;
import Models.Task.Status;
import Models.Task.TaskType;
import java.util.ArrayList;
import java.util.HashMap;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kirsty
 */
public class TaskTest {
    private Task task;
    
    public TaskTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public int testId(){ return 99; }
    public TaskType testType(){ return TaskType.Build; }
    public String testTitle() { return "Test title"; }
    public SetOfAssets testAssets() { return Asset.getAllAssets(); }
    public Priority testPriority() { return Task.Priority.Normal; }
    public Status testStatus() { return Task.Status.Assigned; }
    
    @Before
    public void setUp() {
        task = new Task(testId(), testType());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setTaskType method, of class Task.
     */
    @Test
    public void testSetTaskType() {
        System.out.println("setTaskType");
        task.setTaskType(TaskType.QA);
        assertEquals(TaskType.QA, task.getTaskType());
    }

    /**
     * Test of getId method, of class Task.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        
        assertEquals(testId(), task.getId());
    }

    /**
     * Test of setId method, of class Task.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int test = 66;
        task.setId(66);
        assertEquals(test, task.getId());
    }

    /**
     * Test of getTaskType method, of class Task.
     */
    @Test
    public void testGetTaskType() {
        System.out.println("getTaskType");
        
        assertEquals(testType(), task.getTaskType());
    }

    /**
     * Test of getTitle method, of class Task.
     */
    @Test
    public void testGetTitle() {
        System.out.println("getTitle");

        task.setTitle(testTitle());
        assertEquals(testTitle(), task.getTitle());
    }

    /**
     * Test of getAssignedTo method, of class Task.
     */
    @Test
    public void testGetAssignedTo() {
        System.out.println("getAssignedTo");

        assertEquals(task.getAssignedTo(), instanceOf(SetOfUsers.class));
    }

    /**
     * Test of addAssignedTo method, of class Task.
     */
    @Test
    public void testAddAssignedTo() {
        System.out.println("addAssignedTo");
        
        User user = new User(User.Role.ProjectCoordinator, "Username", "Password", "Forename", "Surname", "ClientName");

        task.addAssignedTo(user);
        SetOfUsers users = task.getAssignedTo();
        assertTrue(users.contains(user));
    }

    /**
     * Test of getAssets method, of class Task.
     */
    @Test
    public void testGetAssets() {
        System.out.println("getAssets");
        
        assertEquals(testAssets(), instanceOf(SetOfAssets.class));
    }

    /**
     * Test of setAssets method, of class Task.
     */
    @Test
    public void testSetAssets() {
        System.out.println("setAssets");
        
        task.setAssets(testAssets());
        assertEquals(testAssets(), task.getAssets());
    }

    /**
     * Test of addAsset method, of class Task.
     */
    @Test
    public void testAddAsset() {
        System.out.println("addAsset");
        
        Asset asset = new Asset();

        task.addAsset(asset);
        SetOfAssets assets = task.getAssets();
        assertTrue(assets.contains(asset));
    }

    /**
     * Test of getPriority method, of class Task.
     */
    @Test
    public void testGetPriority() {
        System.out.println("getPriority");

        task.setPriority(testPriority());
        assertEquals(testPriority(), task.getPriority());
    }

    /**
     * Test of setPriority method, of class Task.
     */
    @Test
    public void testSetPriority() {
        System.out.println("setPriority");
        
        task.setPriority(testPriority());
        assertEquals(testPriority(), task.getPriority());
    }

    /**
     * Test of getStatus method, of class Task.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");

        task.setStatus(testStatus());
        assertEquals(testStatus(), task.getStatus());
    }

    /**
     * Test of setStatus method, of class Task.
     */
    @Test
    public void testSetStatus() {
        System.out.println("setStatus");
        Task.Status status = null;
        Task instance = new Task();
        instance.setStatus(status);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReport method, of class Task.
     */
    @Test
    public void testGetReport() {
        System.out.println("getReport");
        Task instance = new Task();
        Report expResult = null;
        Report result = instance.getReport();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReport method, of class Task.
     */
    @Test
    public void testSetReport() {
        System.out.println("setReport");
        Report report = null;
        Task instance = new Task();
        instance.setReport(report);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clearAndNullifyAll method, of class Task.
     */
    @Test
    public void testClearAndNullifyAll() {
        System.out.println("clearAndNullifyAll");
        
        Task.clearAndNullifyAll();
        assertEquals(null, Task.getAllTasks());
    }

    /**
     * Test of toString method, of class Task.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        
        String test = "(ID: " + task.getId() + ") " + task.getTaskType() + " Task - " + task.getTitle();
        assertEquals(test, task.toString());
    }

    /**
     * Test of getAllTasks method, of class Task.
     */
    @Test
    public void testGetAllTasks() {
        System.out.println("getAllTasks");
        SetOfTasks expResult = null;
        SetOfTasks result = Task.getAllTasks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTaskByID method, of class Task.
     */
    @Test
    public void testGetTaskByID() {
        System.out.println("getTaskByID");
        int id = 0;
        Task expResult = null;
        Task result = Task.getTaskByID(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTasksForUser method, of class Task.
     */
    @Test
    public void testGetTasksForUser() {
        System.out.println("getTasksForUser");
        User user = null;
        SetOfTasks expResult = null;
        SetOfTasks result = Task.getTasksForUser(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTasksByStatus method, of class Task.
     */
    @Test
    public void testGetTasksByStatus() {
        System.out.println("getTasksByStatus");
        Task.Status status = null;
        SetOfTasks expResult = null;
        SetOfTasks result = Task.getTasksByStatus(status);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
