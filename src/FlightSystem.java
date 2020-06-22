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
        graph = new ListGraph(50, true);
    }

    /**
     * Reads txt files and stores it in ArrayLists
     * @throws FileNotFoundException If there is no such file
     */
    private void scanFromFile() throws FileNotFoundException {
        Scanner scanDistance = new Scanner(new File("distances.txt"));
        Scanner scanCities = new Scanner(new File("cities.txt"));
        for (int i = 0; i < 50; i++) {
            distance.add(new ArrayList<>());
            city.add(scanCities.nextLine());
            for (int j = 0; j < 50; j++) {
                distance.get(i).add(scanDistance.nextInt());
            }
        }
    }    

    private int[] shortestPath(Graph graph, int start){
		Queue<Integer> theQueue = new LinkedList<>();
		//Declare array parent and initialize its elements to -1
		int[] parent = new int[graph.getNumV()];
		for(int i = 0; i < graph.getNumV(); i++){
			parent[i] = -1;
		}
		//Declare array identified and initialize its elements to false
		boolean[] identified = new boolean[graph.getNumV()];
		/* Mark the start vertex as identified and insert it into the queue */
		identified[start] = true;
		theQueue.offer(start);
		
		/* Perform breadth-first search until done */
		while(!theQueue.isEmpty()){
			/* Take a vertex, current, out of the queue. Begin visiting current */
			int current = theQueue.remove();
			/* Examine each vertex, neighbor, adjacent to current. */
			Iterator<Edge> itr = graph.edgeIterator(current);
			while(itr.hasNext()){
				Edge edge = itr.next();
				int neighbor = edge.getDest();
				//If neighbor has not been identified
				if(!identified[neighbor]){
					//Mark it identified
					identified[neighbor] = true;
					//Place it into the queue
					theQueue.offer(neighbor);
					/* Insert the edge (current, neighbor) into the tree */
					parent[neighbor] = current;
				}
			}
			//Finished visiting current
		}
		return parent;
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
        }
        if (temp.containsKey(destination)) {
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
            return graph.remove(new Edge(city.indexOf(setOff), city.indexOf(destination)));  
        } else {
            return false;
        }
    }

    public void addPlane(Plane plane) {
        availablePlanes.add(plane);
    }

    public Plane peekPlane() {
        Plane temp = availablePlanes.first();
        availablePlanes.remove(temp);
        return temp;
    }

    public void ShowAllPlanes() {
        System.out.println(availablePlanes.toString());
    }

    public TreeSet<Plane> getAvailablePlanes() {
        return availablePlanes;
    }

    public Map<String,Map<String, PriorityQueue<Flight>>> getFlight_map() {
        return flight_map;
    }

    public PriorityQueue<Flight> getFlights(String setOff, String destination) {
        return flight_map.get(setOff).get(destination);
    }
}
