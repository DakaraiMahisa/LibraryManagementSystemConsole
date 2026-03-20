package librarymanagementsystem;

import librarymanagementsystem.config.AppConfig;
import librarymanagementsystem.config.Menu;


public class LibraryManagementApp {
    public static void main(String[] args) {

        AppConfig appConfig = new AppConfig();
        appConfig.initialize();
        Menu menu = appConfig.getMenu();
        menu.start();
    }
}
