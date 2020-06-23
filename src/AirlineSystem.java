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
  
    public static void main(String[] args) {
        try {
            AirlineSystem system = new AirlineSystem();
            mainMenu(system);
        } catch (Exception e) {
            System.out.println("Failed to start the system!\n" + e);
            System.exit(1);
        }
    }

    private static void mainMenu(AirlineSystem system) {
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
                        while (option == STAND) {
                            System.out.println("ID:");
                            id = scan.nextLine();
                            System.out.println("Password: ");
                            passwd = scan.nextLine();
                            usr = system.userSet.find(new User(id, passwd));
                            if (usr == null) {
                                System.out.println("There is no such registered user in the system!");
                                System.out.println("Do you want to use customer registration(Y/N) ?");
                                    yn = scan.nextLine();
                                if (yn.charAt(0) == 'Y' || yn.charAt(0) == 'y') {
                                    //Registration
                                } else if (yn.charAt(0) == 'N' || yn.charAt(0) == 'n') {
                                    //Exit from the menu
                                    option = EXIT;
                                } else {
                                    System.out.println("Invalid option.");
                                    option = EXIT;
                                }
                            } else {
                                usr.menu();
                                option = STAND;
                            }
                        }
                        break;
                    case 2:
                        
                        break;
                    case 3:
                        option = EXIT;
                        break;
                    default:
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
