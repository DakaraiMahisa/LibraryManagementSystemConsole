package librarymanagementsystem.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Borrower {

    private final User user;

    private List<Book> borrowedBooks = new ArrayList<>();
    private Set<Book> cart = new HashSet<>();

    private double securityDeposit = 1500;
    private double fineLimit = 500;
    private double currentFine = 0;

    public Borrower(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public List<Book> getBorrowedBooks() {
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

    public double getFineLimit() {
        return fineLimit;
    }

    public void setFineLimit(double fineLimit) {
        this.fineLimit = fineLimit;
    }

    public double getCurrentFine() {
        return currentFine;
    }

    public void setCurrentFine(double currentFine) {
        this.currentFine = currentFine;
    }
}
