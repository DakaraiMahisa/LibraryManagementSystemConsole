package librarymanagementsystem;

import librarymanagementsystem.config.AppConfig;
import librarymanagementsystem.config.Menu;
import librarymanagementsystem.database.LibraryDatabase;

public class LibraryManagementApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        appConfig.initialize();
        LibraryDatabase db = appConfig.getLibraryDatabase();
        Menu menu = new Menu(db);
        menu.start();
    }
}
