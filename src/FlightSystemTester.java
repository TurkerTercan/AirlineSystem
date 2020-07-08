import java.io.FileNotFoundException;
import java.util.*;

/** Class to test FlightSystem class */
public class FlightSystemTester {
    private static String time_test = "20/06/2020,14:30";
    private static final int LOWER_TEST_COUNT = 20;

    private static final String test_city_file = "cities.txt";
    private static final String test_distances_file = "distances.txt";
    private static final String test_flights_file = "flights.txt";
    private static final Queue<Plane> test_queue = new LinkedList<>();

    public static void main(String[] args) throws FileNotFoundException {
        FlightSystem flight = new FlightSystem(test_city_file, test_distances_file,test_queue);
        flight.addPlane(new Plane(100));
        flight.addPlane(new Plane(150));
        flight.addPlane(new Plane(170));
        flight.addPlane(new Plane(180));
        flight.addPlane(new Plane(10));
        flight.addPlane(new Plane(135));
        flight.addPlane(new Plane(200));
        flight.addPlane(new Plane(101));

        flight.ShowAllPlanes();
        //test_addFlight(LOWER_TEST_COUNT);
        System.out.println("Testing adding/removing flights from/to the system");
        test_removeFlight(LOWER_TEST_COUNT);
        test_findShortestFlights(5);
    }

    /**
     * Creates flights and adds to the system
     * @param test_count How many test flights will be created
     * @return created FlightSystem
     * @throws FileNotFoundException if file not found
     */
    private static FlightSystem test_addFlight(int test_count) throws FileNotFoundException {
        System.out.println("Adding "+test_count+" flights to the system.");
        System.out.println("Expected Output: False if there is already a flight with the same properties, otherwise; true.");
        
        FlightSystem system = new FlightSystem(test_city_file, test_distances_file, test_queue);
        ArrayList<String> cities = system.getCity();
        int city_size = cities.size();
        int f_id = 1;
        int p_id = 1;

        Random rand = new Random();
        for (int i = 0; i < test_count; i++) {
            String c1 = cities.get(rand.nextInt(city_size));
            String c2 = cities.get(rand.nextInt(city_size));

            System.out.print("\tAdding a new flight: "+c1+" -> "+c2+": ");
            System.out.println(system.addFlight(new Flight(String.valueOf(f_id++), new Plane(String.valueOf(p_id++), 50), c1, c2, time_test, 500)));
        }
        return system;
    }

    /**
     * Firstly creates flights and then, removes it
     * @param test_count How many test flights will be created and removed
     * @return created FlightSystem
     * @throws FileNotFoundException if file not found
     */
    private static FlightSystem test_removeFlight(int test_count) throws FileNotFoundException {
        System.out.println("Removing all flights of a system that has "+test_count+" flights recorded.");
        System.out.println("Expected Output: False if there is no such flight in the system or graph, otherwise; true.");

        FlightSystem system = test_addFlight(test_count);
        ArrayList<String> cities = system.getCity();
        int city_size = cities.size();
        Map<String,Map<String, PriorityQueue<Flight>>> flights = system.getFlight_map();
        int p_id = 1;
        for (int i = 0; i < city_size; i++) {
            Map<String, PriorityQueue<Flight>> f = flights.get(cities.get(i));
            if (f != null) {
                for (int j = 0; j < city_size; j++) {
                    PriorityQueue<Flight> f2 = f.get(cities.get(j));
                    if (f2 != null) {
                        for (int k = 0; k < f2.size(); k++) {
                            Flight f3 = f2.peek();
                            System.out.print("\tRemoving the flight: "+f3.getSetOff()+" -> "+f3.getDestination()+": ");
                            
                            System.out.println(system.removeFlight(new Flight(f3.getID(), new Plane(String.valueOf(p_id++), 50), f3.getSetOff(), f3.getDestination(), time_test, 500)));
                        }
                    }
                }
            }
        }
        return system;
    }

    /**
     * To test getPath method
     * @param test_count How many transfer points will be
     * @return Created FlightSystem
     * @throws FileNotFoundException if file not found
     */
    private static FlightSystem test_findShortestFlights(int test_count) throws FileNotFoundException {
        System.out.println("Finds shortest flights between two cities if there is not any flight between them");
        System.out.println("Excepted Output: Prints an Flight array between source to destination");
        FlightSystem system = new FlightSystem(test_city_file, test_distances_file, test_queue);
        int f_id = 1;
        int p_id = 1;
        ArrayList<String> cities = system.getCity();
        String[] city = new String[test_count];
        for (int i = 0; i < city.length; i++) {
            city[i] = cities.get(i);
        }

        for (int i = 0; i < city.length - 1; i++) {
            System.out.println(system.addFlight(new Flight(String.valueOf(f_id++),
                    new Plane(String.valueOf(p_id++), 50), city[i], city[i + 1], time_test, 500)));
        }

        //System.out.println(system.addFlight(new Flight(String.valueOf(f_id++),
         //       new Plane(String.valueOf(p_id++), 50), city[5], city[10], time_test, 500)));



        PriorityQueue<Flight> test = system.getFlights(city[0],city[city.length - 1]);
        System.out.println("There is no flight between " + city[0] + " and " + city[city.length - 1] + ": " + (test == null));
        Flight[] array = system.getPath(city[0], city[city.length - 1]);
        if (array == null)
            System.out.println("There is no transfer point between those cities");
        else {
            System.out.println("There is " + array.length + " transfer cities founded");
            for (Flight f : array) {
                System.out.println(f.toString());
            }
        }


        return system;
    }

}
