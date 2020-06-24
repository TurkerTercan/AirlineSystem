import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class FlightManager extends User {
    private Scanner input;
    private boolean LogedIn = false;
    private FlightSystem flightSystem;
    private SkipList<User> users;

    public FlightManager(String id, String password, FlightSystem flightSystem, SkipList<User> users) {
        super(id, password);
        input = new Scanner(System.in);
        this.flightSystem = flightSystem;
        this.users = users;
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
        int choice = 1;
        while (choice!=0){
            System.out.println("\nMain menu:");
            System.out.println("Please choose an action:");
            System.out.println("0-Up\n1-Create a flight\n2-Remove a flight\n3-Modify a flight\n4-Print a flight");
            System.out.print("\nchoice:");
            choice = input.nextInt();
            input.nextLine();// Consume newline left-over
                switch (choice){
                    case 1:
                        addFlight();
                        break;
                    case 2:{
                        removeFlight();
                        break;
                    }
                    case 3:
                        modifyFlight();
                        break;
                    case 4:
                        printFlight();
                        break;
            }
        }
    }

    /** Adds Flight */
    public void addFlight(){
        Flight tempFlight = new Flight("",new Plane("",0),"","","",0.0); //for only check
        System.out.println("Please enter flight id");
        String flightID = input.nextLine();
        System.out.println("Please enter plane id");
        String planeID = input.nextLine();
        System.out.println("Please enter destination");
        String dest = input.nextLine();
        System.out.println("Please enter set off zone");
        String setOff = input.nextLine();
        System.out.println("Please enter depart time");
        String depart = input.nextLine();
        Plane plane = findPlane(planeID);
        System.out.println("Please enter price");
        double price = input.nextDouble();
        input.nextLine();// Consume newline left-over
        boolean checkDest = checkDestination(dest);
        boolean checkSetOff = checkSetOff(setOff);
        if(!printError(tempFlight, plane,new Pilot("0","0"),new Hostess("0","0"),
                checkDest,checkSetOff)) {
            if(dest.equals(setOff))
                System.out.println("Set off and destination cannot be the same");
            else {
                Flight newFlight = new Flight(flightID, plane, setOff, dest, depart, price);
                flightSystem.addFlight(createCrew(newFlight));
            }
        }
    }

    /** Removes Flight */
    public void removeFlight(){
        Flight flight = getFlight();
        if(!printError(flight,new Plane("",0),new Pilot("0","0"),
                new Hostess("0","0"),true,true)){
            flight.removeAllCrew();
            flightSystem.removeFlight(flight);
        }
    }

    /** Modifies Flight */
    public void modifyFlight(){
        Flight flight = getFlight();
        if(!printError(flight,new Plane("",0),new Pilot("0","0"),
                new Hostess("0","0"),true,true))
        {
            setFlight(flight);
        }
    }

    /** Prints flight */
    public void printFlight(){
        Flight flight = getFlight();
        System.out.println(flight.toString());
    }

    /** Takes information about flight and returns it
     * @return The flight
     */
    private Flight getFlight(){
        System.out.println("Please enter flight id");
        String flightID = input.nextLine();
        System.out.println("Please enter destination");
        String dest = input.nextLine();
        System.out.println("Please enter set off information");
        String setOff = input.nextLine();
        return findFlight(flightID,setOff,dest);
    }

    /** Returns required flight
     * @param id The flight id
     * @param dest The destination information
     * @return The flight if exist, null otherwise
     */
    private Flight findFlight(String id, String setOff ,String dest){

        Map<String, Map<String,PriorityQueue<Flight>>> map= flightSystem.getFlight_map();
        Map<String, PriorityQueue<Flight>> flights = map.get(setOff);
        if(flights == null)
            return null;
        else{
            PriorityQueue<Flight> flight = flights.get(dest);
            if(flight == null)
                return null;
            else{
                for (Flight temp : flight) {
                    if (temp.getID().equals(id))
                        return temp;
                }
                return null;
            }
        }
    }

    /** Returns required plane
     * @param id The plane id
     * @return The plane if exist, null otherwise
     */
    private Plane findPlane(String id){
        for (Plane temp : flightSystem.getAvailablePlanes()) {
            if (temp.getId().equals(id))
                return temp;
        }
        return null;
    }

    /**
     * Check validity of destination
     * @param dest The destination
     * @return True if destination is exist, false otherwise
     */
    private boolean checkDestination(String dest){ return flightSystem.getCity().contains(dest); }

    /**
     * Check validity of set off information
     * @param so The set off information
     * @return True if set off is exist, false otherwise
     */
    private boolean checkSetOff(String so){return flightSystem.getCity().contains(so); }

    /**
     * Displays error
     * @param plane The plane
     * @param pilot The pilot
     * @param hostess The hostess
     * @param dest The destination
     * @param so The set off information
     * @return True if any conditions are false, true otherwise
     */
    private boolean printError(Flight flight, Plane plane, Pilot pilot, Hostess hostess, boolean dest, boolean so){
        if(flight != null && plane != null && pilot != null && hostess != null && dest && so)
            return false;
        else{
            if(flight == null)
                System.out.println("Wrong flight id.");
            if(plane == null)
                System.out.println("Wrong plane id.");
            if(pilot == null)
                System.out.println("Wrong pilot id.");
            if(hostess == null)
                System.out.println("Wrong hostess id.");
            if(!dest)
                System.out.println("Wrong destination information.");
            if(!so)
                System.out.println("Wrong set off information");
            return true;
        }
    }

    /** Assign employees to the flight
     * @param flight The flight
     * @return Updated Flight
     */
    private Flight createCrew(Flight flight) {
        int choice = 1;
        while (choice != 0) {
            System.out.println("\nAdd crew menu:");
            System.out.println("Please choose an action:");
            System.out.println("0-Up\n1-Add a pilot\n2-Add a hostess");
            System.out.print("\nchoice:");
            choice = input.nextInt();
            input.nextLine();// Consume newline left-over
            Flight tempFlight = new Flight("",new Plane("",0),"","","",0.0);
            switch (choice) {
                case 1:
                    System.out.println("Please enter pilot id");
                    String pilotID = input.nextLine();
                    Pilot pilot = findPilot(pilotID);
                    if(!printError(tempFlight,new Plane("0",0),pilot,
                            new Hostess("0","0"),true,true)){
                        pilot.setFlight(flight);
                        flight.addCrewMember(pilot);
                    }
                    break;
                case 2:
                    System.out.println("Please enter hostess id");
                    String hostessID = input.nextLine();
                    Hostess hostess = findHostess(hostessID);
                    if(!printError(tempFlight,new Plane("0",0),new Pilot("0","0"),
                            hostess,true,true)){
                        hostess.setFlight(flight);
                        flight.addCrewMember(hostess);
                    }
                    break;
            }
        }
        return flight;
    }

    /** Returns required pilot
     * @param id The pilot id
     * @return The pilot if exist, null otherwise
     */
    private Pilot findPilot(String id){
        User u = users.find(new Pilot(id,null));
        if(u == null)
            return null;
        else{
            if(u instanceof Pilot){
                Pilot pilot = (Pilot)u;
                if(pilot.getFlight() != null)
                    return null;
                else
                    return pilot;
            }
            else
                return null;
        }
    }

    /** Returns required hostess
     * @param id The hostess id
     * @return The hostess if exist, null otherwise
     */
    private Hostess findHostess(String id){
        User u = users.find(new Hostess(id,null));
        if(u == null)
            return null;
        else{
            if(u instanceof Hostess){
                Hostess hostess = (Hostess) u;
                if(hostess.getFlight() != null)
                    return null;
                else
                    return hostess;
            }
            else
                return null;
        }
    }

    /**
     * Sets flight
     * @param flight The flight
     */
    private void setFlight(Flight flight){
        int choice = 1;
        while (choice != 0) {
            System.out.println("\nSet flight menu:");
            System.out.println("Please choose an action:");
            System.out.println("0-Up\n1-Set id\n2-set plane\n3-set destination\n4-set set off information\n" +
                    "5-set departTime\n6- add crew\n7- remove crew");
            System.out.print("\nchoice:");
            choice = input.nextInt();
            input.nextLine();// Consume newline left-over
            Plane tempPlane = new Plane("0", 0);
            switch (choice) {
                case 1:
                    System.out.println("Please enter new id");
                    String newID = input.nextLine();
                    flight.setID(newID);
                    break;
                case 2:
                    System.out.println("Please enter new plane ID");
                    String planeID = input.nextLine();
                    Plane newPlane = findPlane(planeID);
                    if (newPlane != null) {
                        flight.setPlane(newPlane);
                    } else
                        System.out.println("Wrong plane id");
                    break;
                case 3:
                    System.out.println("Please enter new destination");
                    String dest = input.nextLine();
                    if(checkDestination(dest) && !dest.equals(flight.getSetOff())){
                        Flight newFlight = new Flight(flight.getID(), flight.getPlane(), flight.getSetOff(),
                                flight.getDestination(), flight.getDepartTime(),flight.getPricePerSeat());
                        flightSystem.removeFlight(flight);
                        flightSystem.addFlight(newFlight);
                    }
                    else{
                        System.out.println("Wrong destination information");
                    }
                    break;
                case 4:
                    System.out.println("Please enter new set off information");
                    String setOff = input.nextLine();
                    if(checkSetOff(setOff) && !setOff.equals(flight.getDestination())){
                        Flight newFlight = new Flight(flight.getID(), flight.getPlane(), setOff,
                                flight.getDestination(), flight.getDepartTime(),flight.getPricePerSeat());
                        flightSystem.removeFlight(flight);
                        flightSystem.addFlight(newFlight);
                    }
                    else
                        System.out.println("Wrong set off information");
                    break;
                case 5:
                    System.out.println("Please enter new depart time information");
                    String dt = input.nextLine();
                    Flight newFlight = new Flight(flight.getID(), flight.getPlane(), flight.getSetOff(),
                            flight.getDestination(),dt,flight.getPricePerSeat());
                    flightSystem.removeFlight(flight);
                    flightSystem.addFlight(newFlight);
                    break;
                case 6:
                    createCrew(flight);
                    break;
                case 7:
                    removeCrew(flight);
                    break;
            }
        }
    }

    /** Remove employees to the flight
     * @param flight The flight
     */
    private void removeCrew(Flight flight){
        int choice = 1;
        while (choice != 0) {
            System.out.println("\nRemove crew menu:");
            System.out.println("Please choose an action:");
            System.out.println("0-Up\n1-Remove a crew member");
            System.out.print("\nchoice:");
            choice = input.nextInt();
            input.nextLine();// Consume newline left-over
            if (choice == 1) {
                System.out.println("Please enter crew member id");
                String ID = input.nextLine();
                boolean check = false;
                ArrayList<User> crew = flight.getCrew();
                int pos = 0;
                for (int i = 0; i < crew.size(); i++) {
                    if (crew.get(i).getID().equals(ID)) {
                        pos = i;
                        check = true;
                    }
                }
                if (!check)
                    System.out.println("Wrong id");
                else {
                    if(crew.get(pos) instanceof Pilot){
                        Pilot pilot = (Pilot) crew.get(pos);
                        pilot.setFlight(null);
                    }
                    else if(crew.get(pos) instanceof Hostess){
                        Hostess hostess = (Hostess) crew.get(pos);
                        hostess.setFlight(null);
                    }
                    crew.remove(pos);
                }
            }
        }
    }

}
