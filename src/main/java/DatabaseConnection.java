import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/datenbankersatzleistung_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Baut eine Verbindung zur MySQL-Datenbank auf.
     * @return Connection-Objekt
     * @throws SQLException, ClassNotFoundException
     */
    public static Connection connect() throws SQLException, ClassNotFoundException {
        // Treiber laden (optional in neueren JDKs, aber sicher)
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Verbindung aufbauen
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
