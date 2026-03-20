package librarymanagementsystem.entity;

import librarymanagementsystem.enums.Role;
import librarymanagementsystem.enums.UserType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Borrower extends User{
    private ArrayList<Book> borrowedBooks;
    private Set<Book> cart=new HashSet<>();
    private double securityDeposit=1500;

    public Borrower(String name, String userId, String email, String password, UserType userType, Role role) {
        super(name, userId, email, password, userType, role);
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public Set<Book> getCart() {
        return cart;
    }

    public double getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(double securityDeposit) {
        this.securityDeposit = securityDeposit;
    }
}
