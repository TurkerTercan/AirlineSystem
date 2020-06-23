import java.io.FileNotFoundException;
import java.util.Arrays;

public class FlightSystemTester {
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
        flight.addFlight(new Flight("1", flight.popPlane(), ""))



    }

    private static test_init() {
        FlightSystem flight = new FlightSystem();
    }
}
