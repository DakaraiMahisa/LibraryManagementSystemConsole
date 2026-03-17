package librarymanagementsystem.service;

import librarymanagementsystem.database.LibraryDatabase;
import librarymanagementsystem.entity.User;
import librarymanagementsystem.enums.Role;

import java.util.Scanner;

public class AuthService {
  private final User user;
  private final LibraryDatabase libraryDatabase;
  private static Integer userIdCounter = 1;
  private User loggedInUser;
  public AuthService(User user, LibraryDatabase libraryDatabase) {
    this.user = user;
    this.libraryDatabase = libraryDatabase;
  }
  public boolean register(){
      Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();
        if(name.isEmpty()) {
            System.out.println("Name cannot be empty. Please try again.");
            return false;
        }
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();
        if(email.isEmpty()) {
            System.out.println("Email cannot be empty. Please try again.");
            return false;
        }
        System.out.println("Enter your password: ");
        String password = scanner.nextLine().trim();
        if(password.isEmpty()||password.length() < 6) {
            System.out.println("Password must be at least 6 characters long. Please try again.");
            return false;
        }

        String userId = "U" + String.format("%04d", userIdCounter++);
        System.out.println("Choose your role (1 for Student, 2 for Admin,3 Staff): ");
        int roleChoice = scanner.nextInt();
        Role role = switch (roleChoice) {
            case 1 -> Role.STUDENT;
            case 2 -> Role.ADMIN;
            case 3 -> Role.STAFF;
            default -> throw new IllegalStateException("Unexpected value: " + roleChoice);
        };
        User newUser = new User(name, userId, email, password, role);
        libraryDatabase.getUserMap().put(email, newUser);
        System.out.println("Registration successful! Your user ID is: " + userId);
    return true;
  }

  public boolean login() {
      Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();
        User user = libraryDatabase.getUserMap().get(email);
        if(user==null) {
            System.out.println("User not found.");
            return false;
        }
        if(user.getPassword().equals(password)) {
            loggedInUser = user;
            System.out.println("Login successful! Welcome, " + user.getName() + "!");
            return true;
        } else {
            System.out.println("Login failed! Please check your email and password.");
            return false;
        }
  }

  public void logout(){
      if(loggedInUser!=null) {
          System.out.println("Goodbye, " + loggedInUser.getName() + "!");
          loggedInUser = null;
      } else {
          System.out.println("No user is currently logged in.");
      }
  }
  public boolean isAuthenticated() {
      return loggedInUser != null;
  }

  public boolean isAdmin() {
      return loggedInUser != null && loggedInUser.getRole() == Role.ADMIN;
  }
  public boolean isStaff() {
      return loggedInUser != null && loggedInUser.getRole() == Role.STAFF;
  }
  public boolean isStudent() {
      return loggedInUser != null && loggedInUser.getRole() == Role.STUDENT;
  }
}
