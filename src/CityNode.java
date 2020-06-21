@SuppressWarnings("rawtypes")
public class CityNode implements Comparable{
    private String city;
    private PriorityQueue<Flight> p_queue;
    private int size = 0;

    public CityNode(String city) {
        this.city = city;
        p_queue = new PriorityQueue<>();
    }

    public PriorityQueue<Flight> getPriorityQueue() {
        return p_queue;
    }

    public String getCity() {
        return city;
    }

    @Override
    public int compareTo(Object o) {
        return city.compareTo(((CityNode)o).city);
    }
    ///test
}
