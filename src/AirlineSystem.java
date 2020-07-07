import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Environment class for whole the system.
 */
public class AirlineSystem {
    //Data Fields
    private SkipList<User> userSet;
    private FlightSystem flightSystem;
    private Queue<Plane> planeMaintance;

    private AirlineSystem() throws FileNotFoundException {
        planeMaintance = new ArrayDeque<>();
        userSet = new SkipList<>();
        //A default administrator(id: "admin", passwd: "admin") will be added to the system right after the execution of the program.
        userSet.add(new Admin("admin", "admin", this));
    }

    public AirlineSystem(String file_city, String file_distance) throws FileNotFoundException {
        this();
        flightSystem = new FlightSystem(file_city, file_distance);
    }

    public AirlineSystem(String file_city, String file_distance, String file_flights) throws FileNotFoundException {
        this();
        flightSystem = new FlightSystem(file_city, file_distance, file_flights);
    }

    public AirlineSystem(String file_city, String file_distance, String file_flight, String file_user) throws FileNotFoundException {
        this(file_city, file_distance, file_flight);
        ScanUsersFromFile(file_user);
        AssignCrew();
    }

    private void AssignCrew() {
        Iterator<User> itr = userSet.iterator();
        ArrayList<Pilot> pilots = new ArrayList<>();
        ArrayList<Hostess> hostesses = new ArrayList<>();
        while(itr.hasNext()) {
            User temp = itr.next();
            if (temp.getClass() == Pilot.class)
                pilots.add((Pilot)temp);
            else if (temp.getClass() == Hostess.class)
                hostesses.add((Hostess)temp);
        }
        int i = 0;
        int j = 0;
        for (String temp : flightSystem.getFlight_map().keySet()) {
            for (String temp2 : flightSystem.getFlight_map().get(temp).keySet()) {
                for (Flight flight : flightSystem.getFlight_map().get(temp).get(temp2)) {
                    if (i < pilots.size())
                        pilots.get(i++).setFlight(flight);
                    if (i < pilots.size())
                        pilots.get(i++).setFlight(flight);
                    if (j < hostesses.size())
                        hostesses.get(j++).setFlight(flight);
                    if (j < hostesses.size())
                        hostesses.get(j++).setFlight(flight);
                    if (j < hostesses.size())
                        hostesses.get(j++).setFlight(flight);
                }
            }
        }
    }

    /**
     * This method reads a list of users from a specific file.
     * File format should be like:
     *      user1_role user1_id user1_password
     *      user2_role user2_id user2_password
     * @param user_file Path of the file that the users will be read from
     */
    private void ScanUsersFromFile(String user_file) {
//      Reads users from a test file
        try {
            String line;
            String[] in = new String[3];

//          Read a line including a user data
            Scanner sc_line = new Scanner(new File(user_file));
            while(sc_line.hasNextLine()) {
                line = sc_line.nextLine();
//              Tokenize user role, id and password data
                Scanner sc_key = new Scanner(line);
                for (int i = 0; sc_key.hasNext(); i++) {
                    in[i] = sc_key.next();
                }
//              Add users to the system according to his/her role
                if (in[0].equals("Admin")) {
                    this.userSet.add(new Admin(in[1], in[2], this));
                } else if (in[0].equals("Customer")) {
                    this.userSet.add(new Customer(in[1], in[2], flightSystem, userSet));
                } else if (in[0].equals("FlightManager")) {
                    this.userSet.add(new FlightManager(in[1], in[2], flightSystem, userSet));
                } else if (in[0].equals("Hostess")) {
                    this.userSet.add(new Hostess(in[1], in[2]));   
                } else if (in[0].equals("Pilot")) {
                    this.userSet.add(new Pilot(in[1], in[2]));
                } else if (in[0].equals("Technician")) {
                    this.userSet.add(new Technician(in[1], in[2]));
                } else {
                    System.out.println("There is no such user role in the system.");
                    System.exit(1);
                }
                sc_key.close();
            }
            sc_line.close();
        } catch (Exception e) {
            System.out.println("ERROR: Testing Airline System: "+e);
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
                                    
                                if (yn == null || yn.length() <= 0) {
                                    option = EXIT;
                                } else if (yn.charAt(0) == 'Y' || yn.charAt(0) == 'y') {
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
            System.out.println("ERROR: Testing AirlineSystem: "+e);
            System.exit(1);
        }
    }
    
    /**
     * Main method which will call mainMenu
     * @param args Commandline arguments
     */
    public static void main(String[] args) {
        /*try {
            AirlineSystemTester tester = new AirlineSystemTester();
            AirlineSystemTester.test_AirlineSystem("AllUsers.txt");
            System.exit(1);
        } catch (Exception e) {
            System.out.println("The process has failed.");
        }*/

        try {
            AirlineSystem system = new AirlineSystem("cities.txt", "distances.txt", "flights.txt", "AllUsers.txt");
            mainMenu(system);
        } catch (Exception e) {
            System.out.println("Failed to start the system!\n" + e);
            System.exit(1);
        }
    }


    /**
     * AirlineSystem tester class
     */
    public static class AirlineSystemTester {
        private static final String test_city_file = "cities.txt";
        private static final String test_distances_file = "distances.txt";
        private static final String test_flights_file = "flights.txt";
        private static final String test_users_file = "AllUsers.txt";

        private static void test_AirlineSystem(String user_file) {
            try {
                AirlineSystem system = new AirlineSystem(test_city_file, test_distances_file, test_flights_file, test_users_file);

            } catch (Exception e) {
                //Handle Exception
            }
        }
    }
}
