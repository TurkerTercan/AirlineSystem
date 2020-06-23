import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * FlightSystem class is implemented for storing values of Planes, Graph,
 * Map of the flights and some user's needed methods. All flights stored in a Java.Map interface
 * and all planes stored
 * in a Binary-Balanced Search Tree. Graph's Edges represents a flight coming from source to
 * the destination.
 */
public class FlightSystem {
    //Data Fields
    private static final int MAX_CAPACITY = 50;

    /** BinaryBalancedSearchTree of Planes, Planes are compared with their capacities */
    private TreeSet<Plane> availablePlanes;

    /** Nested Flight Map of PriorityQueue. First key represents SetOff String,
     * second String represents Destination and PriorityQueue is stores all flights
     * from SetOff to Destination
     */
    private Map<String, Map<String,PriorityQueue<Flight>>> flight_map;

    /**
     * Graph's vertices represent cities and Edges between cities are
     * the flights between source to the destination. An Edge's weight
     * is the distance between these two cities.
     */
    private Graph graph;

    /**
     * Distance between two cities
     * it works like distance[source][destination]
     */
    private ArrayList<List<Integer>> distance;

    /**
     * ArrayList of names of cities
     */
    private ArrayList<String> city;

    /**
     * Basic Constructor for FlightSystem
     * Instantiates all data fields and reads two txt with scanner
     * @throws FileNotFoundException If there is no such file
     */
    public FlightSystem() throws FileNotFoundException {
        distance = new ArrayList<>();
        city = new ArrayList<>();
        availablePlanes = new TreeSet<>();
        flight_map = new HashMap<>();
        scanFromFile();
        graph = new ListGraph(MAX_CAPACITY, true);
    }

    /**
     * Reads txt files and stores it in ArrayLists
     * @throws FileNotFoundException If there is no such file
     */
    private void scanFromFile() throws FileNotFoundException {
        Scanner scanCities = new Scanner(new File("cities.txt"));
        Scanner scanDistance = new Scanner(new File("distances.txt"));
        String line;
        int city_c = 0;

        while (scanCities.hasNextLine()) {
            city.add(scanCities.nextLine());
            distance.add(new ArrayList<>());   
            city_c++;
        }
        for (int i = 0, j = 0; i < city_c; i++, j = 0) {
            line = scanDistance.nextLine();
            try {
                Scanner sc = new Scanner(line);
                while (sc.hasNextInt()) {
                    distance.get(i).add(sc.nextInt());
                    j++;
                }
                if (j != city.size()) {
                    System.out.println("There should be exactly "+city.size()+" number of elements in the distance file for all cities");
                    System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Something went wrong");
                System.exit(0);
            }
        }
    }

    /**
     * If Map contains given element returns false, otherwise; Flight is added to PriorityQueue that is
     * Mapped with SetOff and Destination String
     * @param newFlight New flight to be added to the Map
     * @return If Map contains same elements, false; otherwise, a new Flight is added to the Map and return true
     */
    public boolean addFlight(Flight newFlight) {
        String setOff = newFlight.getSetOff();
        String destination = newFlight.getDestination();
        Map<String, PriorityQueue<Flight>> temp = flight_map.get(setOff);
        PriorityQueue<Flight> flight;
        if (temp == null) {
            flight = new PriorityQueue<>();
            flight.add(newFlight);
            temp = new HashMap<>();
            temp.put(destination, flight);
            flight_map.put(setOff, temp);
        } else if (temp.containsKey(destination)) {
            flight = temp.get(destination);
            if (flight.contains(newFlight))
                return false;
            flight.add(newFlight);
        } else {
            flight = new PriorityQueue<>();
            flight.add(newFlight);
            temp.put(destination, flight);
        }

        graph.insert(new Edge(city.indexOf(setOff), city.indexOf(destination),
                distance.get(city.indexOf(setOff)).get(city.indexOf(destination))));
        return true;
    }

    /**
     * Removes given element from the Map. If element is not found return false
     * @param removed The element will be removed
     * @return If element is not found return false
     */
    public boolean removeFlight(Flight removed) {
        String setOff = removed.getSetOff();
        String destination = removed.getDestination();
        Map<String, PriorityQueue<Flight>> temp = flight_map.get(setOff);
        if (temp == null)
            return false;
        if (!temp.containsKey(destination))
            return false;

        PriorityQueue<Flight> flight = temp.get(destination);
        boolean result = flight.remove(removed);
        if (flight.size() == 0) {
            temp.remove(destination);
        }
        if (result) {
            Edge e = graph.getEdge(city.indexOf(setOff), city.indexOf(destination));
            return graph.remove(e);
        } else {
            return false;
        }
    }

    /**
     * @return Returns cities that are recorded in the system
     */
    public ArrayList<String> getCity() {
        return city;
    }

    /**
     * Adds new element to the TreeSet
     * @param plane The object will be added
     */
    public void addPlane(Plane plane) {
        availablePlanes.add(plane);
    }

    /**
     * Removes first element in the TreeSet and returns it
     * @return First Plane of the TreeSet
     */
    public Plane popPlane() {
        Plane temp = availablePlanes.first();
        availablePlanes.remove(temp);
        return temp;
    }

    /**
     * Shows all planes in the TreeSet
     */
    public void ShowAllPlanes() {
        System.out.println(availablePlanes.toString());
    }

    /**
     * Getter method for availablePlanes
     * @return availablePlanes
     */
    public TreeSet<Plane> getAvailablePlanes() {
        return availablePlanes;
    }

    /**
     * Getter method for flight_map
     * @return flight_map
     */
    public Map<String,Map<String, PriorityQueue<Flight>>> getFlight_map() {
        return flight_map;
    }

    /**
     * Gets PriorityQueue between setOff and Destination
     * @param setOff The string that plane's setOff
     * @param destination The string that flight's destination
     * @return PriorityQueue that is contains all flights setOff to Destination
     */
    public PriorityQueue<Flight> getFlights(String setOff, String destination) {
        Map<String,PriorityQueue<Flight>> f = flight_map.get(setOff);
        if (f != null) {
            return flight_map.get(setOff).get(destination);
        } else {
            return null;
        }
    }

    /**
     * If there is no flight between source and destination
     * Customer needs to get shortest path to get to the destination
     * It searches graph with using Dijkstra's Shortest Path Algorithm
     * and finds the Flights that are shortest to get to the destination
     * @param source The customer's setOff String
     * @param destination The customer's destination String
     * @return Flight array that is shortest path
     */
    public Flight[] getPath(String source, String destination) {
        int index_source = city.indexOf(source);
        int index_destination = city.indexOf(destination);

        int[] pred = new int[graph.getNumV()];
        double[] distance = new double[graph.getNumV()];
        dijkstraAlgorithm(graph, index_source, pred, distance);
        //If there no path between them
        if (pred[index_destination] == Double.POSITIVE_INFINITY)
            return null;

        Stack<Integer> temp = new Stack<>();
        while(index_destination != index_source) {
            temp.push(index_destination);
            index_destination = pred[index_destination];
        }
        temp.push(index_source);
        Flight[] temp_flight = new Flight[temp.size() - 1];
        int temp_source = temp.pop();
        int temp_destination;
        int i = 0;
        while(!temp.isEmpty()) {
            temp_destination = temp.pop();
            temp_flight[i++] = flight_map.get(city.get(temp_source)).get(city.get(temp_destination)).peek();
            temp_source = temp_destination;
        }
        return temp_flight;
    }


    /**
     * Dijkstra's Shortest Path Algorithm
     * pre: graph to be searched is a weighted directed graph with only positive weight
     *      pred and dist are arrays of size V
     * @param graph The weighted graph to be searched
     * @param start The start vertex
     * @param pred Output array to contain the predecessors in the shortest path
     * @param dist Output array to contain the distance in the shortest path
     */
    private static void dijkstraAlgorithm(Graph graph, int start, int[] pred, double[] dist) {
        int numV = graph.getNumV();
        HashSet<Integer> vMinusS = new HashSet<>(numV);
        //Initialize V - S
        for(int i = 0; i < numV; i++){
            if(i != start)
                vMinusS.add(i);
        }
        // Initialize pred and dist
        for(int v : vMinusS){
            pred[v] = start;
            dist[v] = graph.getEdge(start, v).getWeight();
        }
        //Main loop
        while(vMinusS.size() != 0){
            //Find the value u in V - S with the smallest dist[u]
            double minDist = Double.POSITIVE_INFINITY;
            int u = -1;
            for(int v : vMinusS){
                if(dist[v] < minDist){
                    minDist = dist[v];
                    u = v;
                }
            }
            // Remove u from vMinusS
            vMinusS.remove(u);
            //Update the distances
            Iterator<Edge> edgeIter = graph.edgeIterator(u);
            while(edgeIter.hasNext()){
                Edge edge = edgeIter.next();
                int v = edge.getDest();
                if(vMinusS.contains(v)){
                    double weight = edge.getWeight();
                    if(dist[u] + weight < dist[v]){
                        dist[v] = dist[u] + weight;
                        pred[v] = u;
                    }
                }
            }
        }
    }
}
