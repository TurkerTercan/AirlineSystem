import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents admins of the airline system.
 */
public class Admin extends User {
    //Data fields
    private Scanner input;
    private boolean LogedIn = false;
    private AirlineSystem system;

    /**
     * Constructor
     * @param id Admin's identification
     * @param password Admin's password
     * @param system environment class
     */
    public Admin(String id, String password, AirlineSystem system) {
        super(id, password);
        input = new Scanner(System.in);
        this.system = system;
    }

    /**
     * Login method for Admin.
     */
    @Override
    public void login() {
        while (!LogedIn) {
            System.out.println("Password: ");
            String PWord = input.next();

            if (!PWord.equals(getPassword())) {
                System.out.println("Authentication failed please try again!!");
            } else LogedIn = !LogedIn;
        }
        menu();
    }

    /**
     * Menu for admin
     */
    @Override
    public void menu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\nMain menu:");
            System.out.println("please choose an action:");
            System.out.println("0-Up\n1-Hire an employee\n2-Remove an employee\n3-Buy a plane");
            System.out.print("\nchoice:");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    User newUser = hireEmployeeMenu();
                    if (newUser != null)
                        system.getUserSet().add(newUser);
                    break;
                case 2:
                    removeEmployee();
                    break;
                case 3:
                    buyPlaneMenu();
                    break;
            }
        }
    }

    /**
     * menu required to hire employees
     * @return created User
     */
    private User hireEmployeeMenu() {
        String UN = "";
        String PW = "";
        boolean exists = false;
        int choice = -1;
        while (choice != 0) {
            System.out.println("\nchoose employee to hire:");
            System.out.println("0-Up\n1-Pilot\n2-Hostess\n3-Technician\n4-Flight Manager");
            System.out.print("\nchoice:");
            choice = input.nextInt();
            if (choice != 0) {
                do {
                    System.out.print("Enter UserName: ");
                    UN = input.next();
                    System.out.print("Enter new PassWord: ");
                    PW = input.next();
                    if (!system.getUserSet().isEmpty()) {
                        exists = system.getUserSet().find(new User(UN, "")) != null;
                    }
                    if (exists) {
                        System.out.println("UserName Taken please try again");
                    }
                } while (exists);

                return hireEmployee(UN, PW, choice);
            }
        }
        return null;
    }
    /**
     * Admin adds pilot, hostess, technician and flight manager.
     * @param UN User Name
     * @param PW password
     * @param Type Class type
     * @return User
     */
    private User hireEmployee(String UN, String PW, int Type){
                switch (Type) {
                    case 0:
                        break;
                    case 1:
                        return new Pilot(UN, PW);
                    case 2:
                        return new Hostess(UN, PW);
                    case 3:
                        return new Technician(UN, PW, system.getFlightSystem());
                    case 4:
                        return new FlightManager(UN, PW, system.getFlightSystem(), system.getUserSet());
                    default:
                        System.out.println("Invalid Choice!!");
                        break;
                }

        return null;
    }

    /**
     * Admin removes pilot, hostess, technician and flight manager.
     */
    private void removeEmployee(){
        String UN = "";
        System.out.println("choose employee to remove:");
        System.out.println("0-Up\n1-Pilot\n2-Hostess\n3-Technician\n4-Flight Manager");

        int choice = input.nextInt();
        User fired = null;
        if (choice!=0){
            System.out.print("Enter UserName: ");
            UN = input.next();
        }else return;
        switch (choice){
            case 0:
                break;
            case 1:
                fired = new Pilot(UN,"");
            case 2:
                fired = new Hostess(UN,"");
            case 3:
                fired = new Technician(UN,"", null);
            case 4:
                fired = new FlightManager(UN,"",null,null);
            default:
                System.out.println("Invalid Choice!!");
                break;
        }
        try {
            if(system.getUserSet().find(fired).compareTo(fired) == 0){
                if(system.getUserSet().remove(fired)){
                   System.out.println("Removed Employee "+ fired.getID());
                }
            }else {
                System.out.println("couldn't find Employee "+ fired.getID());
            }
        }catch (Exception e){
            System.out.println("Task Faild");
        }

    }

    /**
     * menu required to buy aircraft
     */
    private void buyPlaneMenu(){
        int choice = -1;
        while (choice!=0) {
            System.out.println("\nchoose a plane to buy:");
            System.out.println("0-Up\n1-Airbus A220\tCapacity: 150\n2-Airbus A330\tCapacity: 250 \n3-Boeing 747\tCapacity: 400\n");
            System.out.print("\nchoice:");
            choice = input.nextInt();
            buyPlane(choice);
        }
    }

    /**
     * Buy plane method for admin.
     */
    private void buyPlane(int Type){
        switch (Type) {
            case 0:
                break;
            case 1:
                system.getFlightSystem().addPlane(new Plane(150));
                break;
            case 2:
                system.getFlightSystem().addPlane(new Plane(250));
                break;
            case 3:
                system.getFlightSystem().addPlane(new Plane(400));
                break;
            default:
                System.out.println("Invalid Choice!!");
                break;
        }
    }

    /**
     * The admin's methods are tested.
     */
    public static class AdminTester{
        private static final String test_city_file = "cities.txt";
        private static final String test_distances_file = "distances.txt";
        private static AirlineSystem sys;

        static {
            try {
                sys = new AirlineSystem(test_city_file,test_distances_file);
            } catch (FileNotFoundException e) {
                System.out.println("Error - AirlineSystem");
            }
        }

        private static Random rand = new Random();
        private static String[]  testUserName = new String[]{"aaaa", "aaab", "aaac", "aaad", "aaae", "aaaf", "aaag", "aaah", "aaai", "aaaj", "aaak", "aaal", "aaam", "aaan"};
        private static Admin admin = new Admin("admin2","admin2", sys);;
        private static FlightManager manager = new FlightManager("abc","1234",sys.getFlightSystem(),sys.getUserSet());

        public AdminTester() throws FileNotFoundException { }

        public static void testHireEmployee() throws FileNotFoundException {
            System.out.println("Testing HireEmployee by Admin");

            //adding random users to the system
            for(int i=0;i<testUserName.length;i++){
               User temp = admin.hireEmployee(testUserName[i],"1234",rand.nextInt(4)+1);
               sys.getUserSet().add(temp);
            }
            //teseting if added users are found
            for(int i=0;i<sys.getUserSet().getSize()-1;i++) {
                User temp = sys.getUserSet().find(new User(testUserName[i], "1234"));
                if (temp == null) {
                    System.out.println("User " + testUserName[i] + " not found in the system");
                } else {
                    System.out.println("User " + temp.getID() + " found in the system");
                }
            }
        }

        private static void testRemoveEmployee(){
            System.out.println("\nTesting removeEmployee by Admin");
            int i=0;
            while (!sys.getUserSet().isEmpty()){
                if(sys.getUserSet().remove(new User(testUserName[i],"1234"))){
                    System.out.println("User "+testUserName[i]+" removed Successfully");
                    i++;
                }else{
                    System.out.println("User " + testUserName[i] + " not found in the system");
                }
            }
        }

        private static void testBuyPlane(int testCount){
            System.out.println("\nTesting buyPlane by Admin "+testCount+" times");
            for (int i=0;i<testCount;i++){
                admin.buyPlane(rand.nextInt(2)+1);
            }
            for (int i = 0; i < testCount; i++) {
                Plane temp = manager.findPlane(String.valueOf(i));
                if (temp != null) {
                    System.out.println("Plane "+temp.getId()+" has been found in the system");
                } else {
                    System.out.println("Plane "+i+" has not been found in the system");
                }
            }
        }

        public static void main(String[] args) throws FileNotFoundException {

            try {
                testHireEmployee();
            }catch (Exception e){
                System.out.println("ERROR - hireEmployee()");
            }
            try {
                testRemoveEmployee();
            }catch (Exception e){
                System.out.println("ERROR - removeEmployee()");
            }
            testBuyPlane(10);
        }
    }
}
