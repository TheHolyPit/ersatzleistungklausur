/**
 * POJO-Klasse (Plain Old Java Object) für die Tabelle "user".
 * Enthält Felder, Konstruktoren und Getter/Setter.
 *
 * Felder:
 * - id: Primärschlüssel in der Datenbank
 * - username: Benutzername
 * - email: E-Mail-Adresse
 * - password: Passwort
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String password;

    /**
     * Konstruktor für User mit ID (z.B. beim Lesen aus DB)
     */
    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Konstruktor ohne ID (für neue User vor Insert)
     */
    public User(String username, String email, String password) {
        this(0, username, email, password);
    }

    // Getter und Setter
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
