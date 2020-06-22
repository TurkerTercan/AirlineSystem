public abstract class AbstractGraph implements Graph{
    //Data Fields
    /**
     * Number of vertices
     */
    private int numV;
    /**
     * Flag to indicate whether this is a directed graph
     */
    private boolean directed;

    /**
     * Return the number of vertices
     * @return the number of vertices
     */
    @Override
    public int getNumV() {
        return numV;
    }

    /**
     * Determine whether this is a directed graph
     * @return True if this is a directed graph
     */
    @Override
    public boolean isDirected() {
        return directed;
    }

    /**
     * Construct a graph with the specified number of vertices.
     * The boolean directed indicates whether or not this is a directed graph
     * @param numV The number of vertices
     * @param directed The directed flag
     */
    public AbstractGraph(int numV, boolean directed){
        this.numV = numV;
        this.directed = directed;
    }
}
