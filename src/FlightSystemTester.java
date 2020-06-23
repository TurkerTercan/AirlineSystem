import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import sun.net.www.content.text.plain;

public class FlightSystemTester {
    private static final String time_test = "20/06/2020,14:30";
    private static final int LOWER_TEST_COUNT = 20;
    private static final int TEST_PRICE = 500;
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
        test_removeFlight(LOWER_TEST_COUNT);
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
            System.out.println(system.addFlight(new Flight(String.valueOf(f_id++), new Plane(String.valueOf(p_id++), 50), c2, c1, time_test, TEST_PRICE)));
        }
        return system;
    }

    private static FlightSystem test_removeFlight(int test_count) throws FileNotFoundException {
        System.out.println("Removing all flights of a system that has "+test_count+" flights recorded.");
        FlightSystem system = test_addFlight(test_count);
        ArrayList<String> cities = system.getCity();
        int city_size = cities.size();
        Map<String,Map<String, PriorityQueue<Flight>>> flights = system.getFlight_map();

        for (int i = 0; i < city_size; i++) {
            for (int j = 0; j < city_size; j++) {
                PriorityQueue<Flight> f2 = system.getFlights(cities.get(i), cities.get(j));
                if (f2 != null) {
                    for (int k = 0; k < f2.size(); k++) {
                        Flight f3 = f2.peek();
                        System.out.println("\tRemoving the flight: "+f3.getSetOff()+" -> "+f3.getDestination()+": "+system.removeFlight(f3));
                    }
                }
            }
        }
        return system;
    }
}
