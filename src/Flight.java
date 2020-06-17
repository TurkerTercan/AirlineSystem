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

    private String destination;
    private String setOff;
    private String departTime;
    private ArrayList<User> crew;
    private ArrayList<Customer> customers;
    private int seatCount;
    private int remainingSeats;

    //Constructors
    public Flight( String ID, Plane plane, String destination, String setOff) {
        this.ID = ID;
        this.plane = plane;
        this.destination = destination;
        this.setOff = setOff;
    }

}
