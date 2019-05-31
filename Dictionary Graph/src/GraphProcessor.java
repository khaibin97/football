import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


/**
 * This class adds additional functionality to the graph as a whole.
 * 
 * Contains an instance variable, {@link #graph}, which stores information for
 * all the vertices and edges.
 * 
 * @see #populateGraph(String) - loads a dictionary of words as vertices in the
 *      graph. - finds possible edges between all pairs of vertices and adds
 *      these edges in the graph. - returns number of vertices added as Integer.
 *      - every call to this method will add to the existing graph. - this
 *      method needs to be invoked first for other methods on shortest path
 *      computation to work.
 * @see #shortestPathPrecomputation() - applies a shortest path algorithm to
 *      precompute data structures (that store shortest path data) - the
 *      shortest path data structures are used later to to quickly find the
 *      shortest path and distance between two vertices. - this method is called
 *      after any call to populateGraph. - It is not called again unless new
 *      graph information is added via populateGraph().
 * @see #getShortestPath(String, String) - returns a list of vertices that
 *      constitute the shortest path between two given vertices, computed using
 *      the precomputed data structures computed as part of
 *      {@link #shortestPathPrecomputation()}. -
 *      {@link #shortestPathPrecomputation()} must have been invoked once before
 *      invoking this method.
 * @see #getShortestDistance(String, String) - returns distance (number of
 *      edges) as an Integer for the shortest path between two given vertices -
 *      this is computed using the precomputed data structures computed as part
 *      of {@link #shortestPathPrecomputation()}. -
 *      {@link #shortestPathPrecomputation()} must have been invoked once before
 *      invoking this method.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class GraphProcessor {

    /**
     * Graph which stores the dictionary words and their associated connections
     */
    private GraphADT<String> graph;

    private List<String>[][] matrix;

    /**
     * Constructor for this class. Initializes instances variables to set the
     * starting state of the object
     */
    public GraphProcessor() {
        this.graph = new Graph<>();
    }

    /**
     * Builds a graph from the words in a file. Populate an internal graph, by
     * adding words from the dictionary as vertices and finding and adding the
     * corresponding connections (edges) between existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph. Repeat for all
     * words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent
     * {@link WordProcessor#isAdjacent(String, String)} If a pair is adjacent, adds
     * an undirected and unweighted edge between the pair of vertices in the graph.
     * 
     * @param filepath
     *            file path to the dictionary
     * @return Integer the number of vertices (words) added
     */
    @SuppressWarnings("unchecked")
    public Integer populateGraph(String filepath) {
        File file;
        BufferedReader br;

        Iterable<String> neighbors;
        Iterator<String> iterator;
        Iterator<String> iteratorTwo;

        String tempA;
        String tempB;
        String input;

        Integer count = 0;
        Integer temp = 0;

        try {
            file = new File(filepath);
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            return null;
        }

        try {
            while (br.ready()) {
                input = br.readLine();
                graph.addVertex(input);
                count++;

            }
            br.close();
        } catch (IOException e) {
            return null;
        }

        neighbors = graph.getAllVertices();
        iterator = neighbors.iterator();
        iteratorTwo = neighbors.iterator();

        // Attempts to add edges
        // Catches random errors.
        try {
            while (iteratorTwo.hasNext()) {
                if (temp == 0) {
                    iteratorTwo.next();
                    temp++;
                }
                tempA = iterator.next();
                tempB = iteratorTwo.next();
                if (WordProcessor.isAdjacent(tempA, tempB)) {
                    graph.addEdge(tempA, tempB);
                }
            }
        } catch (Exception e) {
            System.out.println("Error encountered adding edges: " + e);
        }

        matrix = new ArrayList[count][count];

        return count;

    }

    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary, cat rat hat neat wheat kit shortest path between
     * cat and wheat is the following list of words: [cat, hat, heat, wheat]
     * 
     * @param word1
     *            first word
     * @param word2
     *            second word
     * @return List<String> list of the words
     */
    public List<String> getShortestPath(String word1, String word2) {
        List<String> verticies = (ArrayList<String>) graph.getAllVertices();
        int x = -1;
        int y = -1;
        for(int i = 0; i < verticies.size(); i++) {
        if(verticies.get(i).equals(word1)) x = i;
        if(verticies.get(i).equals(word2)) y = i;
        }
        if( x == -1 || y == -1) {
            System.out.println("One of the words was not found in the graph.");
            return null;
        }
        return matrix[x][y];

    }

    /**
     * Gets the distance of the shortest path between word1 and word2
     * 
     * Example: Given a dictionary, cat rat hat neat wheat kit distance of the
     * shortest path between cat and wheat, [cat, hat, heat, wheat] = 3 (the number
     * of edges in the shortest path)
     * 
     * @param word1
     *            first word
     * @param word2
     *            second word
     * @return Integer distance
     */
    public Integer getShortestDistance(String word1, String word2) {
        List<String> verticies = (ArrayList<String>) graph.getAllVertices();
        int x = -1;
        int y = -1;
        for(int i = 0; i < verticies.size(); i++) {
        if(verticies.get(i).equals(word1)) x = i;
        if(verticies.get(i).equals(word2)) y = i;
        }
        if( x == -1 || y == -1) {
            System.out.println("One of the words was not found in the graph.");
            return null;
        }
        return matrix[x][y].size() - 1;
    }

    /**
     * Computes shortest paths and distances between all possible pairs of vertices.
     * This method is called after every set of updates in the graph to recompute
     * the path information. Any shortest path algorithm can be used (Djikstra's or
     * Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() {
        // Index's
        String word1 = "";
        String word2 = "";
        // All the verticies
        List<String> verticies = (ArrayList<String>) graph.getAllVertices();

        // Set up Path matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                // Make space and get current words
                matrix[i][j] = new ArrayList<String>();
                word1 = verticies.get(i);
                word2 = verticies.get(j);
                matrix[i][j] = getDirections(word1, word2);
            }
        }

    }



    private Map<String, Boolean> vis = new HashMap<String, Boolean>();

    private Map<String, String> prev = new HashMap<String, String>();

    public List<String> getDirections(String start, String finish) {
        List<String> directions = new LinkedList<String>();
        List<String> temp = new LinkedList<String>();
        Queue<String> q = new LinkedList<String>();
        String current = start;
        q.add(current);
        vis.put(current, true);
        while (!q.isEmpty()) {
            current = q.remove();
            if (current.equals(finish)) {
                break;
            } else {
                for (String word : graph.getNeighbors(current)) {
                    if (!vis.containsKey(word)) {
                        q.add(word);
                        vis.put(word, true);
                        prev.put(word, current);
                    }
                }
            }
        }
        if (!current.equals(finish)) {
            System.out.println("can't reach destination");
        }
        for (String word = finish; word != null; word = prev.get(word)) {
            temp.add(word);
        }
        for (int i = temp.size() - 1; i >= 0; i++) {
            directions.add(temp.get(i));
        }
        return directions;

    }
}
