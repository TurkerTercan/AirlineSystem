import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FlightSystem {
    private TreeSet<Plane> availablePlanes;
    private Map<String, PriorityQueue<Flight>> flight_map;
    private Graph graph;
    private int[][] distance = new int[50][50];
    private String[] city = new String[50];

    public FlightSystem() throws FileNotFoundException {
        availablePlanes = new TreeSet<>();
        flight_map = new HashMap<>();
        scanFromFile("distances.txt", "cities.txt");
        graph = new ListGraph(50, false);
    }

    private void scanFromFile(String distanceTxt, String cityTxt) throws FileNotFoundException {
        Scanner scanDistance = new Scanner(new File(distanceTxt));
        Scanner scanCities = new Scanner(new File(cityTxt));
        for (int i = 0; i < 50; i++) {
            city[i] = scanCities.nextLine();
            for (int j = 0; j < 50; j++) {
                distance[i][j] = scanDistance.nextInt();
            }
        }

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

    public TreeSet<Plane> getAvailablePlanes() {
        return availablePlanes;
    }

    public Map<String, PriorityQueue<Flight>> getFlight_map() {
        return flight_map;
    }
}
