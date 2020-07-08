import java.io.FileNotFoundException;
import java.util.*;

/**
 * Customer class that represent AirlineSystem's clients
 */
public class Customer extends User {
    /** Terminal Input */
    private static Scanner input;
    /** Represent to if user logedIn */
    private boolean LogedIn = false;
    /** ArrayList that holds that users all tickets */
    private final ArrayList<Ticket> tickets;
    /** To check if there was another user with the same name */
    private static SkipList<User> users;
    /** To get Flights from the system */
    private static FlightSystem Fsys;

    /**
     * Basic constructor
     * 
     * @param id          Customer's identification
     * @param password    Customer's password
     * @param fs        FlightSystem that holds flights
     * @param systemUsers All users in the system
     */
    public Customer(String id, String password, FlightSystem fs, SkipList<User> systemUsers) {
        super(id, password);
        input = new Scanner(System.in);
        tickets = new ArrayList<>();
        Fsys = fs;
        users = systemUsers;
    }

    /**
     * Method to able to log in to the system
     */
    @Override
    public void login() {
        while (!LogedIn) {
            System.out.println("Password: ");
            String PWord = input.next();

            if (!PWord.equals(getPassword())) {
                System.out.println("Authentication failed please try again!!");
            } else
                LogedIn = !LogedIn;
        }
        menu();
    }

    /**
     * Necessary information is taken and recorded for registration
     */
    public static void registration() {
        String id;
        String passwd;
        boolean stand;
        System.out.println("Customer Registration");
        do {
            stand = false;
            System.out.print("Id: ");
            id = input.nextLine();
            if (users.find(new User(id, "")) != null) {
                System.out.println("There is already a user registered with this id in the system.");
                stand = true;
            } else {
                System.out.print("\nPassword: ");
                passwd = input.nextLine();
                users.add(new Customer(id, passwd, Fsys, users));
            }
        } while (stand);
        System.out.println("A new customer has been added to the system.");
    }

    /**
     * FlightManager's interface
     */
    @Override
    public void menu() {
        int choice = -1;
        while (choice!=0){
            System.out.println("\nMain menu:");
            System.out.println("please choose an action:");
            System.out.println("0-Up\n1-Buy a ticket\n2-Cancel Ticket\n3-Show Tickets\n4-Change Password");
            System.out.print("\nchoice:");

            choice = input.nextInt();
            switch (choice){
                case 0:
                    break;
                case 1:
                    buyTicket();
                    break;
                case 2:
                    cancelTicket();
                    break;
                case 3:
                    showTickets();
                    break;
                case 4:
                    changePassword();
                    break;
                default:
                    System.out.println("Invalid Input!!\n");
            }
        }
    }

    /**
     * Change customer's password
     */
    private void changePassword() {
        System.out.println("Please enter your current password");
        String old = input.next();
        if (old.equals(getPassword())) {
            System.out.println("Please enter new password");
            String newPass = input.next();
            setPassword(newPass);
            System.out.println("Operation is successful!");
            return;
        }
        System.out.println("Password is wrong! Try Again!");
    }

    /**
     * Necessary information is obtained and recorded for buying a ticket
     */
    private void buyTicket(){
        System.out.println("Please enter Setoff city:");
        String source = input.next();
        System.out.println("Please enter Destination city:");
        String dest = input.next();
        //ask by price or time ?
        System.out.println("1.Sort by time\n2.Sort by price\n");
        String choise = input.next();

        PriorityQueue<Flight> flights = Fsys.getFlights(source, dest);
        if (flights == null) {
            System.out.println("There is no flight between " + source + " and " + dest);
            Flight[] temp = Fsys.getPath(source,dest);
            if (temp == null) {
                System.out.println("There is no transfer point either. Please try another");
                return;
            }
            System.out.println("You can transfer from your setOff to your destination with these flights");
            for (int i = 0; i < temp.length; i++) {
                System.out.println((i+1) + ". " + temp[i].toString());
            }
            System.out.println("Do you want to buy all tickets from these flights? (Y/N)");
            String tempStr = input.next();
            switch (tempStr) {
                case "Y":
                    for(Flight F : temp) {
                        F.addNewCustomer(this);
                        tickets.add(new Ticket(F.getDepartTime(), String.valueOf(F.getRemainingSeats())));
                    }
                    System.out.println("Operation is successful");
                    break;
                case "N":
                    System.out.println("Backing to the menu");
                    break;
                default:
                    System.out.println("Wrong Choice!");
            }
            return;
        }
        int index = 1;

        if(choise.matches("2")) {

            List<Flight> flights1 = new ArrayList<>();
            for (int i = 0; i < flights.size(); i++ ) {
                flights1.add(flights.get(i));
            }

            QuickSort.sort(flights1);
            for ( Flight F : flights1 ) {
                System.out.println( flights.getIndexOf(F)+1 + F.toString());
            }
        }

        else {
            for(Flight F : flights){
                System.out.println(( index++ )+F.toString());
            }
        }

        System.out.println("Please choose a flight: ");
        index = input.nextInt();
        Flight chosen = flights.get(index-1);
        tickets.add(new Ticket(chosen.getDepartTime(),String.valueOf(chosen.getRemainingSeats())));
        chosen.addNewCustomer(this);
        System.out.println("Operation is successful");
    }

    /**
     * Necessary information is obtained and deleted for canceling a ticket
     */
    private void cancelTicket(){
        System.out.println("Your Tickets : ");
        showTickets();
        System.out.print("Please enter ticket ID:");
        String TId = input.next();
        for(Ticket T : tickets){
            if(T.getId().equals(TId)){
                tickets.remove(T);
                return;
            }
        }
        System.out.println("Couldn't Find a ticket with provided ID in your active tickets");
    }

    /**
     * Shows the customer's tickets.
     */
    private void showTickets(){
        if(tickets.size() == 0){
            System.out.println("You don't have any purchased tickets");
            return;
        }
        for (Ticket T : tickets){
            System.out.println(T.toString());
        }
    }


    /**
     * Class to represent a ticket that is the Customer bought
     */
    private static class Ticket{
        private static int ID = 0;
        private String id;
        private String deprTime;
        private String seat;

        public Ticket(String id, String deprTime, String seat){
            this.id = id;
            this.deprTime = deprTime;
            this.seat = seat;
        }

        public Ticket(String deprTime, String seat){
            this(String.valueOf(ID++), deprTime, seat);
        }

        public String getDeprTime() {
            return deprTime;
        }

        public String getId() {
            return id;
        }


        public String getSeat() {
            return seat;
        }

        @Override
        public String toString() {
            return  "id: " + id + '\t' +
                    "deprTime: " + deprTime + '\t' +
                    "seat= " + seat + '\n';
        }
    }

    /**
     * The customer's methods are tested.
     */
    public static class CustomerTester {
        private static final String test_city_file = "cities.txt";
        private static final String test_distances_file = "distances.txt";
        private static final String test_flights_file = "flights.txt";
        private static final Queue<Plane> test_queue = new LinkedList<>();

        //Unique plane id that will be used for testing
        FlightSystem system;
        static Customer customer;
        static SkipList<User> users;

        public CustomerTester() throws FileNotFoundException {
            system = new FlightSystem(test_city_file,test_distances_file,test_queue,test_flights_file);
            users = new SkipList<>();
            users.add(new User("test", "test"));

            customer = new Customer("test", "test", system, users);
        }

        public static void test_buyTicket() throws FileNotFoundException {
            System.out.println("Testing buy ticket method of Customer ");
            customer.buyTicket();
            customer.showTickets();
        }

        public static void test_cancelTicket() throws FileNotFoundException {
            System.out.println("Testing cancel ticket method of Customer ");
            customer.cancelTicket();
            customer.showTickets();
        }

        public static void test_registration() throws FileNotFoundException {
            System.out.println("Testing registration method of Customer ");
          //  FlightSystem system = new FlightSystem();
           // Customer customer = new Customer("test", "1", system, null);
            Customer.registration();
            customer.showTickets();
        }

        public static void main(String[] args) throws FileNotFoundException {
                new CustomerTester();
                test_buyTicket();
                //CustomerTester.test_cancelTicket();
                //customerTester.test_registration();

        }
    }
}
