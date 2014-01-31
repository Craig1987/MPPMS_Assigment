package Models;

import java.text.SimpleDateFormat;
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
    
    public int testId() { return 1; }
    public SetOfComments testComments() { return report.getComments(); }
    public String testTitle() { return "Test title"; }
    
    @Before
    public void setUp() {
        report = new Report(testId());
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
        
        assertEquals(testId(), report.getId());
    }

    /**
     * Test of getTitle method, of class Report.
     */
    @Test
    public void testGetTitle() {
        System.out.println("getTitle");
         
        report.setTitle(testTitle());
        assertEquals(testTitle(), report.getTitle());
    }

    /**
     * Test of setTitle method, of class Report.
     */
    @Test
    public void testSetTitle() {
        System.out.println("setTitle");
        
        report.setTitle(testTitle());
        assertEquals(testTitle(), report.getTitle());
    }

    /**
     * Test of addComment method, of class Report.
     */
    @Test
    public void testAddComment() {
        System.out.println("addComment");
        
        Comment test = new Comment();
        report.addComment(test);
        
        SetOfComments comments = report.getComments();
        assertTrue(comments.contains(test));
    }

    /**
     * Test of toString method, of class Report.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        
        String test = (report.getComments().size() > 0 ? report.getComments().size() + " comment" + (report.getComments().size() > 1 ? "s" : "")
            + " (last edited " + new SimpleDateFormat("dd MMM yyyy").format(report.getComments().get(report.getComments().size() - 1).getDate()) + ")"
            : "Blank report");
        assertEquals(test, report.toString());
    }

    /**
     * Test of getComments method, of class Report.
     */
    @Test
    public void testGetComments() {
        System.out.println("getComments");
        
        assertEquals(report.getComments().getClass(), SetOfComments.class);
    }

    /**
     * Test of getAllReports method, of class Report.
     */
    @Test
    public void testGetAllReports() {
        System.out.println("getAllReports");
        
        assertEquals(Report.getAllReports().getClass(), SetOfReports.class);
    }

    /**
     * Test of getReportByID method, of class Report.
     */
    @Test
    public void testGetReportByID() {
        System.out.println("getReportByID");
        
        
        assertEquals(report.getId(), Report.getReportByID(report.getId()).getId());
    }
    
}
