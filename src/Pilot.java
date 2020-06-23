import java.util.Scanner;

/**
 * Represents pilots of the airline system.
 */
public class Pilot extends User{
    private Scanner input;
    private boolean LogedIn = false;
    private Flight flight;
    public Pilot(String id, String password) {
        super(id, password);
    }

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

    @Override
    public void menu() {
        int choice = -1;
        while (choice!=0) {
            System.out.println("\nMain menu:");
            System.out.println("Please choose an action:");
            System.out.println("0-Up\n1-Show flight details");
            System.out.print("\nChoice:");
            choice = input.nextInt();
            switch (choice){
                case 0:
                    break;
                case 1:
                    showFlight();
                    break;
                default:
                    System.out.println("Invalid Input!!\n");
            }
        }
    }

    private void setFlight(Flight flight){
        this.flight = flight;
    }

    private void showFlight(){
        System.out.print(flight.toString());
    }
}
