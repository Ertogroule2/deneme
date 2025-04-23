package model;

public class User {
    private String username;
    private String password;
    private String portfolioId;    // ← ID of their only portfolio

    public User(String username, String password, String portfolioId) {
        this.username    = username;
        this.password    = password;
        this.portfolioId = portfolioId;
    }

    public String getUsername()     { return username; }
    public void   setUsername(String u) { this.username = u; }

    public String getPassword()     { return password; }
    public void   setPassword(String p) { this.password = p; }

    public String getPortfolioId()      { return portfolioId; }
    public void   setPortfolioId(String id) { this.portfolioId = id; }

    @Override
    public String toString() {
        return "User[" + username + "]";
    }
}
