import java.io.FileNotFoundException;

public class AdminTester {
    public static void main(String[] args) throws FileNotFoundException {
       Admin admin = new Admin("admin2","admin2", new AirlineSystem());

       admin.login();
    }
}
