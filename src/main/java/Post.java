import java.sql.Timestamp;
import java.util.Objects;

/**
 * POJO-Klasse für die Tabelle "post".
 * Repräsentiert einen Beitrag/Post in der Datenbank.
 *
 * Felder:
 * - id: Primärschlüssel (AUTO_INCREMENT)
 * - userId: Foreign Key zur User-Tabelle
 * - title: Titel des Posts
 * - content: Inhalt des Posts
 * - createdAt: Zeitstempel der Erstellung
 */
public class Post {
    private int id;
    private int userId;
    private String title;
    private String content;
    private Timestamp createdAt;

    /**
     * Vollständiger Konstruktor (beim Lesen aus der Datenbank)
     */
    public Post(int id, int userId, String title, String content, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    /**
     * Konstruktor ohne ID und Timestamp (für neue Posts vor dem Insert)
     */
    public Post(int userId, String title, String content) {
        this(0, userId, title, content, null);
    }

    // Getter
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}