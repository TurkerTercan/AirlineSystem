import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class AirlineSystem {
    //Data Fields
    private Set<User> userSet = new HashSet<User>(){
        @Override
        public boolean contains(Object o) {
            for (User u : userSet) {
                if (u.getID() == ((User) o).getID()) return true;
            }
            return false;
        }
    };
    private FlightSystem flightSystem;
    private Queue<Plane> planeMaintance;

}
