import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
    /**
     * Instance variables and constructors
     */
    private ArrayList<E> vertices = new ArrayList<E>();
    private boolean[][] edgeMatrix = null;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {
        if (vertex == null)
            return null;
        for (E v : vertices)
            if (v.equals(vertex))
                return null;
        vertices.add(vertex);
        expandEdgeMatrix();
        return vertex;
    }

    private void expandEdgeMatrix() {
        if (edgeMatrix == null) {
            edgeMatrix = new boolean[1][1];
            return;
        }
        boolean[][] newMatrix = new boolean[vertices.size()][vertices.size()];
        for (int row = 0; row < edgeMatrix.length; row++)
            for (int col = 0; col < edgeMatrix[row].length; col++)
                newMatrix[row][col] = edgeMatrix[row][col];
        edgeMatrix = newMatrix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
        
        if (vertex == null)
            return null;
        if (!vertices.contains(vertex))
            return null;
        
        int index = vertices.indexOf(vertex);
        
        if (edgeMatrix.length == 1) {
            edgeMatrix = null;
        } else {
            int newI = 0;
            int newJ = 0;
            boolean[][] newMatrix = new boolean[vertices.size() - 1][vertices.size() - 1];
            for (int row = 0; row < edgeMatrix.length; row++) {
                if (row != index) { //skip the row to be deleted
                    for (int col = 0; col < edgeMatrix[row].length; col++) {
                        if (col != index) { //skip the column to be deleted
                            newMatrix[newI][newJ] = edgeMatrix[row][col];
                            newJ++;
                        }
                    }
                    newI++;
                    newJ = 0;
                }
            }
            edgeMatrix = newMatrix;
        }
        
        E temp = vertices.get(index);
        vertices.remove(vertex);
        return temp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        if (vertex1.equals(vertex2))
            return false;
        if (!vertices.contains(vertex1) || !vertices.contains(vertex2))
            return false;
        int index1 = vertices.indexOf(vertex1);
        int index2 = vertices.indexOf(vertex2);
        edgeMatrix[index1][index2] = true;
        edgeMatrix[index2][index1] = true;
        // TODO Clarify what to do if an edge already exists (return false?)
        // I would say do nothing and return false, so that it knows no edge is added, 
        // plus, adding a duplicate edge would be a flaw in the 
        // GraphProcessor algorithm, i think.
        return true;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        if (vertex1.equals(vertex2))
            return false;
        if (!vertices.contains(vertex1) || !vertices.contains(vertex2))
            return false;
        int index1 = vertices.indexOf(vertex1);
        int index2 = vertices.indexOf(vertex2);
        edgeMatrix[index1][index2] = false;
        edgeMatrix[index2][index1] = false;
        // TODO Clarify what to do if an edge doesn't exist (return false?)
        // Same as adding an edge. The thing is, no error would occur, since we are overwriting the matrix
        // Counting is the problem, which cant be done in the Graph ADT level
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        if (vertex1.equals(vertex2))
            return false;
        if (!vertices.contains(vertex1) || !vertices.contains(vertex2))
            return false;
        int index1 = vertices.indexOf(vertex1);
        int index2 = vertices.indexOf(vertex2);
        return edgeMatrix[index1][index2];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        ArrayList<E> neighbors = new ArrayList<E>();
        int vertIndex = vertices.indexOf(vertex);
        for (int i = 0; i < edgeMatrix[vertIndex].length; i++) {
            if (edgeMatrix[vertIndex][i])
                neighbors.add(vertices.get(i));
        }
        return neighbors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
        return vertices;
    }

}