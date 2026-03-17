package librarymanagementsystem.entity;

import librarymanagementsystem.enums.Role;
import librarymanagementsystem.enums.UserType;

public class User {
    private String name;
    private String userId;
    private String email;
    private String password;
    private UserType userType;
    private Role role;

    public User(String name, String userId, String email, String password, UserType userType,Role role) {
        this.name = name;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
