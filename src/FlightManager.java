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
            System.out.println("Flight Manager Login");
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
        int choice = 1;
        while (choice!=0){
            System.out.println("\nMain menu:");
            System.out.println("Please choose an action:");
            System.out.println("0-Up\n1-Create a flight\n2-Remove a flight\n3-Modify a flight");
            System.out.print("\nchoice:");
            choice = input.nextInt();
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
            }
        }
    }

    /** Adds Flight */
    public void addFlight(){
        Flight tempFlight = new Flight("",new Plane("",0),"","",""); //for only check
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
        boolean checkDest = checkDestination(dest);
        boolean checkSetOff = checkSetOff(setOff);
        if(!printError(tempFlight, plane,new Pilot("0","0"),new Hostess("0","0"),
                checkDest,checkSetOff)) {
            Flight newFlight = new Flight(flightID, plane, dest, setOff, depart);
            createCrew(newFlight);
        }
    }

    /** Removes Flight */
    public void removeFlight(){
        System.out.println("Please enter flight id");
        String flightID = input.nextLine();
        System.out.println("Please enter destination");
        String dest = input.nextLine();
        Flight flight = findFlight(flightID,dest);
        if(!printError(flight,new Plane("",0),new Pilot("0","0"),
                new Hostess("0","0"),true,true))
            flightSystem.removeFlight(flight);
    }

    /** Modifies Flight */
    public void modifyFlight(){
        System.out.println("Please enter flight id");
        String flightID = input.nextLine();
        System.out.println("Please enter destination");
        String dest = input.nextLine();
        Flight flight = findFlight(flightID,dest);
        if(!printError(flight,new Plane("",0),new Pilot("0","0"),
                new Hostess("0","0"),true,true))
        {
            setFlight(flight);
        }
    }

    /** Returns required flight
     * @param id The flight id
     * @param dest The destination information
     * @return The flight if exist, null otherwise
     */
    private Flight findFlight(String id, String dest){

        Map<String, PriorityQueue<Flight>> map= flightSystem.getFlight_map();
        PriorityQueue<Flight> flights = map.get(dest);
        if(flights == null)
            return null;
        else{
            for (Flight temp : flights) {
                if (temp.getID().equals(id))
                    return temp;
            }
            return null;
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
    private boolean checkDestination(String dest){
        return true;
    }

    /**
     * Check validity of set off information
     * @param so The set off information
     * @return True if set off is exist, false otherwise
     */
    private boolean checkSetOff(String so){
        return true;
    }

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
     */
    private void createCrew(Flight flight) {
        int choice = 1;
        while (choice != 0) {
            System.out.println("\nAdd crew menu:");
            System.out.println("Please choose an action:");
            System.out.println("0-Up\n1-Add a pilot\n2-Add a hostess");
            System.out.print("\nchoice:");
            choice = input.nextInt();
            Flight tempFlight = new Flight("",new Plane("",0),"","","");
            switch (choice) {
                case 1:
                    System.out.println("Please enter pilot id");
                    String pilotID = input.nextLine();
                    Pilot pilot = findPilot(pilotID);
                    if(!printError(tempFlight,new Plane("0",0),pilot,
                            new Hostess("0","0"),true,true)){
                        flight.addCrewMember(pilot);
                    }
                    break;
                case 2:
                    System.out.println("Please enter hostess id");
                    String hostessID = input.nextLine();
                    Hostess hostess = findHostess(hostessID);
                    if(!printError(tempFlight,new Plane("0",0),new Pilot("0","0"),
                            hostess,true,true)){
                        flight.addCrewMember(hostess);
                    }
                    break;
            }
        }
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
            if(u instanceof Pilot)
                return (Pilot)u;
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
            if(u instanceof Hostess)
                return (Hostess)u;
            else
                return null;
        }
    }

    /**
     * Sets flight
     * @param flight The flight
     */
    private void setFlight(Flight flight){

    }
    //testTT
}
