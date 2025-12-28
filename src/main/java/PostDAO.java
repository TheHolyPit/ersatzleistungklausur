import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public void create(Post post) throws SQLException {
        String sql = "INSERT INTO post (user_id, title, content) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getTitle());
            ps.setString(3, post.getContent());
            ps.executeUpdate();
        }
    }

    public List<Post> findAll() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM post";
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
        return posts;
    }

    public void update(Post post) throws SQLException {
        String sql = "UPDATE post SET title=?, content=? WHERE id=?";
        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setInt(3, post.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM post WHERE id=?";
        try (Connection con = DatabaseConnection.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
