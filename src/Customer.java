import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Customer extends User {
    private Scanner input;
    private boolean LogedIn = false;
    private ArrayList<Ticket> tickets;
    private PriorityQueue<Flight> flights;
    FlightSystem Fsys;

    public Customer(String id, String password,FlightSystem Fsys) {
        super(id, password);
        input = new Scanner(System.in);
        tickets = new ArrayList<>();
        this.Fsys = Fsys;
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
                    buyTicket();
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

    private void buyTicket(){
        System.out.println("Please enter Setoff city:");
        String source = input.next();
        System.out.println("Please enter Destination city:");
        String dest = input.next();
        //ask by price or time ?
        System.out.println("1.Sort by time\n2.Sort by price\n");
        String choise = input.next();

        flights = Fsys.getFlights(source,dest);
        if (flights == null) {
            System.out.println("There is no flight between " + source + " and " + dest);
            Flight[] temp = Fsys.getPath(source,dest);
            if (temp.length == 0) {
                System.out.println("There is no transfer point either. Please try another");
                return;
            }
            System.out.println("You can transfer from your setOff to your destination with these flights");
            for (int i = 0; i < temp.length; i++) {
                System.out.println((i+1) + ". " + temp[i].toString());
            }
            System.out.println("Do you want to buy all tickets from these flights? (Y/N)");
            String tempStr = input.nextLine();
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
            for ( int i = 0; i < flights.size(); i++ ) {
                flights1.add(flights.get(i));
            }

            QuickSort.sort(flights1);
            for ( Flight F : flights1 ) {
                System.out.println( flights.getIndexOf(F) + F.toString());
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
