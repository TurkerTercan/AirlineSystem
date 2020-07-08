import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Environment class for whole the system.
 */
public class AirlineSystem {
    //Data Fields
    /** The SkipList that holds Users */
    private SkipList<User> userSet;
    /** A system that has all necessary methods for a create flight, remove a flight etc. */
    private FlightSystem flightSystem;
    /** A queue that has all planes that is required to maintance */
    private Queue<Plane> planeMaintance;

    /**
     * Basic Constructor that just initializes basics and adds admin to users
     */
    private AirlineSystem() {
        planeMaintance = new ArrayDeque<>();
        userSet = new SkipList<>();
        //A default administrator(id: "admin", passwd: "admin") will be added to the system right after the execution of the program.
        userSet.add(new Admin("admin", "admin", this));
    }

    /**
     * Constructor with 2 txt files. With these files it constructs a FlightSystem.
     * @param file_city txt that represents cities
     * @param file_distance txt that represents distances
     * @throws FileNotFoundException if any file is not found
     */
    public AirlineSystem(String file_city, String file_distance) throws FileNotFoundException {
        this();
        flightSystem = new FlightSystem(file_city, file_distance, planeMaintance);
    }

    /**
     * Constructor with 3 txt files. With these files it constructs a FlightSystem and also it adds all
     * flights that is given in file_flights
     * @param file_city txt that represents cities
     * @param file_distance txt that represents distances
     * @param file_flights txt that holds all flight between those given cities
     * @throws FileNotFoundException if any file is not found
     */
    public AirlineSystem(String file_city, String file_distance, String file_flights) throws FileNotFoundException {
        this();
        flightSystem = new FlightSystem(file_city, file_distance, planeMaintance, file_flights);
    }

    /**
     * Constructor with 4 txt files. With these files it constructs a FlightSystem and also it adds all
     * flights that is given in file_flights. And adds Users in the file_user to userSet.
     * @param file_city txt that represents cities
     * @param file_distance txt that represents distances
     * @param file_flight txt that holds all flight between those given cities
     * @param file_user txt that holds all users of the system
     * @throws FileNotFoundException if file not found
     */
    public AirlineSystem(String file_city, String file_distance, String file_flight, String file_user) throws FileNotFoundException {
        this(file_city, file_distance, file_flight);
        ScanUsersFromFile(file_user);
        AssignCrew();
    }

    /**
     * Assigns all flights to 2 pilots and 3 hostesses
     */
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
                    if (flight.getCrew().size() != 5) {
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
                    this.userSet.add(new Technician(in[1], in[2], flightSystem));
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
     * @return SkipList of users
     */
    public SkipList<User> getUserSet() {
        return userSet;
    }

    /**
     * getter method of the flightSystem
     * @return FlightSysten
     */
    public FlightSystem getFlightSystem() {
        return flightSystem;
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
        try {
            Scanner scan = new Scanner(System.in);    
            //Stay in the main menu until user wants to exit
            while (option == STAND) {
                System.out.println("------Main Menu------");
                System.out.println("1.Login");
                System.out.println("2.Customer Registration");
                System.out.println("3.Exit");
                option = scan.nextInt();
//                  Read the buffer
                    scan.nextLine();
                switch (option) {
                    case 1:
//                      Stay in the main menu until user wants to exit  
                        option = STAND;
                        System.out.print("ID:");
                        id = scan.nextLine();
                        System.out.print("Password: ");
                        passwd = scan.nextLine();
                        usr = system.userSet.find(new User(id, passwd));

                        //If there is no such user in the system
                        if (usr == null) {
                            System.out.println("There is no such registered user in the system!");
                            System.out.println("Do you want to use customer registration(Y/N) ?");
                                yn = scan.nextLine();

                            if (yn == null || yn.length() <= 0) {
                                //Exit from the menu
                            } else if (yn.charAt(0) == 'Y' || yn.charAt(0) == 'y') {
                                //Calling static registration method of the user
                                Customer.registration();
                            } else if (yn.charAt(0) == 'N' || yn.charAt(0) == 'n') {
                                //Exit from the menu
                            } else {
                                System.out.println("Invalid option.");
                            }
                        } else {
                            //If the login is succesfull, call the user's menu
                            usr.menu();
                        }                      
                        break;
                    case 2:
                        //Calling static registration method of the user
                        Customer.registration();
                        option = STAND;
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
        try {
            AirlineSystem system = new AirlineSystem("cities.txt", "distances.txt", "flights.txt", "AllUsers.txt");
            mainMenu(system);
        } catch (Exception e) {
            System.out.println("Failed to start the system!\n" + e);
            System.exit(1);
        }
    }
}
