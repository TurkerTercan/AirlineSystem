import java.util.Scanner;
import java.util.Queue;

/**
 * Represents technicians of the airline system.
 */
public class Technician extends User {
    //Data fields
    /** Terminal Input */
	private Scanner input;
	/** Represent to if user logedIn */
    private boolean LogedIn = false;
    /** A queue that stores planes need to be maintaince */
    private Queue<Plane> planeMaintance;

    /**
     * Constructor
     * @param id
     * @param password
     */
    public Technician(String id, String password) {
        super(id, password);
        input = new Scanner(System.in);
    }

    /**
     * Login method for technician.
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
     * Menu for technician.
     */
    @Override
    public void menu() {
        int choice = -1;
        while (choice!=0) {
            System.out.println("\nMain menu:");
            System.out.println("Please choose an action:");
            System.out.println("0-Up\n1-Maintenance Confirm\n2-Show Maintenance Airplane Queue");
            System.out.print("\nChoice:");
            choice = input.nextInt();
            switch (choice){
                case 1:
                    maintenanceConfirm();
                    break;

                case 2:
                    getPlaneMaintance();
                    break;
            }
        }
    }

    /**
     * Maintenance confirm method for technician.
     */
    private void maintenanceConfirm()
    {
        if(planeMaintance.size() != 0) {
            Plane maintain = planeMaintance.poll();
            System.out.print("Plane " + maintain.getId() +" is maintained!");
        }
        else {
            System.out.print("Plane maintain queue is empty!");
        }
    }

    /**
     * Shows maintenance queue for technician.
     */
    private void showMaintenanceQueue() {
        System.out.print("There are planes waiting to be maintained : ");
        System.out.print(getPlaneMaintance().toString());
    }

    /**
     * Getter method for plane maintance
     * @return plane queue
     */
    public Queue<Plane> getPlaneMaintance() {
        return planeMaintance;
    }

    /**
     * Setter method for plane maintance
     * @param planeMaintance
     */
    public void setPlaneMaintance(Queue<Plane> planeMaintance) {
        this.planeMaintance = planeMaintance;
    }
}
