import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents pilots of the airline system.
 */
public class Pilot extends User{
    //Data fields
    private Scanner input;
    private boolean LogedIn = false;
    private Flight flight;

    /**
     * Constructor
     * @param id
     * @param password
     */
    public Pilot(String id, String password) {
        super(id, password);
        input = new Scanner(System.in);
    }

    /**
     * Login method for pilot.
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
     * Menu for pilot.
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
     * Setter method for pilot.
     * @param flight
     */
    public void setFlight(Flight flight){
        this.flight = flight;
        flight.addCrewMember(this);
    }


    /**
     * Getter method for pilot.
     */
    public Flight getFlight(){
        return flight;
    }

    /**
     * Shows flights for pilot.
     */
    private void showFlight(){
        if(flight == null)
            System.out.println("There is no flight on the schedule");
        else
            System.out.print(flight.toString());
    }

    /**
     * The pilot's methods are tested.
     */
    public static class PilotTester {
        private static final String test_city_file = "cities.txt";
        private static final String test_distances_file = "distances.txt";
        private static final  String test_flights_file = "flights.txt";

        //Unique plane id that will be used for testing
        static FlightSystem system;
        static Pilot pilot;
        static String setoff = "Ankara";
        static String destination = "İstanbul";
        public PilotTester() throws FileNotFoundException {
            system = new FlightSystem(test_city_file,test_distances_file,test_flights_file);
            pilot = new Pilot("test", "test");
        }

        public static void test_setFlight() throws FileNotFoundException {
            System.out.println("Testing set flight method of Pilot ");

            pilot.setFlight(system.getFlights(setoff,destination).peek());
            pilot.showFlight();
        }


        public static void main(String[] args) throws FileNotFoundException {
            PilotTester pilotTester = new PilotTester();
            try {
                pilotTester.test_setFlight();
            } catch (Exception e) {
                System.out.println("ERROR "+ e.getMessage());
            }
        }
    }
}
