import java.util.ArrayList;

/**
 * This class represents a flight from this airline company.
 * It has all necessary data fields and methods for a flight
 */
public class Flight<E> implements Comparable< E > {
    //Data fields
    /** The ID of the flight */
    private String ID;
    /** The Plane that is will fly */
    private Plane plane;

    /** Destination address */
    private String destination;
    /** The plane's set off zone */
    private String setOff;

    public String getDepartTime() {
        return departTime;
    }

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

    @Override
    public int compareTo(E o) {
        //Split departTime
        String[] time1 = departTime.split("/");
        int day =  Integer.parseInt(time1[0]);
        int  month = Integer.parseInt(time1[1]);

        String[] time2 = time1[2].split(",");

        int year = Integer.parseInt(time2[0]);
        String[] time3 = time2[1].split(":");
        int hour = Integer.parseInt(time3[0]);
        int minute = Integer.parseInt(time3[1]);

        //Split parameter
        time1 = o.toString().split("/");
        int oday = Integer.parseInt(time1[0]);
        int omonth = Integer.parseInt(time1[1]);

        time2 = time1[2].split(",");

        int oyear = Integer.parseInt(time2[0]);
        time3 = time2[1].split(":");
        int ohour = Integer.parseInt(time3[0]);
        int ominute = Integer.parseInt(time3[1]);

        //Compare year
        if(year < oyear) {
            return -1;
        } else if(year > oyear)
            return 1;

        //compare month
        if(month < omonth)
            return -1;
        else if(month > omonth)
            return 1;

        //compare day
        if(day < oday)
            return -1;
        else if(day > oday)
            return 1;

        //compare hour
        if(hour < ohour)
            return -1;
        else if(hour > ohour)
            return 1;

        //compare minute
        if(minute < ominute)
            return -1;
        else if(minute > ominute)
            return 1;

        return 0;
    }
}
