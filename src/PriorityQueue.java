
import java.util.*;
/** The KWPriorityQueue implements the Queue interface
 *   by building a heap in an ArrayList. The heap is structured
 *   so that the "smallest" item is at the top.
 *   @author Koffman and Wolfgang
 */
public class PriorityQueue<E> extends AbstractQueue <E>  implements Queue < E > {

    // Data Fields
    /** The ArrayList to hold the data. */
    private ArrayList<E> theData;

    /** An optional reference to a Comparator object. */
    Comparator< Flight > comparator = null;

    // Methods
    // Constructor
    public PriorityQueue() {
        theData = new ArrayList<E> ();
    }

    /** Creates a heap-based priority queue with the specified initial
     capacity that orders its elements according to the specified
     comparator.
     @param cap The initial capacity for this priority queue
     @param comp The comparator used to order this priority queue
     @throws IllegalArgumentException if cap is less than 1
     */
    public PriorityQueue(int cap, Comparator<Flight> comp) {
        if (cap < 1)
            throw new IllegalArgumentException();
        theData = new ArrayList < E > (cap + 1);
        comparator = comp;
    }

    /** Insert an item into the priority queue.
     pre: The ArrayList theData is in heap order.
     post: The item is in the priority queue and
     theData is in heap order.
     @param item The item to be inserted
     @throws NullPointerException if the item to be inserted is null.
     */
    public boolean offer(E item) {
        // Add the item to the heap.
        theData.add(item);
        // child is newly inserted item.
        int child = theData.size() - 1;
        int parent = (child - 1) / 2; // Find child�s parent.
        // Reheap
        Flight pflight = ( Flight ) theData.get(parent);
        Flight cflight = ( Flight ) theData.get(child);

        while (parent >= 0 && compare( pflight, cflight) > 0) {

            Collections.swap(theData,parent, child);
            child = parent;
            parent = (child - 1) / 2;
            pflight = ( Flight ) theData.get(parent);
            cflight = ( Flight ) theData.get(child);

        }
        return true;
    }


    /** Remove an item from the priority queue
     pre: The ArrayList theData is in heap order.
     post: Removed smallest item, theData is in heap order.
     @return The item with the smallest priority value or null if empty.
     */

    /** Remove an item from the priority queue
     pre: The ArrayList theData is in heap order.
     post: Removed smallest item, theData is in heap order.
     @return The item with the smallest priority value or null if empty.
     */
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        // Save the top of the heap.
        E result = theData.get(0);
        // If only one item then remove it.
        if (theData.size() == 1) {
            theData.remove(0);
            return result;
        }
    /* Remove the last item from the ArrayList and place it into
       the first position. */
        theData.set(0, theData.remove(theData.size() - 1));
        // The parent starts at the top.
        int parent = 0;
        while (true) {
            int leftChild = 2 * parent + 1;
            if (leftChild >= theData.size()) {
                break; // Out of heap.
            }
            int rightChild = leftChild + 1;
            int minChild = leftChild; // Assume leftChild is smaller.
            // See whether rightChild is smaller.
            if (rightChild < theData.size() && compare((Flight ) theData.get(leftChild),(Flight ) theData.get(rightChild)) > 0) {
                minChild = rightChild;
            }
            // assert: minChild is the index of the smaller child.
            // Move smaller child up heap if necessary.
            if (compare((Flight)theData.get(parent), (Flight) theData.get(minChild)) > 0) {
                Collections.swap(theData,parent, minChild);
                parent = minChild;
            }
            else { // Heap property is restored.
                break;
            }
        }
        return result;
    }

    /** Compare two items using either a Comparator object�s compare method
     or their natural ordering using method compareTo.
     pre: If comparator is null, left and right implement Comparable<E>.
     @return Negative int if left less than right,
     0 if left equals right,
     positive int if left > right
     @throws ClassCastException if items are not Comparable
      * @param left One item
     * @param right The other item
     */
    private int compare(Flight left, Flight right) {

        if (comparator != null) { // A Comparator is defined.
            return comparator.compare(left, right);
        }
        else { // Use left�s compareTo method.
            int res  =  left.compareTo(right.getDepartTime());
            //  System.out.println(" ?? " + res);
            return res;
        }
    }

    public int size() {
        return theData.size();
    }

    public Iterator < E > iterator() {
        return null;
    }

    public E peek() {
        return theData.get(0);
    }

    public void printQueue() {
        for ( int i = 0; i < theData.size(); i++ ) {
            Flight flight = ( Flight ) theData.get(i);
            System.out.println(flight.getDepartTime());
        }
    }
    /**** END EXERCISE ****/
}
