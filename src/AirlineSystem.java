import java.io.FileNotFoundException;
import java.io.IOException;
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
        int option = -1;
        String id;
        String passwd;
        
        System.out.print("------Main Menu------");
        System.out.print("1.Login");
        System.out.print("2.Customer Registration");
        System.out.print("3.Exit");
        try {
            Scanner scan = new Scanner(System.in);    
            while (option == -1) {
                option = scan.nextInt();
                //Read the buffer
                scan.nextLine();
                switch (option) {
                    case 1:
                        option = -1;
                        while (option == -1) {
                            System.out.print("ID:");
                            id = scan.nextLine();
                            System.out.print("Password: ");
                            passwd = scan.nextLine();
                            if (system.userSet.find(new User(id, passwd)) == null) {
                                System.out.print("There is no such registered user in the system!");
                                option = 0;
                            }
                        }
                        break;
                    case 2:
                        
                        break;
                    case 3:
                        //Exit
                        break;
                    default:
                        System.out.println("Please select a valid option.");
                        option = -1;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("The process has failed.");
            System.exit(1);
        }
    }
}
