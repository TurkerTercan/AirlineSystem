import java.util.ArrayList;

/**
 * This class represents a flight from this airline company.
 * It has all necessary data fields and methods for a flight
 */
public class Flight {
    //Data fields
    /** The ID of the flight */
    private String ID;
    /** The Plane that is will fly */
    private Plane plane;

    /** Destination address */
    private String destination;
    /** The plane's set off zone */
    private String setOff;
    /** The time that the plane will be departed */
    private String departTime;
    /** List of crew */
    private ArrayList<User> crew;
    /** List of customers */
    private ArrayList<Customer> customers;
    /** Total count of available seats */
    private int seatCount;
    /** Total number of remaining seats */
    private int remainingSeats;

    //Constructors
    public Flight( String ID, Plane plane, String destination, String setOff) {
        this.ID = ID;
        this.plane = plane;
        this.destination = destination;
        this.setOff = setOff;
    }

    //Methods
    /** Adds new crew member in to the arraylist */
    public void addCrewMember(User user){
        crew.add(user);
    }

    /** Adds new customer in to the list */
    public void addNewCustomer(Customer customer){
        customers.add(customer);
    }

    /**
     * Returns how many seats are available int this flight.
     * @return how many seats are available in this flight.
     */
    public int getRemainingSeats(){
        return remainingSeats;
    }

    /**
     * Override toString method to represent a String for this class
     * @return returns a String that represents a flight
     */
    @Override
    public String toString()  {
        return "Flight ID: " + ID + "\n" +
                "Set Off: " + setOff + "\tDestination: " + destination + "\n" +
                "Depart Time: " + departTime + "\n" +
                customers.size() + " Customers, " + crew.size() + " Crew members\n";
    }
}
