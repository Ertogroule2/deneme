package model;

public class PortfolioItem {
    private String symbol;
    private double quantity;

    public PortfolioItem(String symbol, double quantity) {
        this.symbol   = symbol;
        this.quantity = quantity;
    }

    public String getSymbol()           { return symbol; }
    public void setSymbol(String symbol){ this.symbol = symbol; }

    public double getQuantity()                { return quantity; }
    public void   setQuantity(double quantity) { this.quantity = quantity; }
}
