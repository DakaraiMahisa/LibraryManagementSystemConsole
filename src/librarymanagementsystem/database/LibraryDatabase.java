package librarymanagementsystem.database;


import librarymanagementsystem.entity.Borrower;
import librarymanagementsystem.entity.Book;
import librarymanagementsystem.entity.User;

import java.util.HashMap;
import java.util.Map;

public class LibraryDatabase {

   private final Map<String, User> userMap = new HashMap<>();
   private final Map<String, Book> bookInventory = new HashMap<>();
   private final Map<String, Borrower> borrowers = new HashMap<>();

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public Map<String, Book> getBookInventory() {
        return bookInventory;
    }

    public Map<String, Borrower> getBorrowers() {
        return borrowers;
    }
}
