import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FlightSystem {
    private TreeSet<Plane> availablePlanes;
    private Map<String, Map<String,PriorityQueue<Flight>>> flight_map;
    private Graph graph;
    private ArrayList<List<Integer>> distance;
    private ArrayList<String> city;

    public FlightSystem() throws FileNotFoundException {
        distance = new ArrayList<List<Integer>>();
        city = new ArrayList<String>();
        availablePlanes = new TreeSet<>();
        flight_map = new HashMap<>();
        scanFromFile("distances.txt", "cities.txt");
        graph = new ListGraph(50, false);
    }

    private void scanFromFile(String distanceTxt, String cityTxt) throws FileNotFoundException {
        Scanner scanDistance = new Scanner(new File(distanceTxt));
        Scanner scanCities = new Scanner(new File(cityTxt));
        for (int i = 0; i < 50; i++) {
            distance.add(new ArrayList<Integer>());
            city.add(scanCities.nextLine());
            for (int j = 0; j < 50; j++) {
                distance.get(i).add(scanDistance.nextInt());
            }
        }
    }    

    private int[] shortestPath(Graph graph, int start){
		Queue<Integer> theQueue = new LinkedList<Integer>();
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



    public boolean removeFlight(Flight removed) {
        String setOff = removed.getSetOff();
        String destination = removed.getDestination();
        Map<String, PriorityQueue<Flight>> temp = flight_map.get(setOff);
        if (temp == null)
            return false;
        if (!temp.containsKey(destination))
            return false;
        PriorityQueue<Flight> flight = temp.get(destination);
        return flight.remove(removed);
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

    public void showFlights() {
            
    }

    public TreeSet<Plane> getAvailablePlanes() {
        return availablePlanes;
    }

    public Map<String, PriorityQueue<Flight>> getFlight_map() {
        return flight_map;
    }
}
