import java.util.Iterator;

/**
 * Interface to specify a Graph ADT. A graph is set of vertices and set of edges.
 *      Vertices are represented by integers from 0 to n - 1
 *      Edges are ordered pairs of vertices
 * Each implementation of the Graph interface should provide a constructor that
 * specifies the number of vertices and whether or not the graph is directed
 */
public interface Graph {
    //Interface methods

    /**
     * Return the number of vertices
     * @return the number of vertices
     */
    int getNumV();

    /**
     * Determine whether this is a directed graph
     * @return True if this is a directed graph
     */
    boolean isDirected();

    /**
     * Insert a new edge into the graph
     * @param e The new edge
     */
    void insert(Edge e);

    /**
     * Determine whether an edge exists
     * @param source The source vertex
     * @param destination The destination vertex
     * @return true if there is an edge from source to dest
     */
    boolean isEdge(int source, int destination);

    /**
     * Get edge between two vertices
     * @param source The source vertex
     * @param destination The destination vertex
     * @return The Edge between these two vertices,
     *         or an Edge with a weight of
     *         Double.POSITIVE_INFINITY if there is no edge
     */
    Edge getEdge(int source, int destination);

    /**
     * Return an iterator to the edges connected to a given vertes
     * @param source The source vertex
     * @return An Iterator Edge to the vertices connected to source
     */
    Iterator<Edge> edgeIterator(int source);
}
