import java.io.IOException;
import java.util.Scanner;

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

    /**
     *
     * @param scan Use to scan given file
     * @param isDirected directed or undirected graph
     * @param type The type of graph
     * @return new initialized graph
     */
    public static Graph createGraph(Scanner scan, boolean isDirected, String type){
        int numV = scan.nextInt();
        scan.nextLine();
        AbstractGraph returnValue;
        if (type.equalsIgnoreCase("Matrix")) {
            returnValue = new MatrixGraph(numV, isDirected);
        } else if (type.equalsIgnoreCase("List")) {
            returnValue = new ListGraph(numV, isDirected);
        }
        else {
            throw new IllegalArgumentException();
        }
        returnValue.loadEdgesFromFile(scan);
        return returnValue;
    }

    /**
     * Load the edges of a graph from the data in an input file.
     * The file should contain a series of lines, each line
     * with two or three data values. The first is the source,
     * the second is the destination, and the optional third is the weight.
     * @param scan The Scanner connected to the data file
     */
    public void loadEdgesFromFile(Scanner scan){
        //Completed in non abstract implementations
    }
}
