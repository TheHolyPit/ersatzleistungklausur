import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "";
    private static final String User = "root";
    private static final String password = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, User, password);
    }
}
