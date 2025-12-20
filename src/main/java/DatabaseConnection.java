import java.sql.*;
// In diesem Java Code wird die Verbindung zwischen Java und der SQL hergestellt, bzw. sollte so sein, ich arbeite noch dran.
public class DatabaseConnection {
    private static final String URL = "";
    private static final String User = "root";
    private static final String password = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, User, password);
    }
}
