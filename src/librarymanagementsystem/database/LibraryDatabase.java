package librarymanagementsystem.database;


import librarymanagementsystem.entity.Book;
import librarymanagementsystem.entity.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LibraryDatabase {

   private final Map<String, User> userMap = new HashMap<>();
   private final Map<String, Book> bookInventory = new HashMap<>();
   private Set<Book> cart = new HashSet<>();

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public Map<String, Book> getBookInventory() {
        return bookInventory;
    }
    public Set<Book> getCart() {
        return cart;
    }

}
