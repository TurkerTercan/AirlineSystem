import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;

public class MatrixGraph extends AbstractGraph{
    /**
     * A matrix of edge weights
     */
    private double[][] edges;

    /**
     * Construct a matrix graph with the given number of vertices
     * @param numV Number of vertices in the graph
     * @param directed Flag indicating whether the graph is directed or not
     */
    public MatrixGraph(int numV, boolean directed) {
        super(numV, directed);
        edges = new double[numV][numV];
        for (int i = 0; i < numV; i++) {
            for (int j = 0; j < numV; j++) {
                edges[i][j] = Double.POSITIVE_INFINITY; //fill the matrix with positive infinity values
            }
        }
    }

    public void insert(Edge edge) {
        edges[edge.getSource()][edge.getDest()] = edge.getWeight();
        if (!isDirected()) {
            edges[edge.getDest()][edge.getSource()] = edge.getWeight();
        }
    }

    @Override
    public boolean isEdge(int source, int destination) {
        return edges[source][destination] != Double.POSITIVE_INFINITY;
    }

    @SuppressWarnings({"hiding, rawtypes"})
    private class Iter<Edge> implements Iterator {
        /**
         * A list of nodes adjacent to the node of the iterator
         */
        private Queue<Edge> adjacentEdges;

        /**
         * Constructs an iterator for the given set of edges
         * @param adjacentEdges A queue of the edges adjacent to the source node
         */
        public Iter(Queue<Edge> adjacentEdges) {
            this.adjacentEdges = adjacentEdges;
        }

        public boolean hasNext() {
            return adjacentEdges.peek() != null;
        }

        public Object next() {
            return adjacentEdges.poll();
        }

        public void remove() {
            adjacentEdges.poll();
        }

        public String toString() {
            return adjacentEdges.toString();
        }
    }

    @SuppressWarnings({ "unchecked" })
    public Iterator<Edge> edgeIterator(int source) {
        Queue<Edge> adjacentEdges = new ArrayDeque<>();
        int end = edges.length;
        for (int i = 0; i < end; i++) {
            double weight = edges[source][i];
            if (weight != Double.POSITIVE_INFINITY) {
                adjacentEdges.offer(new Edge(source, i, weight));
            }
        }
        return new Iter<Edge>(adjacentEdges);
    }

    public Edge getEdge(int source, int dest){
        //The weight of the edge will be the proper weight if there is an edge
        //or Double.POSITIVE_INFINITY if not
        return new Edge(source, dest, edges[source][dest]);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        int end = edges.length;
        for (int i = 0; i < end; i++) {
            sb.append("Node ").append(i).append("-->\n");
            for (int j = 0; j < end; j++) {
                if (edges[i][j] == Double.POSITIVE_INFINITY) {
                    sb.append("\tnode: ").append(j).append(" is not adjacent to ").append(i).append("\n");
                } else {
                    sb.append("\tnode: ").append(j).append(", weight: ").append(edges[i][j]).append("\n");
                }
            }
        }
        return sb.toString();
    }

    public void loadEdgesFromFile(Scanner scan) {
        //System.out.println(edges.length);
        while(scan.hasNextLine()) {
            //While there are still more edges
            String input = scan.nextLine();
            Scanner sc = new Scanner(input); //scan a line as an edge
            sc.useDelimiter(","); //use only comma delimiter
            try {
                //in case we fail to parse anything, such as format didn't work
                int source = sc.nextInt();
                int dest = sc.nextInt();
                if (sc.hasNextDouble()){
                    edges[source][dest] = sc.nextDouble();
                } else {
                    edges[source][dest] = Edge.UNWEIGHTED_EDGE;
                }
            }
            catch (Exception e) {
                System.out.println("Failed to parse Edge: " + input);
            }
            sc.close(); //close scanner
        }
        scan.close();
    }
}
