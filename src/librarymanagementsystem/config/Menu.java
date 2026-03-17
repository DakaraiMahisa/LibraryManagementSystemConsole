package librarymanagementsystem.config;

import librarymanagementsystem.database.LibraryDatabase;

public class Menu {

    private final LibraryDatabase libraryDatabase;
    public Menu(LibraryDatabase libraryDatabase) {
        this.libraryDatabase = libraryDatabase;
    }
    public static void displayMainMenu() {
        System.out.println("Welcome to the Library Management System");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }

    public static void displayUserMenu() {
        System.out.println("User Menu:");
        System.out.println("1. View Books");
        System.out.println("2. Borrow Book");
        System.out.println("3. Return Book");
        System.out.println("4. Logout");
    }

    public static void displayAdminMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1. Add Book");
        System.out.println("2. Remove Book");
        System.out.println("3. View All Users");
        System.out.println("4. Logout");
    }

    public void start() {
        boolean running = true;
            while (running) {
                displayMainMenu();
            }
    }
}
