import java.util.ArrayList;
import java.util.TreeSet;

public class FlightSystem {
    private ArrayList<Plane> avblPlanes;
    private TreeSet<CityNode> flights;

    //Requires implementation of PriorityQueue
    //Set<PriorityQueue<Flight>> flights;
    FlightSystem() {
        avblPlanes = new ArrayList<Plane>();

        //Requires implementation of PriorityQueue
        //flights = new TreeSet<PriorityQueue<Flight>>();
    }
    

    public boolean addFlight(Flight newFlight) {
        String setoff = newFlight.getSetOff();
        if (flights.contains(new CityNode(setoff))) {
            flights.
        }
    }

        public boolean removeFlight() {

        }

        public void showFlights() {
            
        }

}
