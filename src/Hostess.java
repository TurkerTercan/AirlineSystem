import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents hostesses of the airline system.
 */
public class Hostess extends User{
    //Data fields
    /**
     * To scan from terminal
     */
    private Scanner input;
    /**
     * LogIn boolean to checks the users logedin or not
     */
    private boolean LogedIn = false;


    private Flight flight;

    /**
     * Constructor
     * @param id
     * @param password
     */
    public Hostess(String id, String password) {
        super(id, password);
        input = new Scanner(System.in);
    }

    /**
     * Login method for hostess.
     */
    @Override
    public void login() {
        while (!LogedIn) {
            System.out.println("Password: ");
            String PWord = input.next();

            if (!PWord.equals(getPassword())) {
                System.out.println("Authentication failed please try again!!");
            }else LogedIn = !LogedIn;
        }
        menu();
    }

    /**
     * Menu for hostess.
     */
    @Override
    public void menu() {
        int choice = -1;
        while (choice!=0) {
            System.out.println("\nMain menu:");
            System.out.println("Please choose an action:");
            System.out.println("0-Up\n1-Show flight details");
            System.out.print("\nChoice:");
            choice = input.nextInt();
            switch (choice){
                case 0:
                    break;
                case 1:
                    showFlight();
                    break;
                default:
                    System.out.println("Invalid Input!!\n");
            }
        }
    }

   /**
     * Setter method for hostess.
     * @param flight
     */
    public void setFlight(Flight flight){
        this.flight = flight;
        flight.addCrewMember(this);
    }

    /**
     * Getter method for hostess.
     */
    public Flight getFlight(){
        return flight;
    }

    /**
     * Shows flights for hostess.
     */
    private void showFlight(){
        System.out.print(flight.toString());
    }

    public static class HostessTester {
        private static final String test_city_file = "cities.txt";
        private static final String test_distances_file = "distances.txt";
        private static final  String test_flights_file = "flights.txt";

        //Unique plane id that will be used for testing
        static FlightSystem system;
        static Hostess hostess;
        static SkipList<User> users;

        public HostessTester() throws FileNotFoundException {
            system = new FlightSystem(test_city_file,test_distances_file,test_flights_file);
            users = new SkipList<>();
            users.add(new User("test", "test"));

            hostess = new Hostess("test", "test");
        }

        public static void test_setFlight() throws FileNotFoundException {
            System.out.println("Testing set flight method of Hostess ");

            hostess.setFlight(system.getFlights("Ankara","İstanbul").peek());
            hostess.showFlight();
        }

        public static void main(String[] args) throws FileNotFoundException {
            Hostess.HostessTester hostessTester = new Hostess.HostessTester();
            try {
                hostessTester.test_setFlight();
            } catch (Exception e) {
                System.out.println("ERROR "+ e.getMessage());
            }
        }
    }
}
