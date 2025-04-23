package db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import model.PortfolioItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PortfolioService {
    private final MongoCollection<Document> col;

    public PortfolioService() {
        MongoDatabase db = MongoDBConnection.getDatabase("investedDB");
        col = db.getCollection("portfolios");
    }

    /** Called on registration: make one portfolio seeded with startingCash, return its ID. */
    public String createInitialPortfolio(String username, double startingCash) {
        String id = UUID.randomUUID().toString();
        Document cashItem = new Document("symbol", "CASH")
                                .append("quantity", startingCash);
        Document doc = new Document("id", id)
            .append("user", username)
            .append("items", List.of(cashItem));
        col.insertOne(doc);
        return id;
    }

    /** Fetch exactly that one portfolio’s items. */
    public List<PortfolioItem> fetchPortfolioItems(String portfolioId) {
        Document doc = col.find(new Document("id", portfolioId)).first();
        if (doc == null) return List.of();
        List<Document> arr = doc.getList("items", Document.class);
        List<PortfolioItem> out = new ArrayList<>();
        for (Document i : arr) {
            out.add(new PortfolioItem(
                i.getString("symbol"),
                i.getDouble("quantity")
            ));
        }
        return out;
    }

    /**
     * Buy or sell an item: remove old, then re‑push if qty>0.
     * Calling with symbol="CASH" and negative qty will adjust cash.
     */
    public void upsertItem(String portfolioId, String symbol, double quantity) {
        Document filter = new Document("id", portfolioId);
        // remove any existing entry
        col.updateOne(filter, new Document("$pull",
            new Document("items", new Document("symbol", symbol))
        ));
        // if quantity>0 push the new one
        if (quantity > 0) {
            Document newItem = new Document("symbol", symbol)
                                   .append("quantity", quantity);
            col.updateOne(filter, new Document("$push",
                new Document("items", newItem)
            ));
        }
    }
}
