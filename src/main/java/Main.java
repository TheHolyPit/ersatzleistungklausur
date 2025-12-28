/**
 * Main-Klasse zum Testen aller Funktionen.
 * Fügt User und Post ein, aktualisiert sie, gibt sie aus und löscht sie wieder.
 */
public class Main {
    public static void main(String[] args) {
        try {
            UserDAO userDAO = new UserDAO();
            PostDAO postDAO = new PostDAO();

            // Neuen User erstellen
            User user = new User("Capi", "capi@mail.de", "1234");
            userDAO.create(user);

            // Neuen Post für diesen User erstellen
            Post post = new Post(user.getId(), "Mein erster Post", "Hello World!");
            postDAO.create(post);

            // User und Post aktualisieren
            user.setEmail("capineu@mail.de");
            userDAO.update(user);

            post.setContent("Update Inhalt!");
            postDAO.update(post);

            // Ausgabe aller User und Posts
            userDAO.findAll().forEach(u -> System.out.println(u.getUsername() + " | " + u.getEmail()));
            postDAO.findAll().forEach(p -> System.out.println(p.getTitle() + " | " + p.getContent()));

            // Löschen
            postDAO.delete(post.getId());
            userDAO.delete(user.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
