import java.util.Scanner;
import java.util.Set;

/**
 * Represents admins of the airline system.
 */
public class Admin extends User{
    //Data fields
    private Scanner input;
    private boolean LogedIn = false;
    private AirlineSystem system;

    /**
     * Constructor
     * @param id
     * @param password
     * @param system
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
            }else LogedIn = !LogedIn;
        }
        menu();
    }

    /**
     * Menu for admin
     */
    @Override
    public void menu() {
        System.out.println("\nMain menu:");
        System.out.println("please choose an action:");
        System.out.println("0-Up\n1-Hire an employee\n2-Remove an employee\n3-Buy a plane");
        System.out.print("\nchoice:");
        int choice = input.nextInt();
        while (choice!=0){
            switch (choice){
                case 1:
                    User newUser = hireEmployee();
                    if(newUser!=null)
                        system.getUserSet().add(newUser);
                    break;
                case 2:
                    removeEmployee();
                    break;
                case 3:
                    buyPlane();
                    break;
            }
        }
    }

    /**
     * Admin adds pilot, hostess, technician and flight manager.
     * @return User
     */
    private User hireEmployee(){
        String UN = "";
        String PW = "";
        boolean exists = false;
        int choice = -1;
        while (choice!=0){
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
                    if(!system.getUserSet().isEmpty()) {
                        System.out.print(system.getUserSet().getSize());
                        exists = system.getUserSet().find(new User(UN, "")) != null;
                    }
                    if(exists){
                        System.out.println("UserName Taken please try again");
                    }
                }while (exists);
                switch (choice) {
                    case 1:
                            return new Pilot(UN, PW);
                    case 2:
                            return new Hostess(UN, PW);
                    case 3:
                            return new Technician(UN, PW);
                    case 4:
                            return new FlightManager(UN, PW, system.getFlightSystem(), system.getUserSet());
                }
            }
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
        }
        switch (choice){
            case 1:
                fired = new Pilot(UN,"");
            case 2:
                fired = new Hostess(UN,"");
            case 3:
                fired = new Technician(UN,"");
            case 4:
                fired = new FlightManager(UN,"",null,null);
        }

        if(system.getUserSet().find(fired).compareTo(fired) == 0){
            system.getUserSet().remove(fired);
        }
    }

    /**
     * Buy plane method for admin.
     */
    private void buyPlane(){
        int choice = -1;
        while (choice!=0) {
            System.out.println("\nchoose a plane to buy:");
            System.out.println("0-Up\n1-Airbus A220\tCapacity: 150\n2-Airbus A330\tCapacity: 250 \n3-Boeing 747\tCapacity: 400\n");
            System.out.print("\nchoice:");
            choice = input.nextInt();
            while (choice != 0) {
                switch (choice) {
                    case 1:
                        system.getFlightSystem().addPlane(new Plane(150));
                        break;
                    case 2:
                        system.getFlightSystem().addPlane(new Plane(250));
                        break;
                    case 3:
                        system.getFlightSystem().addPlane(new Plane(400));
                        break;
                }
            }
        }
    }
}
