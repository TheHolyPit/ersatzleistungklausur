import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Verwaltet die Datenbankverbindung zur MySQL-Datenbank.
 * Lädt den JDBC-Treiber beim Klasseninitialisierung.
 */
public class DatabaseConnection {

    // Verwende Umgebungsvariablen für Production
    // Für Development können diese Werte verwendet werden
    private static final String URL = "jdbc:mysql://localhost:3306/datenbankersatzleistung_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Treiber einmalig beim Laden der Klasse initialisieren
    // HINWEIS: In modernen JDBC-Versionen (4.0+) ist das explizite Laden oft nicht nötig
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Warnung statt Fehler - moderne JDBC lädt Treiber automatisch
            System.err.println("WARNUNG: MySQL JDBC Driver nicht explizit gefunden.");
            System.err.println("Falls Sie JDBC 4.0+ verwenden, könnte der Treiber trotzdem funktionieren.");
            System.err.println("Anderenfalls fügen Sie mysql-connector-j-8.x.x.jar zum Classpath hinzu.");
        }
    }

    /**
     * Baut eine Verbindung zur MySQL-Datenbank auf.
     * @return Connection-Objekt
     * @throws SQLException wenn die Verbindung fehlschlägt
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Alternative Methode mit Umgebungsvariablen (für Production empfohlen)
     * Setzen Sie diese Umgebungsvariablen:
     * - DB_URL
     * - DB_USER
     * - DB_PASSWORD
     */
    public static Connection connectFromEnv() throws SQLException {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (url == null || user == null || password == null) {
            throw new SQLException("Umgebungsvariablen DB_URL, DB_USER oder DB_PASSWORD nicht gesetzt.");
        }

        return DriverManager.getConnection(url, user, password);
    }
}