import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) für User.
 * Diese Klasse enthält alle SQL-Operationen (CRUD) für die User-Tabelle.
 */
public class UserDAO {

    /**
     * Fügt einen neuen User in die Datenbank ein.
     * @param user Das User-Objekt
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public void create(User user) throws SQLException {
        String sql = "INSERT INTO user (username, email, password) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Liest alle User aus der Datenbank.
     * @return Liste aller User
     * @throws SQLException
     */
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    /**
     * Aktualisiert einen existierenden User.
     * @param user Das User-Objekt mit aktualisierten Daten
     * @throws SQLException
     */
    public void update(User user) throws SQLException {
        String sql = "UPDATE user SET username=?, email=?, password=? WHERE id=?";
        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Löscht einen User anhand der ID.
     * @param id ID des Users
     * @throws SQLException
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id=?";
        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
