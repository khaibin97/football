import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphProcessorTest {
    
    GraphProcessor gp;
    String testListFilepath = "/word_list.txt";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        gp = new GraphProcessor();
        gp.populateGraph(testListFilepath);
    }

    @After
    public void tearDown() throws Exception {
        gp = null;
    }

    @Test
    public void testDistance1() {
        int dist = gp.getShortestDistance("comedo", "charge");
        assertEquals("Distance from comedo to charge", 49, dist);
    }
    
    @Test
    public void testPath1() {
        List<String> path = gp.getShortestPath("comedo", "charge");
        String first = path.get(0).toLowerCase();
        int length = path.size();
        String last = path.get(length - 1).toLowerCase();
        assertEquals("Length of path", 49, length);
        assertEquals("Starting point", "comedo", first);
        assertEquals("Ending point", "charge", last);
    }

    @Test
    public void testDistance2() {
        int dist = gp.getShortestDistance("charge", "gimlets");
        assertEquals("Distance from charge to gimlets", 78, dist);
    }
    
    @Test
    public void testPath2() {
        List<String> path = gp.getShortestPath("charge", "gimlets");
        String first = path.get(0).toLowerCase();
        int length = path.size();
        String last = path.get(length - 1).toLowerCase();
        assertEquals("Length of path", 78, length);
        assertEquals("Starting point", "charge", first);
        assertEquals("Ending point", "gimlets", last);
    }
    
    @Test
    public void testDistance3() {
        int dist = gp.getShortestDistance("bellies", "jollies");
        assertEquals("Distance from bellies to jollies", 2, dist);
    }
    
    @Test
    public void testPath3() {
        List<String> path = gp.getShortestPath("bellies", "jollies");
        String first = path.get(0).toLowerCase();
        int length = path.size();
        String last = path.get(length - 1).toLowerCase();
        assertEquals("Length of path", 2, length);
        assertEquals("Starting point", "bellies", first);
        assertEquals("Ending point", "jollies", last);
    }
    
    @Test
    public void testDistance4() {
        int dist = gp.getShortestDistance("define", "shinny");
        assertEquals("Distance from define to shinny", 26, dist);
    }
    
    @Test
    public void testPath4() {
        List<String> path = gp.getShortestPath("define", "shinny");
        String first = path.get(0).toLowerCase();
        int length = path.size();
        String last = path.get(length - 1).toLowerCase();
        assertEquals("Length of path", 26, length);
        assertEquals("Starting point", "define", first);
        assertEquals("Ending point", "shinny", last);
    }
    
    @Test
    public void testZeUpdateGraph() {
        gp.populateGraph("/extra_words.txt");
        
        int dist = gp.getShortestDistance("define", "shinny");
        assertEquals("Distance from define to shinny",5,dist);
        
        List<String> shortestPath = Arrays.asList("define","efine","ehine","shine","shiney","shinny");
        List<String> path = gp.getShortestPath("define", "shinny");
        assertEquals("Shortest path from define to shinny",shortestPath,path);
        
    }
}