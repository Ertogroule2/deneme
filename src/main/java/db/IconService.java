package db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IconService {
    private final MongoDatabase db =
        MongoDBConnection.getDatabase("investedDB");
    private final MongoCollection<Document> icons =
        db.getCollection("icons");

    /** Uploads or replaces the icon for a given stock symbol. */
    public void uploadIcon(String symbol, Path imagePath) throws IOException {
        byte[] bytes = Files.readAllBytes(imagePath);
        Binary data = new Binary(bytes);

        Document filter = new Document("symbol", symbol);
        Document update = new Document("$set", new Document("data", data)
                                                  .append("contentType", "image/png"));
        icons.updateOne(filter, update,
            new com.mongodb.client.model.UpdateOptions().upsert(true));
    }

    /** Returns raw image bytes for a symbol, or null if none stored. */
    public byte[] getIcon(String symbol) {
        Document doc = icons.find(new Document("symbol", symbol)).first();
        if (doc != null && doc.containsKey("data")) {
            return doc.get("data", Binary.class).getData();
        }
        return null;
    }
}
