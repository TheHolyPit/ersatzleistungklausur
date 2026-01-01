import java.util.Objects;

/**
 * POJO-Klasse (Plain Old Java Object) für die Tabelle "user".
 * Repräsentiert einen Benutzer in der Datenbank.
 *
 * Felder:
 * - id: Primärschlüssel in der Datenbank (AUTO_INCREMENT)
 * - username: Benutzername (eindeutig)
 * - email: E-Mail-Adresse
 * - password: Passwort (sollte gehasht gespeichert werden!)
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String password;

    /**
     * Konstruktor für User mit ID (z.B. beim Lesen aus der Datenbank)
     */
    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Konstruktor ohne ID (für neue User vor dem Insert)
     * Die ID wird nach dem Insert automatisch von der Datenbank generiert.
     */
    public User(String username, String email, String password) {
        this(0, username, email, password);
    }

    // Getter
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}