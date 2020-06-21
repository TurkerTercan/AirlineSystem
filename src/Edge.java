/**
 * Edge class for implementing graphs. Vertices are represented by the type int
 */
public class Edge {
    //Data fields
    /**
     * Destination vertex for an edge
     */
    private int destination;
    /**
     * Source vertex for an edge
     */
    private int source;
    /**
     * Weight of an edge
     */
    private double weight;
    /**
     * Base weight for unweighted edges
     */
    public static double UNWEIGHTED_EDGE = 1.0;

    /**
     * Construct an edge from source to dest and sets weight to 1.0
     * @param source The source vertex for the edge
     * @param destination The destination vertex for the edge
     */
    public Edge(int source, int destination) {
        this.source = source;
        this.destination = destination;
        this.weight = UNWEIGHTED_EDGE;
    }

    /**
     * Construct an edge from source to destination with the given weight
     * @param source The source vertex
     * @param destination The destination vertex
     * @param weight The specified weight of the edge
     */
    public Edge(int source, int destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    //Methods

    /**
     * Compares two edges for equality. Edges are equal if their sources and
     * destination vertices are the same.
     *      Weight is not considered.
     * @param e The edge we are comparing to.
     * @return true if their sources and destination vertices are the same,
     *         otherwise; false.
     */
    public boolean equals(Edge e) {
        return (this.source == e.source && this.destination == e.destination);
    }

    /**
     * Getter for destination vertex
     * @return The destination vertex of this edge
     */
    public int getDest(){
        return this.destination;
    }

    /**
     * Getter for source vertex
     * @return The source vertex of this edge
     */
    public int getSource(){
        return this.source;
    }

    /**
     * Getter for the edge weight
     * @return The weight of this edge
     */
    public double getWeight(){
        return this.weight;
    }

    /**
     * Return the hash code for an edge. The hash code depends only on the
     * source and destination
     * @return source and destination hash codes
     */
    public int hashCode(){
        return Integer.hashCode(source) + Integer.hashCode(destination);
    }

    /**
     * String representation of edge
     * @return String representation of edge
     */
    public String toString() {
        return "Source: " + source + ", Destination: " + destination + ", Weight: " + weight;
    }

}
