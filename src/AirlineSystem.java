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

        //A default administrator(id: "admin", passwd: "admin") will be added to the system right after the execution of the program.
        userSet.add(new Admin("admin", "admin", this));
    }
  
    /**
     * Main method which will call mainMenu
     * @param args Commandline arguments
     */
    public static void main(String[] args) {
        try {
            AirlineSystem system = new AirlineSystem();
            mainMenu(system);
        } catch (Exception e) {
            System.out.println("Failed to start the system!\n" + e);
            System.exit(1);
        }
    }

    /**
     * getter method of the userSet skiplist
     */
    public SkipList<User> getUserSet() {
        return userSet;
    }

    /**
     * getter method of the flightSystem
     */
    public FlightSystem getFlightSystem() {
        return flightSystem;
    }

    /**
     * getter method of the plane maintance queue
     */
    public Queue<Plane> getPlaneMaintance() {
        return planeMaintance;
    }
    /**
     * Main menu method of the AirlineSystem
     * Customers or authorized users will be logged in to system or
     * registrated to the system.
     * @param system AirlineSystem parameter to be work on it
     */
    private static void mainMenu(AirlineSystem system) {
        //Data field
        final int EXIT = 0;
        final int STAND = -1;
        int option = STAND;
        String id;
        String passwd;
        String yn;
        User usr;
        System.out.println("------Main Menu------");
        System.out.println("1.Login");
        System.out.println("2.Customer Registration");
        System.out.println("3.Exit");
        try {
            Scanner scan = new Scanner(System.in);    
            //Stay in the main menu until user wants to exit
            while (option == STAND) {
                option = scan.nextInt();
                //Read the buffer
                    scan.nextLine();
                switch (option) {
                    case 1:
                        option = STAND;
                        //Stay in the login menu until user wants to exit
                        while (option == STAND) {
                            System.out.println("ID:");
                            id = scan.nextLine();
                            System.out.println("Password: ");
                            passwd = scan.nextLine();
                            usr = system.userSet.find(new User(id, passwd));

                            //If there is no such user in the system
                            if (usr == null) {
                                System.out.println("There is no such registered user in the system!");
                                System.out.println("Do you want to use customer registration(Y/N) ?");
                                    yn = scan.nextLine();
                                if (yn.charAt(0) == 'Y' || yn.charAt(0) == 'y') {
                                    //Calling static registration method of the user
                                    Customer.registration();
                                } else if (yn.charAt(0) == 'N' || yn.charAt(0) == 'n') {
                                    //Exit from the menu
                                    option = EXIT;
                                } else {
                                    System.out.println("Invalid option.");
                                    option = EXIT;
                                }
                            } else {
                                //If the login is succesfull, call the user's menu
                                usr.menu();
                                option = STAND;
                            }
                        }
                        break;
                    case 2:
                        //Calling static registration method of the user
                        Customer.registration();
                        break;
                    case 3:
                        //Exit from the system
                        option = EXIT;
                        break;
                    default:
                        //Wrong option case
                        System.out.println("Please select a valid option.");
                        option = STAND;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("The process has failed.");
            System.exit(1);
        }
    }
}
