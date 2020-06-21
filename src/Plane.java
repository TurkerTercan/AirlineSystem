@SuppressWarnings("rawtypes")
public class Plane implements Comparable{
    //Unique identification for planes
    private static int PLANE_ID = 0;
    
    //Identification number of the plane
    private String id;

    //Capacity of the plane
    private int capacity;

    public Plane(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    Plane(int capacity) {
        this.id = String.valueOf(PLANE_ID++); 
        this.capacity = capacity;
    }
    
    //Getter methods
    public int getCapacity() {
        return capacity;
    }

    public String getId() {
        return id;
    }

    @Override
    public int compareTo(Object o) {
        return this.capacity - ((Plane)o).capacity;
    }
}
