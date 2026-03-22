package librarymanagementsystem.service;

import librarymanagementsystem.database.LibraryDatabase;
import librarymanagementsystem.entity.Book;
import librarymanagementsystem.entity.Borrower;
import librarymanagementsystem.entity.User;
import librarymanagementsystem.enums.Role;
import librarymanagementsystem.enums.UserType;

public class AdminService {
    private final LibraryDatabase database;
    private AuthService authService;
    public AdminService(LibraryDatabase database, AuthService authService) {
        this.database = database;
        this.authService = authService;
    }

    public void addBookOrModifyBookDetails(String title, String author, String isbn, Integer quantity) {
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
        } else {
            System.out.println("Unauthorized access. " +
                    "Only admins can add or modify book details.");
        }
    }

    public void addOtherAdmins(String email) {
        if (authService.isAuthenticated() && authService.isAdmin()) {
            if (database.getUserMap().containsKey(email)) {
                User user = database.getUserMap().get(email);
                Role currentRole = user.getRole();
                UserType userType = user.getUserType();
                if (userType == UserType.STUDENT) {
                    System.out.println("User is a student and cannot be promoted to admin.");
                    return;
                }
                if (currentRole == Role.ADMIN) {
                    System.out.println("User is already an admin.");
                } else {
                      user.setRole(Role.ADMIN);
                      System.out.println("User role updated to admin successfully.");
                }
            } else {
                System.out.println("User with email " + email + " does not exist.");
            }
        } else {
            System.out.println("Unauthorized access. " +
                    "Only admins can promote users to admin.");
        }
    }

      public void viewBookInventorySortedByTitle(){
        if(authService.isAuthenticated()&& authService.isAdmin()){
            database.getBookInventory().values().stream()
                    .sorted((b1, b2) ->
                            b1.getTitle().compareToIgnoreCase(b2.getTitle()))
                    .forEach(book -> System.out.println("Title: " +
                            book.getTitle() + ", Author: " + book.getAuthor() +
                            ", ISBN: " + book.getIsbn() +
                            ", Quantity: " + book.getQuantity()));
        } else {
            System.out.println("Unauthorized access. " +
                    "Please log in to view the book inventory.");
        }
      }

      public void viewBookInventrySortedByAvailableQuantity(){
        if(authService.isAuthenticated()&& authService.isAdmin()){
            database.getBookInventory().values().stream()
                    .sorted((b1,b2)->Integer.compare(
                            (b2.getAvailableQuantity()),
                            (b1.getAvailableQuantity())))
                    .forEach(book -> System.out.println("Title: " +
                            book.getTitle() + ", Author: " + book.getAuthor() +
                            ", ISBN: " + book.getIsbn() +
                            ", Available Quantity: " +
                            book.getAvailableQuantity()));
        }else{
            System.out.println("Unauthorized access. " +
                    "Please log in to view the book inventory.");
        }
      }
public void searchBooksByTitle(String title){
        if(authService.isAuthenticated()&& authService.isAdmin()){
            database.getBookInventory().values().stream()
                    .filter(book->book.getTitle().
                            toLowerCase().contains(title.toLowerCase()))
                    .forEach(book -> System.out.println("Title: "
                            +book.getTitle() + ", Author: " + book.getAuthor() +
                            ", ISBN: " + book.getIsbn() +
                            ", Available Quantity: "
                            + book.getAvailableQuantity()));
        }else{
            System.out.println("Unauthorized access. " +
                    "Please log in to search for books.");
        }
}
public void searchBooksByISBN(String isbn) {
    if (authService.isAuthenticated()&& authService.isAdmin()) {
        database.getBookInventory().values().stream()
                .filter(book -> book.getIsbn().equalsIgnoreCase(isbn))
                .forEach(book -> System.out.println("Title: "
                        + book.getTitle() + ", Author: " + book.getAuthor() +
                        ", ISBN: " + book.getIsbn() +
                        ", Available Quantity: "
                        + book.getAvailableQuantity()));
    } else {
        System.out.println("Unauthorized access. " +
                "Please log in to search for books.");
    }
}
    public void manageBorrowers(String email, double newLimit) {

        if (!(authService.isAuthenticated() && authService.isAdmin())) {
            System.out.println("Unauthorized access. Please log in as admin.");
            return;
        }

        if (newLimit < 0) {
            System.out.println("Fine limit must be non-negative.");
            return;
        }

        Borrower borrower = database.getBorrowers().get(email);

        if (borrower == null) {
            System.out.println("This user has not borrowed any books yet.");
            return;
        }

        double oldLimit = borrower.getFineLimit();
        borrower.setFineLimit(newLimit);

        System.out.println("Fine limit updated from " + oldLimit + " to " + newLimit);

        if (borrower.getCurrentFine() > borrower.getFineLimit()) {
            System.out.println("Warning: Borrower has exceeded the new fine limit and will be blocked from borrowing.");
        }
    }

    public void viewBorrowers() {
    }
}