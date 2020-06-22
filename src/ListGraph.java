import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListGraph extends AbstractGraph{
    //Data Fields
    /**
     * An array of Lists to contain the edges that originate with each vertex
     */
    private List<Edge>[] edges;

    /**
     * Construct a graph with the specified number of vertices and directionality
     * @param numV The number of vertices
     * @param directed The directionality flag
     */
    @SuppressWarnings("unchecked")
    public ListGraph(int numV, boolean directed) {
        super(numV, directed);
        edges = new List[numV];
        for (int i = 0; i < numV; i++) {
            edges[i] = new LinkedList<>();
        }
    }

    /**
     * Determine whether an edge exists
     * @param source      The source vertex
     * @param destination The destination vertex
     * @return true if there is an edge from source to dest
     */
    public boolean isEdge(int source, int destination) {
        return edges[source].contains(new Edge(source, destination));
    }

    /**
     * Insert a new edge into the graph
     * @param edge The new edge
     */
    public void insert(Edge edge) {
        edges[edge.getSource()].add(edge);
        if (!isDirected()) {
            edges[edge.getDest()].add(new Edge(edge.getDest(), edge.getSource(), edge.getWeight()));
        }
    }

    /**
     * Removes an existing edge from the graph
     * @param edge Edge to be removed
     */
    public boolean remove(Edge edge) {
        if (edges[edge.getSource()].isEmpty()) {
            return false;
        } else {
            return edges[edge.getSource()].remove(edge);
        }
    }

    /**
     * Return an iterator to the edges connected to a given vertes
     * @param source The source vertex
     * @return An Iterator Edge to the vertices connected to source
     */
    @Override
    public Iterator<Edge> edgeIterator(int source) {
        return edges[source].iterator();
    }

    /**
     * Get edge between two vertices
     *
     * @param source      The source vertex
     * @param destination The destination vertex
     * @return The Edge between these two vertices,
     * or an Edge with a weight of
     * Double.POSITIVE_INFINITY if there is no edge
     */
    @Override
    public Edge getEdge(int source, int destination) {
        Edge target = new Edge(source, destination, Double.POSITIVE_INFINITY);
        for (Edge edge: edges[source]){
            if (edge.equals(target))
                return edge;
        }
        return target;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int end = edges.length;
        for (int i = 0; i < end; i++) {
            sb.append("Node ").append(i).append("-->\n");
            for (Edge e : edges[i]) {
                sb.append("\tnode: ").append(e.getDest()).append(", weight: ").append(e.getWeight()).append("\n");
            }
        }
        return sb.toString();
    }
}
