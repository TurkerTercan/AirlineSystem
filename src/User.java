public abstract class User implements Login, Comparable<User>{
    private String id;
    private String password;

    public String getID() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public int compareTo(User o) {
        return id.compareTo(o.id);
    }
}
