package librarymanagementsystem.service;


import librarymanagementsystem.database.LibraryDatabase;
import librarymanagementsystem.entity.Book;
import librarymanagementsystem.entity.Borrower;
import librarymanagementsystem.entity.User;

public class BorrowerService {
    private final LibraryDatabase database;
    private AuthService authService;
    public BorrowerService(LibraryDatabase database, AuthService authService) {
        this.database = database;
        this.authService = authService;

    }

    public void viewListOfBooksAvailable(){

        if(!authService.isAuthenticated()){
            System.out.println("Unauthorized access. " +
                    "Please log in to view the list of books available.");
            return;
        }
        if(database.getBookInventory().isEmpty()){
            System.out.println("Book Inventory is empty!");
            return;
        }
            System.out.println("Books available in the library:");
            database.getBookInventory().values().forEach(book->{
                System.out.println("Title: " + book.getTitle() +
                        ", Author: " + book.getAuthor() + ", ISBN: " +
                        "" + book.getIsbn() + ", Available Quantity: "
                        + book.getAvailableQuantity());
            });
    }
    public void selectBookByISBNandAddToCart(String isbn){
        if(!authService.isAuthenticated()){
            System.out.println("Unauthorized access. " +
                    "Please log in to select a book and add to cart.");
            return;
        }
            Book book = database.getBookInventory().get(isbn);
            Borrower borrower = getOrCreateBorrower();
            if(book != null && book.getAvailableQuantity() > 0){
                borrower.getCart().add(book);
                System.out.println("Book added to cart: " + book.getTitle());
            }else {
                System.out.println("Book not available or out of stock.");
            }
    }

    public void selectBookByTitleAndAddToCart(String title){
        if(!authService.isAuthenticated()){
            System.out.println("Unauthorized access. " +
                    "Please log in to select a book and add to cart.");
            return;
        }
        Borrower borrower = getOrCreateBorrower();
            database.getBookInventory().values().stream()
                    .filter(book -> book.getTitle().equalsIgnoreCase(title))
                    .findFirst()
                    .ifPresentOrElse(book -> {
                        if(book.getAvailableQuantity() > 0){
                            borrower.getCart().add(book);
                            System.out.println("Book added to cart: " + book.getTitle());
                        }else {
                            System.out.println("Book not available or out of stock.");
                        }
                    }, () -> System.out.println("Book not found with title: " + title));
    }
    public void viewCart(){
        if(authService.isAuthenticated()){
            Borrower borrower = getOrCreateBorrower();
            if(borrower.getCart().isEmpty()){
                System.out.println("Your cart is empty! Please add books to cart!");
                return;
            }
            System.out.println("Books in your cart:");
            borrower.getCart().forEach(book -> System.out.println("Title: " +
                    book.getTitle() + ", Author: " + book.getAuthor() +
                    ", ISBN: " + book.getIsbn()));
        }else {
            System.out.println("Unauthorized access. " +
                    "Please log in to view your cart.");
        }
    }

    public void checkout(){
        if(!authService.isAuthenticated()){
            System.out.println("Unauthorized access. " +
                    "Please log in to checkout your books.");
            return;
        }
        Borrower borrower = getOrCreateBorrower();
            if(borrower.getCart().isEmpty()){
                System.out.println("Your cart is empty. Please add books to cart before checkout.");
                return;
            }
            borrower.getCart().forEach(book -> {
                book.setAvailableQuantity(book.getAvailableQuantity() - 1);
                System.out.println("Checked out: " + book.getTitle());
            });
            borrower.getCart().clear();
            System.out.println("Checkout complete! Enjoy your books.");
    }

    public void borrowBook(String isbn){
        if(!authService.isAuthenticated()){
            System.out.println("Unauthorized access. " +
                    "Please log in to borrow a book.");
            return;
        }
        Borrower borrower = getOrCreateBorrower();

        if (borrower.getCurrentFine() > borrower.getFineLimit()) {
            System.out.println("Cannot borrow book. Fine exceeds allowed limit.");
            System.out.println("Current Fine: " + borrower.getCurrentFine() +
                    ", Limit: " + borrower.getFineLimit());
            return;
        }

        if (borrower.getBorrowedBooks().size() >= 3) {
            System.out.println("Borrowing limit reached. Return a book to borrow a new one.");
            return;
        }

        Book book = database.getBookInventory().get(isbn);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.getAvailableQuantity() <= 0) {
            System.out.println("Book is out of stock.");
            return;
        }

        boolean alreadyBorrowed = borrower.getBorrowedBooks().stream()
                .anyMatch(b -> b.getIsbn().equalsIgnoreCase(isbn));

        if (alreadyBorrowed) {
            System.out.println("You have already borrowed this book.");
            return;
        }

        if(borrower.getSecurityDeposit()<500){
            System.out.println("A minimum security deposit of 500 rupees must " +
                    "be maintained! Therefore you are not eligible to borrow a book");
            return;
        }
        borrower.getBorrowedBooks().add(book);
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);

        System.out.println("You have successfully borrowed: " + book.getTitle());
    }
    private Borrower getOrCreateBorrower() {
        User user = authService.getLoggedInUser();
        String email = user.getEmail();

        Borrower borrower = database.getBorrowers().get(email);

        if (borrower == null) {
            borrower = new Borrower(user);
            database.getBorrowers().put(email, borrower);
        }

        return borrower;
    }
    public void viewBooks(){};
    public void returnBook(){};
}
