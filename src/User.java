/**
 * Class for all users implements Login and Comparable
 */
public class User implements Login, Comparable<User>{
    /**
     * User ID
     */
    private String id;
    /**
     * User Password
     */
    private String password;

    /**
     * Getter method for ID
     * @return ID
     */
    public String getID() {
        return id;
    }

    /**
     * Getter method for Password
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for Password
     * @param pass new Password
     */
    public void setPassword(String pass) {
        password = pass;
    }

    /**
     * Basic constructor for User class
     * @param id User's ID
     * @param password User's Password
     */
    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    /**
     * Compare method to compare Users' ID
     * @param o The object that will be compared
     * @return result of the compare
     */
    @Override
    public int compareTo(User o) {
        return id.compareTo(o.id);
    }

    /**
     * To check two users have same ID or not
     * @param id The given user's ID
     * @return If two users' identifications are the same return true, otherwise; false
     */
    public boolean equals(String id) {
        return (this.id.equals(id));
    }

    /**
     * Implemented for subclasses
     */
    @Override
    public void login() {
        //To be implemented in subclasses
    }

    /**
     * Implemented for subclasses
     */
    @Override
    public void menu() {
        //To be implemented in subclasses
    }
}
