package Models;

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
public class ComponentTest {
    private Component component;
    
    public ComponentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public int testId(){ return 99; }
    public SetOfAssets testAssets(){ return Asset.getAllAssets(); }
    public String testDescription(){ return "Test description."; }
    
    @Before
    public void setUp() {
        component = new Component(testId(), testDescription());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Component.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");

        assertEquals(testId(), component.getId());
    }

    /**
     * Test of getDescription method, of class Component.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");

        assertEquals(testDescription(), component.getDescription());
    }

    /**
     * Test of getNumberOfAssets method, of class Component.
     */
    @Test
    public void testGetNumberOfAssets() {
        System.out.println("getNumberOfAssets");
        
        assertEquals(testAssets().size(), component.getNumberOfAssets());
    }

    /**
     * Test of getAssets method, of class Component.
     */
    @Test
    public void testGetAssets() {
        System.out.println("getAssets");
        
        component.setAssets(testAssets());
        assertEquals(testAssets(), component.getAssets());
    }

    /**
     * Test of addAsset method, of class Component.
     */
    @Test
    public void testAddAsset() {
        System.out.println("addAsset");
        Asset asset = new Asset();

        component.addAsset(asset);
        SetOfAssets assets = component.getAssets();
        assertTrue(assets.contains(asset));
    }

    /**
     * Test of clearAndNullifyAll method, of class Component.
     */
    @Test
    public void testClearAndNullifyAll() {
        System.out.println("clearAndNullifyAll");
        
        Component.clearAndNullifyAll();
        assertEquals(null, Component.getAllComponents());
    }

    /**
     * Test of toString method, of class Component.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        
        String test = "(ID: " + testId() + ") " + testDescription();
        assertEquals(test, component.toString());
    }

    /**
     * Test of getAllComponents method, of class Component.
     */
    @Test
    public void testGetAllComponents() {
        System.out.println("getAllComponents");

        assertEquals(Component.getAllComponents(), instanceOf(Component.class));
    }

    /**
     * Test of getComponentByID method, of class Component.
     */
    @Test
    public void testGetComponentByID() {
        System.out.println("getComponentByID");
        
        assertEquals(testId(), component.getId());
    }
    
}
