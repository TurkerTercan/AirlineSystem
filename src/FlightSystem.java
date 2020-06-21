import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class FlightSystem {
    private TreeSet<Plane> availablePlanes;
    private Map<String, PriorityQueue<Flight>> flight_map;

    public FlightSystem() {
        availablePlanes = new TreeSet<>();
        flight_map = new HashMap<>();
    }

    public boolean addFlight(Flight newFlight) {
        String setOff = newFlight.getSetOff();
        PriorityQueue<Flight> temp = flight_map.get(setOff);
        if (temp != null && temp.contains(newFlight))
            return false;

        if (temp == null) {
            temp = new PriorityQueue<>();
            temp.offer(newFlight);
            flight_map.put(setOff, temp);
        } else {
            temp.offer(newFlight);
        }
        return true;
    }

        public boolean removeFlight() {
            return false;
        }

        public void showFlights() {
            
        }

}
