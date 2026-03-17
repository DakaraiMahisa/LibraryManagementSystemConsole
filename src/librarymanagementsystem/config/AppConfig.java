package librarymanagementsystem.config;

import librarymanagementsystem.database.LibraryDatabase;
import librarymanagementsystem.entity.User;
import librarymanagementsystem.enums.Role;
import librarymanagementsystem.enums.UserType;

public class AppConfig {
    private final LibraryDatabase libraryDatabase;
    public AppConfig() {
        this.libraryDatabase = new LibraryDatabase();
    }
    public void initialize() {
        bootstrapAdmin();
    }
    public void bootstrapAdmin() {
        String adminName = System.getenv("ADMIN_NAME");
        String adminUserId = System.getenv("ADMIN_USER_ID");
        String adminEmail = System.getenv("ADMIN_EMAIL");
        String adminPassword = System.getenv("ADMIN_PASSWORD");
        if(adminName == null || adminUserId == null || adminEmail == null || adminPassword == null) {
            throw new IllegalStateException("Admin user details are not set in environment variables");
        }
        if(libraryDatabase.getUserMap().containsKey(adminEmail)) {
            System.out.println("Admin user already exists. Skipping bootstrap.");
            return;
        }else{
            User adminUser = new User(adminName, adminUserId, adminEmail, adminPassword, UserType.STAFF, Role.ADMIN);
            libraryDatabase.getUserMap().put(adminEmail, adminUser);
            System.out.println("Default admin created successfully.");
        }

    }
    public LibraryDatabase getLibraryDatabase() {
        return libraryDatabase;
    }
}
