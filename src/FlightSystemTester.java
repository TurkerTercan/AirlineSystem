import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import sun.net.www.content.text.plain;

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
        test_addFlight(LOWER_TEST_COUNT);
    }

    private static FlightSystem test_addFlight(int test_count) throws FileNotFoundException {
        System.out.println("Testing addFlight for "+test_count+" times");
        System.out.println("Expected Output: False if there is already a flight with the same properties, otherwise; true.");
        Random rand = new Random();
        FlightSystem system = new FlightSystem();
        ArrayList<String> cities = system.getCity();
        int city_size = cities.size();
        int f_id = 1;
        int p_id = 1;
        for (int i = 0; i < test_count; i++) {
            String c1 = cities.get(rand.nextInt(city_size));
            String c2 = cities.get(rand.nextInt(city_size));

            System.out.print("\tAdding a new flight: "+c1+" - "+c2+": ");
            System.out.println(system.addFlight(new Flight(String.valueOf(f_id++), new Plane(String.valueOf(p_id++), 50), c1, c2, time_test, 500)));
        }

        return system;
    }
}
