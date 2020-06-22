import java.util.Scanner;
import java.util.Set;

/**
 * Represents admins of the airline system.
 */
public class Admin extends User{
    private Scanner input;
    private boolean LogedIn = false;
    private SkipList<User> users;
    FlightSystem Fsys;

    public Admin(String id, String password, SkipList<User> users,FlightSystem FSys) {
        super(id, password);
        input = new Scanner(System.in);
        this.users = users;
        this.Fsys = FSys;
    }

    @Override
    public void login() {
        while (!LogedIn) {
            System.out.println("Admin Login");
            System.out.println("UserName: ");
            String uName = input.next();
            System.out.println("Password: ");
            String PWord = input.next();

            if (!uName.equals(getID()) || !PWord.equals(getPassword())) {
                System.out.println("Authentication failed please try again!!");
            }else LogedIn = !LogedIn;
        }
        menu();
    }

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
                    users.add(hireEmployee());
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

    private User hireEmployee(){
        String UN = "";
        String PW = "";
        int choice = -1;
        while (choice!=0){
            System.out.println("\nchoose employee to hire:");
            System.out.println("0-Up\n1-Pilot\n2-Hostess\n3-Technician\n4-Flight Manager");
            System.out.print("\nchoice:");
            choice = input.nextInt();

            if (choice!=0){
                System.out.print("Enter UserName: ");
                UN = input.next();
                System.out.print("Enter new PassWord: ");
                PW = input.next();
            }
            switch (choice){
                case 1:
                    return new Pilot(UN,PW);
                case 2:
                    return new Hostess(UN,PW);
                case 3:
                    return new Technician(UN,PW);
                case 4:
                    return new FlightManager(UN,PW,Fsys,users);
            }
        }

        return null;
    }

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

        users.remove(fired);
    }

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
                        Fsys.addPlane(new Plane(150));
                        break;
                    case 2:
                        Fsys.addPlane(new Plane(250));
                        break;
                    case 3:
                        Fsys.addPlane(new Plane(400));
                        break;
                }
            }
        }
    }
}
