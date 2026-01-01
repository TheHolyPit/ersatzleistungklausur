import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) für Posts.
 * Enthält alle SQL-Operationen für die Post-Tabelle, inkl. Foreign Key-Beziehungen.
 */
public class PostDAO {
    private static final Logger logger = Logger.getLogger(PostDAO.class.getName());

    /**
     * Fügt einen neuen Post in die Datenbank ein.
     * Die generierte ID wird automatisch im Post-Objekt gesetzt.
     *
     * @param post Post-Objekt (userId muss existieren)
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public void create(Post post) throws SQLException {
        String sql = "INSERT INTO post (user_id, title, content) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getTitle());
            ps.setString(3, post.getContent());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Post konnte nicht erstellt werden, keine Zeilen wurden eingefügt.");
            }

            // Generierte ID abrufen und im Post-Objekt setzen
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                    logger.log(Level.INFO, "Post erstellt mit ID: {0}", post.getId());
                } else {
                    throw new SQLException("Post wurde erstellt, aber keine ID wurde zurückgegeben.");
                }
            }
        }
    }

    /**
     * Sucht einen Post anhand der ID.
     *
     * @param id Die Post-ID
     * @return Post-Objekt oder null, wenn nicht gefunden
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public Post findById(int id) throws SQLException {
        String sql = "SELECT * FROM post WHERE id = ?";

        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Post(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Liest alle Posts aus der Datenbank.
     *
     * @return Liste aller Posts (sortiert nach Erstellungsdatum, neueste zuerst)
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public List<Post> findAll() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM post ORDER BY created_at DESC";

        try (Connection con = DatabaseConnection.connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                posts.add(new Post(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at")
                ));
            }
        }

        logger.log(Level.INFO, "{0} Posts gefunden", posts.size());
        return posts;
    }

    /**
     * Sucht alle Posts eines bestimmten Users.
     *
     * @param userId Die User-ID
     * @return Liste aller Posts des Users
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public List<Post> findByUserId(int userId) throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM post WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    posts.add(new Post(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        }

        logger.log(Level.INFO, "{0} Posts für User-ID {1} gefunden", new Object[]{posts.size(), userId});
        return posts;
    }

    /**
     * Aktualisiert Titel und Inhalt eines Posts.
     *
     * @param post Post-Objekt mit neuer Info
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public void update(Post post) throws SQLException {
        String sql = "UPDATE post SET title=?, content=? WHERE id=?";

        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setInt(3, post.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Post mit ID " + post.getId() + " konnte nicht aktualisiert werden.");
            }

            logger.log(Level.INFO, "Post mit ID {0} wurde aktualisiert", post.getId());
        }
    }

    /**
     * Löscht einen Post anhand der ID.
     *
     * @param id Post-ID
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM post WHERE id=?";

        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Post mit ID " + id + " konnte nicht gelöscht werden.");
            }

            logger.log(Level.INFO, "Post mit ID {0} wurde gelöscht", id);
        }
    }

    /**
     * Löscht alle Posts eines bestimmten Users.
     *
     * @param userId Die User-ID
     * @return Anzahl der gelöschten Posts
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public int deleteByUserId(int userId) throws SQLException {
        String sql = "DELETE FROM post WHERE user_id=?";

        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            int affectedRows = ps.executeUpdate();

            logger.log(Level.INFO, "{0} Posts für User-ID {1} gelöscht", new Object[]{affectedRows, userId});
            return affectedRows;
        }
    }

    /**
     * Zählt alle Posts eines Users.
     *
     * @param userId Die User-ID
     * @return Anzahl der Posts
     * @throws SQLException falls ein SQL-Fehler auftritt
     */
    public int countByUserId(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM post WHERE user_id = ?";

        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}