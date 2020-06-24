import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;



public class FlightSystemTester {
    private static String time_test = "20/06/2020,14:30";
    private static final int LOWER_TEST_COUNT = 20;

    public static void main(String[] args) throws FileNotFoundException {
        FlightSystem flight = new FlightSystem();
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
        //System.out.println("Testing adding/removing flights from/to the system");
            //test_removeFlight(LOWER_TEST_COUNT);
        test_findShortestFlights(LOWER_TEST_COUNT);
        System.out.println();

    }

    private static FlightSystem test_addFlight(int test_count) throws FileNotFoundException {
        System.out.println("Adding "+test_count+" flights to the system.");
        System.out.println("Expected Output: False if there is already a flight with the same properties, otherwise; true.");
        
        FlightSystem system = new FlightSystem();
        ArrayList<String> cities = system.getCity();
        int city_size = cities.size();
        int f_id = 1;
        int p_id = 1;

        Random rand = new Random();
        for (int i = 0; i < test_count; i++) {
            String c1 = cities.get(rand.nextInt(city_size));
            String c2 = cities.get(rand.nextInt(city_size));

            System.out.print("\tAdding a new flight: "+c1+" -> "+c2+": ");
            System.out.println(system.addFlight(new Flight(String.valueOf(f_id++), new Plane(String.valueOf(p_id++), 50), c2, c1, time_test, 500)));
        }
        return system;
    }

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
                            
                            System.out.println(system.removeFlight(new Flight(f3.getID(), new Plane(String.valueOf(p_id++), 50), f3.getDestination(), f3.getSetOff(), time_test, 500)));
                        }
                    }
                }
            }
        }
        return system;
    }

    private static FlightSystem test_findShortestFlights(int test_count) throws FileNotFoundException {
        System.out.println("Finds shortest flights between two cities if there is not any flight between them");
        System.out.println("Excepted Output: Prints an Flight array between source to destination");
        FlightSystem system = new FlightSystem();
        int f_id = 1;
        int p_id = 1;
        ArrayList<String> cities = system.getCity();
        String[] city = new String[test_count];
        for (int i = 0; i < city.length; i++) {
            city[i] = cities.get(i);
        }

        for (int i = 0; i < city.length - 1; i++) {
            System.out.println(system.addFlight(new Flight(String.valueOf(f_id++), new Plane(String.valueOf(p_id++), 50), city[i], city[i + 1], time_test, 500)));
        }


        PriorityQueue<Flight> test = system.getFlights(city[0],city[city.length - 1]);
        System.out.println("There is no flight between " + city[0] + " and " + city[city.length - 1] + ": " + (test == null));
        Flight[] array = system.getPath(city[0], city[city.length - 1]);
        System.out.println("There is " + array.length + " transfer cities founded");
        for (Flight f : array) {
            System.out.println(f.toString());
        }

        return system;
    }

}
