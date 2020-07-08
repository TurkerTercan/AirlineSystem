import java.util.*;
import java.io.FileNotFoundException;

/** Represents FlightManagers of airline system */
public class FlightManager extends User {
    /** Terminal Input */
    private Scanner input;
    /** Represent to if user logedIn */
    private boolean LogedIn = false;
    /** Reference to flightSystem */
    private FlightSystem flightSystem;
    /** Reference to Users */
    private SkipList<User> users;

    /**
     * Basic constructor
     * @param id FlightManager's identification
     * @param password FlightManager's password
     * @param flightSystem FlightSystem that holds flights
     * @param users SkipList that holds users
     */
    public FlightManager(String id, String password, FlightSystem flightSystem, SkipList<User> users) {
        super(id, password);
        input = new Scanner(System.in);
        this.flightSystem = flightSystem;
        this.users = users;
    }

    /**
     * Method to able to log in to the system
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
     * FlightManager's interface
     */
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
        System.out.println("Available Plane IDs : " +  flightSystem.getAvailablePlanes().first().getId() + " - "
                + flightSystem.getAvailablePlanes().last().getId());
        String planeID = input.nextLine();
        System.out.println("Please enter set off zone");
        String setOff = input.nextLine();
        System.out.println("Please enter destination");
        String dest = input.nextLine();
        System.out.println("Please enter depart time");
        String depart = input.nextLine();
        Plane plane = findPlane(planeID);
        System.out.println("Please enter price");
        double price = input.nextDouble();
        input.nextLine();// Consume newline left-over
        boolean checkDest = checkDestination(dest);
        boolean checkSetOff = checkSetOff(setOff);
        boolean checkEq = dest.equals(setOff);
        boolean checkDep = checkDepartTime(depart);
        boolean checkPrice = price < 0;
        if(checkDest && checkSetOff){
            if(findFlight(flightID,setOff,dest) != null)
                tempFlight = null;
        }
        if(checkEq)
            System.out.println("Set off and destination cannot be the same");
        if(!checkDep)
            System.out.println("Wrong depart time information");
        if(checkPrice)
            System.out.println("Flight price cannot be negative");
        if(!printError(tempFlight, plane,new Pilot("0","0"),new Hostess("0","0"),
                checkDest,checkSetOff) && !checkEq && checkDep && !checkPrice) {

                Flight newFlight = new Flight(flightID, plane, setOff, dest, depart, price);
                System.out.println("Flight has been created");
                if(flightSystem.addFlight(createCrew(newFlight)))
                    System.out.println("New flight has been added to the system.");
        }
    }

    /** Removes Flight */
    public void removeFlight(){
        Flight flight = getFlight();
        if(!printError(flight,new Plane("",0),new Pilot("0","0"),
                new Hostess("0","0"),true,true)){
            flight.removeAllCrew();
            if(flightSystem.removeFlight(flight))
                System.out.println("Flight has been removed from the system.");
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
        if(flight == null)
            System.out.println("Flight can not found");
        else
            System.out.println(flight.toString());
    }

    /** Takes information about flight and returns it
     * @return The flight
     */
    private Flight getFlight(){
        System.out.println("Please enter flight id");
        String flightID = input.nextLine();
        System.out.println("Please enter set off information");
        String setOff = input.nextLine();
        System.out.println("Please enter destination");
        String dest = input.nextLine();
        return findFlight(flightID,setOff,dest);
    }

    /** Returns required flight
     * @param id The flight id
     * @param setOff The setoff information
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
    protected Plane findPlane(String id){
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
     * Check validity of depart time information
     * @param dt The depart time information
     * @return True if set off is exist, false otherwise
     */
    private boolean checkDepartTime(String dt){
        if(dt.length() != 16)
            return false;
        else if(dt.charAt(2) == '/' && dt.charAt(5) == '/' && dt.charAt(10) == ',' && dt.charAt(13) == ':'){
            //Split parameter's departTime
            String [] time1 = dt.split("/");
            int day = Integer.parseInt(time1[0]);
            int month = Integer.parseInt(time1[1]);
            String [] time2 = time1[2].split(",");
            int year = Integer.parseInt(time2[0]);
            String [] time3 = time2[1].split(":");
            int hour = Integer.parseInt(time3[0]);
            int minute = Integer.parseInt(time3[1]);

            if(day < 0 || day > 31)
                return false;
            else if(month < 0 || month > 12)
                return false;
            else if(year < 0)
                return false;
            else if(hour < 0 || hour > 24)
                return false;
            else return minute >= 0 && minute <= 59;
        }
        else
            return false;
    }

    /**
     * Displays error
     * @param flight The flight
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
                    }
                    break;
                case 2:
                    System.out.println("Please enter hostess id");
                    String hostessID = input.nextLine();
                    Hostess hostess = findHostess(hostessID);
                    if(!printError(tempFlight,new Plane("0",0),new Pilot("0","0"),
                            hostess,true,true)){
                        hostess.setFlight(flight);
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
                else{
                    return pilot;}
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
            System.out.println("0-Up\n1-set id\n2-set plane\n3-set destination\n4-set set off information\n" +
                    "5-set departTime\n6-set price\n7-add crew\n8-remove crew");
            System.out.print("\nchoice:");
            choice = input.nextInt();
            input.nextLine();// Consume newline left-over
            switch (choice) {
                case 1:
                    System.out.println("Please enter new id");
                    String newID = input.nextLine();
                    flight.setID(newID);
                    System.out.println("Flight ID has been updated");
                    break;
                case 2:
                    System.out.println("Please enter new plane ID");
                    String planeID = input.nextLine();
                    Plane newPlane = findPlane(planeID);
                    if (newPlane != null) {
                        flight.setPlane(newPlane);
                        System.out.println("Plane has been updated");
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
                        System.out.println("Destination has been updated");
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
                        System.out.println("Set off information has been updated");
                    }
                    else
                        System.out.println("Wrong set off information");
                    break;
                case 5:
                    System.out.println("Please enter new depart time information");
                    String dt = input.nextLine();
                    if(!checkDepartTime(dt)) {
                        System.out.println("Wrong depart time information");
                    }
                    else {
                        Flight newFlight = new Flight(flight.getID(), flight.getPlane(), flight.getSetOff(),
                                flight.getDestination(), dt, flight.getPricePerSeat());
                        flightSystem.removeFlight(flight);
                        flightSystem.addFlight(newFlight);
                        System.out.println("Depart time has been updated");
                    }
                    break;
                case 6:
                    System.out.println("Please enter new price");
                    double price= input.nextDouble();
                    if(price < 0)
                        System.out.println("Flight price cannot be negative");
                    else {
                        flight.setPricePerSeat(price);
                        System.out.println("Price has been updated");
                    }
                    break;
                case 7:
                    createCrew(flight);
                    break;
                case 8:
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

    /**
     * The flight manager's methods are tested.
     */
    public static class FlightManagerTester {
        //Unique plane id that will be used for testing
        private static int plane_id = 0;
        //Unique pilot id that will be used for testing
        private static int pilot_id = 0;
        //Unique pilot id that will be used for testing
        private static int hostess_id = 120;

        private static final String test_city_file = "cities.txt";
        private static final String test_distances_file = "distances.txt";
        private static final Queue<Plane> test_queue = new LinkedList<>();

        public static void test_getPlane(int test_count) throws FileNotFoundException {
            System.out.println("Testing getPlane method of FlightManager for "+test_count+" times");
            FlightSystem system = new FlightSystem(test_city_file, test_distances_file, test_queue);
            FlightManager manager = new FlightManager("test", "test", system, null);

            //Adding 10 unique planes to the system
            for (int i = 0; i < test_count; i++) {
                system.addPlane(new Plane(String.valueOf(plane_id++), 50));
            }

            //Searching for added planes
            for (int i = 0; i < test_count; i++) {
                Plane temp = manager.findPlane(String.valueOf(--(plane_id)));
                if (temp != null) {
                    System.out.println("Plane "+temp.getId()+" has been found in the system");
                } else {
                    System.out.println("Plane "+plane_id+" has not been found in the system");
                }
            }
        }

        public static void test_findUser(int test_count) throws FileNotFoundException {
            System.out.println("Testing findPilot method of FlightManager for " + test_count + " times");
            SkipList<User> users = new SkipList<>();
            FlightManager manager = new FlightManager("test", "test", null, users);

            //Adding 10 unique pilot to the system
            for (int i = 0; i < test_count; i++) {
                manager.users.add(new Pilot(String.valueOf(pilot_id++), "test"));
            }

            //Adding 10 unique hostess to the system
            for (int i = 0; i < test_count; i++) {
                manager.users.add(new Hostess(String.valueOf(hostess_id++), "test"));
            }

            //Searching for added planes
            for (int i = 0; i < test_count; i++) {
                Pilot temp1 = manager.findPilot(String.valueOf(--(pilot_id)));
                Hostess temp2 = manager.findHostess(String.valueOf(--(hostess_id)));
                System.out.println(temp1);
                System.out.println(temp2);

                if (temp1 != null && temp2 != null) {
                    System.out.println("Pilot " + temp1.getID() + " has been found in the system");
                    System.out.println("Hostess " + temp2.getID() + " has been found in the system");
                } else {
                    System.out.println("Pilot " + pilot_id + " has not been found in the system");
                    System.out.println("Hostess " + hostess_id + " has not been found in the system");
                }
            }
        }

        public static void test_checkDep() {
            FlightManager manager = new FlightManager("asd","asd",null,null);
            String dt1 = "06/07/2020,12:30";
            String dt2 = "06/7/2020,13:30";
            String dt3 = "06/07/2020, 12:30";
            String dt4 = "34/07/2020,12:30";
            String dt5 = "03/13/2020,12:30";
            String dt6 = "03/07/202,12:30";
            String dt7 = "04/07/2020,26:30";
            String dt8 = "02/07/2020,12:60";

            System.out.println(manager.checkDepartTime(dt1));
            System.out.println(manager.checkDepartTime(dt2));
            System.out.println(manager.checkDepartTime(dt3));
            System.out.println(manager.checkDepartTime(dt4));
            System.out.println(manager.checkDepartTime(dt5));
            System.out.println(manager.checkDepartTime(dt6));
            System.out.println(manager.checkDepartTime(dt7));
            System.out.println(manager.checkDepartTime(dt8));

        }
        public static void test() throws FileNotFoundException {
            FlightSystem fs = new FlightSystem(test_city_file, test_distances_file, test_queue);
            SkipList<User> u = new SkipList<>();
            u.add(new Pilot("pilot1","asd"));
            u.add(new Pilot("pilot2","asd"));
            u.add(new Pilot("pilot3","asd"));
            u.add(new Pilot("pilot4","asd"));
            u.add(new Pilot("pilot5","asd"));
            u.add(new Pilot("pilot6","asd"));
            u.add(new Hostess("hostess1","asd"));
            u.add(new Hostess("hostess2","asd"));
            u.add(new Hostess("hostess3","asd"));
            u.add(new Hostess("hostess4","asd"));
            u.add(new Hostess("hostess5","asd"));
            u.add(new Hostess("hostess6","asd"));
            u.add(new Hostess("hostess7","asd"));
            u.add(new Hostess("hostess8","asd"));
            fs.addPlane(new Plane("Plane1",500));
            fs.addPlane(new Plane("Plane2",550));
            fs.addPlane(new Plane("Plane3",520));
            fs.addPlane(new Plane("Plane4",500));
            fs.addPlane(new Plane("Plane5",570));
            fs.addPlane(new Plane("Plane6",580));
            fs.addPlane(new Plane("Plane7",590));
            fs.addPlane(new Plane("Plane8",511));
            System.out.println(fs.getAvailablePlanes().size());
            FlightManager fm = new FlightManager("deha","asdasd",fs,u);
            fm.menu();
        }

        public static void main(String[] args) {
            try {
                FlightManagerTester.test_checkDep();
            } catch (Exception e) {
                System.out.println("ERROR");
            }
        }
    }
}
