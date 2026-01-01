import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main-Klasse zum Testen aller Funktionen.
 * Demonstriert CRUD-Operationen für User und Posts.
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        PostDAO postDAO = new PostDAO();

        try {
            System.out.println("=== Datenbank CRUD Operationen Demo ===\n");

            // ========== USER OPERATIONEN ==========
            System.out.println("--- USER CREATE ---");
            User user1 = new User("Capi", "capi@mail.de", "hashed_password_123");
            userDAO.create(user1);
            System.out.println("User erstellt: " + user1);

            User user2 = new User("Max", "max@example.com", "hashed_password_456");
            userDAO.create(user2);
            System.out.println("User erstellt: " + user2);

            // ========== POST OPERATIONEN ==========
            System.out.println("\n--- POST CREATE ---");
            Post post1 = new Post(user1.getId(), "Mein erster Post", "Hello World! Dies ist mein erster Beitrag.");
            postDAO.create(post1);
            System.out.println("Post erstellt: " + post1);

            Post post2 = new Post(user1.getId(), "Zweiter Post", "Noch mehr interessanter Inhalt!");
            postDAO.create(post2);
            System.out.println("Post erstellt: " + post2);

            Post post3 = new Post(user2.getId(), "Max's Post", "Hallo von Max!");
            postDAO.create(post3);
            System.out.println("Post erstellt: " + post3);

            // ========== READ OPERATIONEN ==========
            System.out.println("\n--- USER READ (findAll) ---");
            List<User> allUsers = userDAO.findAll();
            allUsers.forEach(u -> System.out.println("  " + u.getUsername() + " | " + u.getEmail()));

            System.out.println("\n--- POST READ (findAll) ---");
            List<Post> allPosts = postDAO.findAll();
            allPosts.forEach(p -> System.out.println("  [" + p.getId() + "] " + p.getTitle() + " | " + p.getContent()));

            System.out.println("\n--- POST READ (findByUserId für Capi) ---");
            List<Post> capiPosts = postDAO.findByUserId(user1.getId());
            System.out.println("Capi hat " + capiPosts.size() + " Post(s):");
            capiPosts.forEach(p -> System.out.println("  " + p.getTitle()));

            // ========== UPDATE OPERATIONEN ==========
            System.out.println("\n--- USER UPDATE ---");
            user1.setEmail("capi_neu@mail.de");
            userDAO.update(user1);
            System.out.println("User aktualisiert: " + user1);

            System.out.println("\n--- POST UPDATE ---");
            post1.setContent("Updated Inhalt! Dieser Text wurde geändert.");
            postDAO.update(post1);
            System.out.println("Post aktualisiert: " + post1);

            // ========== WEITERE ABFRAGEN ==========
            System.out.println("\n--- FIND BY ID ---");
            User foundUser = userDAO.findById(user1.getId());
            System.out.println("User gefunden: " + foundUser);

            Post foundPost = postDAO.findById(post1.getId());
            System.out.println("Post gefunden: " + foundPost);

            System.out.println("\n--- USERNAME CHECK ---");
            boolean exists = userDAO.usernameExists("Capi");
            System.out.println("Username 'Capi' existiert: " + exists);

            System.out.println("\n--- POST COUNT ---");
            int postCount = postDAO.countByUserId(user1.getId());
            System.out.println("Anzahl Posts von Capi: " + postCount);

            // ========== DELETE OPERATIONEN ==========
            System.out.println("\n--- DELETE POST ---");
            postDAO.delete(post2.getId());
            System.out.println("Post mit ID " + post2.getId() + " gelöscht");

            System.out.println("\n--- DELETE USER (CASCADE) ---");
            userDAO.delete(user1.getId());
            System.out.println("User mit ID " + user1.getId() + " gelöscht (Posts wurden durch CASCADE auch gelöscht)");

            // ========== FINAL CHECK ==========
            System.out.println("\n--- FINAL STATE ---");
            System.out.println("Verbleibende User:");
            userDAO.findAll().forEach(u -> System.out.println("  " + u));

            System.out.println("\nVerbleibende Posts:");
            postDAO.findAll().forEach(p -> System.out.println("  " + p));

            System.out.println("\n=== Demo erfolgreich abgeschlossen! ===");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Datenbankfehler aufgetreten", e);
            System.err.println("Fehler: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unerwarteter Fehler", e);
            System.err.println("Unerwarteter Fehler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}