/**
 * Plane class represents a plane in our AirlineSystem
 */
public class Plane implements Comparable<Plane>{
    /**
     * Unique identification for planes
     */
    private static int PLANE_ID = 0;

    /**
     * Identification number of the plane
     */
    private String id;

    /**
     * Capacity of the plane
     */
    private int capacity;

    /**
     * Constructs a plane with id and capacity
     * @param id Plane's ID
     * @param capacity Plane's maximum capacity
     */
    public Plane(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    /**
     * Construct a plane with capacity
     * @param capacity Plane's maximum capacity
     */
    Plane(int capacity) {
        this.id = String.valueOf(PLANE_ID++);
        this.capacity = capacity;
    }
    
    //Getter methods

    /**
     * Getter method for capacity
     * @return Plane's capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Getter method for ID
     * @return Plane's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Compare method for Comparable interface
     * @param o The object that will be compared
     * @return extraction of capacities
     */
    @Override
    public int compareTo(Plane o) {
        int tempCap1 = capacity * 1000 + Integer.parseInt(id);
        int tempCap2 = o.capacity * 1000 + Integer.parseInt(o.id);
        return tempCap1 - tempCap2;
    }


}
