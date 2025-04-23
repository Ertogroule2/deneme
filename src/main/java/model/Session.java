package model;

import model.User;

public class Session {
    private static Session instance = new Session();
    private User currentUser;

    private Session() {}

    public static Session getInstance() {
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
