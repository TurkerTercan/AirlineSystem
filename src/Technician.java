import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;

/**
 * Represents technicians of the airline system.
 */
public class Technician extends User {
    //Data fields
    /** Terminal Input */
	private Scanner input;
	/** Represent to if user logedIn */
    private boolean LogedIn = false;
    /** A queue that stores planes need to be maintaince */
    private Queue<Plane> planeMaintance;
    private FlightSystem system;

    /**
     * Constructor
     * @param id Technician's identification
     * @param password Technician's password
     * @param sys FlightSystem
     */
    public Technician(String id, String password, FlightSystem sys) {
        super(id, password);
        input = new Scanner(System.in);
        system = sys;
        planeMaintance = system.getPlaneMaintance();
    }

    /**
     * Login method for technician.
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
     * Menu for technician.
     */
    @Override
    public void menu() {
        int choice = -1;
        while (choice!=0) {
            System.out.println("\nMain menu:");
            System.out.println("Please choose an action:");
            System.out.println("0-Up\n1-Maintenance Confirm\n2-Show Maintenance Airplane Queue");
            System.out.print("\nChoice:");
            choice = input.nextInt();
            switch (choice){
                case 1:
                    maintenanceConfirm();
                    break;

                case 2:
                    showMaintenanceQueue();
                    break;
            }
        }
    }

    /**
     * Maintenance confirm method for technician.
     */
    private void maintenanceConfirm()
    {
        if(planeMaintance.size() != 0) {
            Plane maintain = planeMaintance.poll();
            System.out.println("Plane " + maintain.getId() +" is maintained!");
            system.addPlane(maintain);
            System.out.println("Successfully added to available planes");
        }
        else {
            System.out.println("Plane maintain queue is empty!");
        }
    }

    /**
     * Shows maintenance queue for technician.
     */
    private void showMaintenanceQueue() {
        System.out.print("There are planes waiting to be maintained : ");
        for ( Plane plane: planeMaintance ) {
            System.out.print(plane.getId() + " ");
        }
        System.out.println("");
    }

    /**
     * The technician's methods are tested.
     */
    public static class TechnicianTester {
        private static final String test_city_file = "cities.txt";
        private static final String test_distances_file = "distances.txt";
        private static final String test_flights_file = "flights.txt";
        private static final Queue<Plane> test_queue = new LinkedList<>();

        //Unique plane id that will be used for testing
        static FlightSystem system;
        static Technician technician;
        static String setoff = "Ankara";
        static String destination = "İstanbul";

        public TechnicianTester() throws FileNotFoundException {
            system = new FlightSystem(test_city_file,test_distances_file, test_queue,test_flights_file);
            technician = new Technician("test", "test", system);
            technician.planeMaintance.add(system.getFlights(setoff,destination).peek().getPlane());
            technician.planeMaintance.add(system.getFlights("Londra","Tiflis").peek().getPlane());
        }

        public static void test_showMaintenanceAirplane() {
            System.out.println("Testing set show maintenance airplane queue of Technician ");
            technician.showMaintenanceQueue();
        }
        public static void test_maintenanceConfirm() {
            System.out.println("Testing set maintenance confirm of Technician ");
            technician.maintenanceConfirm();
        }
        public static void main(String[] args) {
            try {
                new TechnicianTester();
                TechnicianTester.test_showMaintenanceAirplane();
                TechnicianTester.test_maintenanceConfirm();
            } catch (Exception e) {
                System.out.println("ERROR "+ e.getMessage());
            }
        }
    }
}
