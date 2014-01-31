package Models;

import java.util.ArrayList;
import java.util.HashMap;
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
public class ReportTest {
    private Report report;
    
    public ReportTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Report.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Report instance = new Report();
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTitle method, of class Report.
     */
    @Test
    public void testGetTitle() {
        System.out.println("getTitle");
        Report instance = new Report();
        String expResult = "";
        String result = instance.getTitle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTitle method, of class Report.
     */
    @Test
    public void testSetTitle() {
        System.out.println("setTitle");
        String title = "";
        Report instance = new Report();
        instance.setTitle(title);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addComment method, of class Report.
     */
    @Test
    public void testAddComment() {
        System.out.println("addComment");
        Comment comment = null;
        Report instance = new Report();
        instance.addComment(comment);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeComment method, of class Report.
     */
    @Test
    public void testRemoveComment() {
        System.out.println("removeComment");
        Comment comment = null;
        Report instance = new Report();
        instance.removeComment(comment);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Report.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Report instance = new Report();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getComments method, of class Report.
     */
    @Test
    public void testGetComments() {
        System.out.println("getComments");
        Report instance = new Report();
        SetOfComments expResult = null;
        SetOfComments result = instance.getComments();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clearAndNullifyAll method, of class Report.
     */
    @Test
    public void testClearAndNullifyAll() {
        System.out.println("clearAndNullifyAll");
        Report.clearAndNullifyAll();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of save method, of class Report.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        Report instance = new Report();
        boolean expResult = false;
        boolean result = instance.save();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAttributesAndValues method, of class Report.
     */
    @Test
    public void testGetAttributesAndValues() {
        System.out.println("getAttributesAndValues");
        boolean includeId = false;
        Report instance = new Report();
        HashMap<String, String> expResult = null;
        HashMap<String, String> result = instance.getAttributesAndValues(includeId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInnerAttributesAndValues method, of class Report.
     */
    @Test
    public void testGetInnerAttributesAndValues() {
        System.out.println("getInnerAttributesAndValues");
        Report instance = new Report();
        ArrayList<HashMap<String, Object>> expResult = null;
        ArrayList<HashMap<String, Object>> result = instance.getInnerAttributesAndValues();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllReports method, of class Report.
     */
    @Test
    public void testGetAllReports() {
        System.out.println("getAllReports");
        SetOfReports expResult = null;
        SetOfReports result = Report.getAllReports();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReportByID method, of class Report.
     */
    @Test
    public void testGetReportByID() {
        System.out.println("getReportByID");
        int id = 0;
        Report expResult = null;
        Report result = Report.getReportByID(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
