import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) für User.
 * Diese Klasse enthält alle SQL-Operationen (CRUD) für die User-Tabelle.
 */
public class UserDAO {
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    /**
     * Fügt einen neuen User in die Datenbank ein.
     * Die generierte ID wird automatisch im User-Objekt gesetzt.
     *
     * @param user Das User-Objekt (ID wird ignoriert und neu generiert)
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public void create(User user) throws SQLException {
        String sql = "INSERT INTO user (username, email, password) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("User konnte nicht erstellt werden, keine Zeilen wurden eingefügt.");
            }

            // Generierte ID abrufen und im User-Objekt setzen
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                    logger.log(Level.INFO, "User erstellt mit ID: {0}", user.getId());
                } else {
                    throw new SQLException("User wurde erstellt, aber keine ID wurde zurückgegeben.");
                }
            }
        }
    }

    /**
     * Sucht einen User anhand der ID.
     *
     * @param id Die User-ID
     * @return User-Objekt oder null, wenn nicht gefunden
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public User findById(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id = ?";

        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Sucht einen User anhand des Usernames.
     *
     * @param username Der Username
     * @return User-Objekt oder null, wenn nicht gefunden
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ?";

        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Liest alle User aus der Datenbank.
     *
     * @return Liste aller User
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user ORDER BY id";

        try (Connection con = DatabaseConnection.connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }
        }

        logger.log(Level.INFO, "{0} User gefunden", users.size());
        return users;
    }

    /**
     * Aktualisiert einen existierenden User.
     *
     * @param user Das User-Objekt mit aktualisierten Daten
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public void update(User user) throws SQLException {
        String sql = "UPDATE user SET username=?, email=?, password=? WHERE id=?";

        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("User mit ID " + user.getId() + " konnte nicht aktualisiert werden.");
            }

            logger.log(Level.INFO, "User mit ID {0} wurde aktualisiert", user.getId());
        }
    }

    /**
     * Löscht einen User anhand der ID.
     * Achtung: Durch CASCADE werden auch alle zugehörigen Posts gelöscht!
     *
     * @param id ID des Users
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id=?";

        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("User mit ID " + id + " konnte nicht gelöscht werden.");
            }

            logger.log(Level.INFO, "User mit ID {0} wurde gelöscht", id);
        }
    }

    /**
     * Prüft, ob ein Username bereits existiert.
     *
     * @param username Der zu prüfende Username
     * @return true, wenn der Username existiert
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";

        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}