import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends User {
    private Scanner input;
    private boolean LogedIn = false;
    private ArrayList<Ticket> tickets;

    public Customer(String id, String password) {
        super(id, password);
        input = new Scanner(System.in);
        tickets = new ArrayList<Ticket>();
    }

    @Override
    public void login() {
        while (!LogedIn) {
            System.out.println("Customer Login");
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
        while (choice!=0){
            System.out.println("\nMain menu:");
            System.out.println("please choose an action:");
            System.out.println("0-Up\n1-Buy a ticket\n2-Postpone a Ticket\n3-Cancel Ticket\n4-Show Tickets");
            System.out.print("\nchoice:");

            choice = input.nextInt();
            switch (choice){
                case 0:
                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:
                    cancelTicket();
                    break;
                case 4:
                    showTickets();
                    break;
                default:
                    System.out.println("Invalid Input!!\n");
            }
        }
    }

    private void cancelTicket(){
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

    private void showTickets(){
        if(tickets.size() == 0){
            System.out.println("You don't have any purchased tickets");
            return;
        }
        for (Ticket T : tickets){
            System.out.println(T.toString());
        }
    }


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
}
