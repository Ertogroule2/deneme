package model;

import java.util.List;
import java.util.UUID;

public class Portfolio {
    private String id;
    private String username;
    private List<PortfolioItem> items;

    public Portfolio(String username, List<PortfolioItem> items) {
        this.id       = UUID.randomUUID().toString();
        this.username = username;
        this.items    = items;
    }

    public String getId()            { return id; }
    public void setId(String u) { this.id = u;}
    public String getUsername()      { return username; }
    public void setUsername(String u){ this.username = u; }
    public List<PortfolioItem> getItems()             { return items; }
    public void setItems(List<PortfolioItem> items)   { this.items = items; }
}
