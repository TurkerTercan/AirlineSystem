import java.util.Scanner;
import java.util.Queue;

/**
 * Represents technicians of the airline system.
 */
public class Technician extends User {
	private Scanner input;
    private boolean LogedIn = false;
    private Queue<Plane> planeMaintance;

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

    private void showMaintenanceQueue() {
        System.out.print("There are planes waiting to be maintained : ");
        System.out.print(getPlaneMaintance().toString());
    }

    public Queue<Plane> getPlaneMaintance() {
        return planeMaintance;
    }

    public void setPlaneMaintance(Queue<Plane> planeMaintance) {
        this.planeMaintance = planeMaintance;
    }
}
