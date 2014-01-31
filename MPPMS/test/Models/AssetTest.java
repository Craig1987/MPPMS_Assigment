package Models;

import Models.Asset.AssetType;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AssetTest {
    private Asset asset;
    
    public AssetTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public int testId(){ return 12; }
    public Asset.AssetType testType(){ return AssetType.Video; }
    public int testLength(){ return 123; }
    public String testDescription(){ return "Test description."; }
    
    @Before
    public void setUp() {
        asset = new Asset(testId(), testLength(), testType(), testDescription());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Asset.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        
        assertEquals(testId(), asset.getId());
    }

    /**
     * Test of getLength method, of class Asset.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");

        assertEquals(testLength(), asset.getLength());
    }

    /**
     * Test of getLengthAsString method, of class Asset.
     */
    @Test
    public void testGetLengthAsString() {
        System.out.println("getLengthAsString");

        assertEquals("" + testLength(), asset.getLengthAsString());
    }

    /**
     * Test of getDescription method, of class Asset.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        
        assertEquals(testDescription(), asset.getDescription());
    }

    /**
     * Test of getAssetType method, of class Asset.
     */
    @Test
    public void testGetAssetType() {
        System.out.println("getAssetType");

        assertEquals(testType(), asset.getAssetType());
    }

    /**
     * Test of toString method, of class Asset.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        
        String test = "(ID: " + testId() + ") " + testType() + " asset (Length: " + asset.getLengthAsString() + ") " 
                        + (testDescription().length() > 25 ?     
                                testDescription().substring(0, 25) + "..." : testDescription());
        assertEquals(test, asset.toString());
    }

    /**
     * Test of getAllAssets method, of class Asset.
     */
    @Test
    public void testGetAllAssets() {
        System.out.println("getAllAssets");

        assertEquals(Asset.getAllAssets().getClass(), SetOfAssets.class);
    }

    /**
     * Test of getAssetByID method, of class Asset.
     */
    @Test
    public void testGetAssetByID() {
        System.out.println("getAssetByID");
        
        assertEquals(testId(), asset.getId());
    }
    
}
