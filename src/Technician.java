import java.util.Scanner;

/**
 * Represents technicians of the airline system.
 */
public class Technician extends User {
	private Scanner input;
    private boolean LogedIn = false;

    public Technician(String id, String password) {
        super(id, password);
    }

    @Override
    public void login() {
        while (!LogedIn) {
            System.out.println("Technician Login");
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

    }
}
