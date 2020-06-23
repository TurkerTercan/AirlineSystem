import java.io.FileNotFoundException;
import java.util.*;

/**
 * Environment class for whole the system.
 */
public class AirlineSystem {
    //Data Fields
    private SkipList<User> userSet;
    private FlightSystem flightSystem;
    private Queue<Plane> planeMaintance;

    public AirlineSystem() throws FileNotFoundException {
        planeMaintance = new ArrayDeque<>();
        flightSystem = new FlightSystem();
        userSet = new SkipList<>();
    }





}
