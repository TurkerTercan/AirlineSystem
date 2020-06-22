import java.util.*;

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

    public boolean removeFlight(Flight removed) {
        String setOff = removed.getSetOff();
        PriorityQueue<Flight> temp = flight_map.get(setOff);
        if (temp == null)
            return false;
        if (temp.size() == 1 && temp.contains(removed)) {
            flight_map.remove(setOff);
        } else if(temp.contains(removed)) {
            temp.remove(removed);
        } else {
            return false;
        }
        return true;

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

}
