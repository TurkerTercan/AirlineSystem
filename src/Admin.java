import java.util.Scanner;
import java.util.Set;

/**
 * Represents admins of the airline system.
 */
public class Admin extends User{
    private Scanner input;
    private boolean LogedIn = false;
    private Set<User> users;

    public Admin(String id, String password, Set<User> users) {
        super(id, password);
        input = new Scanner(System.in);
        this.users = users;
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
                    return new FlightManager(UN,PW,null,null);
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

        for (int i=0; i<users.size(); i++){
            if(users.contains(fired)){
                users.remove(fired);
            }
        }
    }
}
