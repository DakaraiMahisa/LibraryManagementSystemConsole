package librarymanagementsystem.config;

import librarymanagementsystem.database.LibraryDatabase;
import librarymanagementsystem.service.AdminService;
import librarymanagementsystem.service.AuthService;
import librarymanagementsystem.service.BorrowerService;

import java.util.Scanner;

public class Menu {

    private final AuthService authService;
    private final AdminService adminService;
    private final BorrowerService borrowerService;
    public Menu(AuthService authService,
                AdminService adminService,
                BorrowerService borrowerService) {
        this.authService = authService;
        this.adminService = adminService;
        this.borrowerService = borrowerService;
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
        System.out.println("4. Search book");
        System.out.println("5. Add to cart");
        System.out.println("6. View cart");
        System.out.println("7. Checkout your cart");
        System.out.println("8. Logout");
    }

    public static void displayAdminMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1. Add Book");
        System.out.println("2. Add Admin");
        System.out.println("3. View Borrowers");
        System.out.println("4. search for a book");
        System.out.println("5. Manage Borrowers");
        System.out.println("6. Logout");
    }

    public void start() {
        Scanner scan =  new Scanner(System.in);
        boolean running = true;
            while (running) {
                if(!authService.isAuthenticated()){
                displayMainMenu();
                int choice =  scan.nextInt();
                    scan.nextLine();
                switch(choice){
                    case 1:
                         authService.register();
                         break;
                    case 2:
                         authService.login();
                         break;
                    case 3:
                         running=false;
                         break;
                    default:
                        System.out.println("Invalid choice");
                }
                }else if(authService.isAdmin()){
                     displayAdminMenu();
                     int choice = scan.nextInt();
                    scan.nextLine();
                     switch(choice){
                         case 1:
                             System.out.println("Enter the book title:");
                             String title =scan.nextLine().trim();
                             System.out.println("Enter the book author:");
                             String author = scan.nextLine().trim();
                             System.out.println("Enter the book isbn:");
                             String isbn = scan.next().trim();
                             System.out.println("Enter the book quantity:");
                             int quantity = scan.nextInt();
                             adminService.addBookOrModifyBookDetails(title,author,isbn,quantity);
                             break;
                         case 2:
                             System.out.println("Enter the email of the person to be assigned admin role");
                             String email = scan.next().trim();
                             adminService.addOtherAdmins(email);
                             break;
                         case 3:
                             adminService.viewBorrowers();
                             break;
                         case 4:
                             System.out.println("Enter the book isbn");
                             String isbn1 = scan.next().trim();
                             adminService.searchBooksByISBN(isbn1);
                             break;
                         case 5:
                             System.out.println("Enter the borrower email:");
                             String borrowerEmail = scan.next();
                             System.out.println("Enter the new limit:");
                             double newLimit = scan.nextDouble();
                             adminService.manageBorrowers(borrowerEmail,newLimit);
                             break;
                         case 6:
                              authService.logout();
                              break;
                         default:
                             System.out.println("Invalid choice");
                     }
                }else{
                    displayUserMenu();
                    int choice = scan.nextInt();
                    scan.nextLine();
                    switch(choice){
                        case 1:
                             borrowerService.viewListOfBooksAvailable();
                             break;
                        case 2:
                            System.out.println("Enter the book isbn: ");
                            String isbn = scan.next().trim();
                            borrowerService.borrowBook(isbn);
                            break;
                        case 3:
                            borrowerService.returnBook();
                            break;
                        case 5:
                            System.out.println("Enter book title");
                            String title = scan.nextLine().trim();
                            borrowerService.selectBookByTitleAndAddToCart(title);
                             break;
                        case 6:
                            borrowerService.viewCart();
                            break;
                        case 7:
                             borrowerService.checkout();
                             break;
                        case 8:
                            authService.logout();
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                }
            }
    }
}
