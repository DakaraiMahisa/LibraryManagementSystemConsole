package librarymanagementsystem.service;

import librarymanagementsystem.database.LibraryDatabase;
import librarymanagementsystem.entity.Book;

public class UserService {
private final LibraryDatabase database;
private AuthService authService;
private Book book;
public UserService(LibraryDatabase database, AuthService authService,Book book) {
    this.database = database;
    this.authService = authService;
    this.book = book;
}

public void addBookOrModifyBookDetails(String title,String author,String isbn,Integer quantity) {
    if (authService.isAuthenticated() && authService.isAdmin()) {
        if (database.getBookInventory().containsKey(isbn)) {
            Book existingBook = database.getBookInventory().get(isbn);
            existingBook.setQuantity(existingBook.getQuantity() + quantity);
            existingBook.setAvailableQuantity(existingBook.getAvailableQuantity() + quantity);
            System.out.println("Book details updated successfully. New quantity: " + existingBook.getQuantity());
        } else {
            Book newBook = new Book(title, author, isbn, quantity);
            newBook.setAvailableQuantity(quantity);
            database.getBookInventory().put(isbn, newBook);
            System.out.println("Book added successfully to the inventory.");
        }
    }


}
}
