package db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProfileService {
    // Point at your investedDB database
    private final MongoDatabase db =
        MongoDBConnection.getDatabase("investedDB");
    // And your separate profilepictures collection
    private final MongoCollection<Document> pics =
        db.getCollection("profilepictures");

    /** Uploads (or replaces) the avatar for `username` from a local file. */
    public void uploadAvatar(String username, Path imagePath) throws IOException {
        byte[] bytes = Files.readAllBytes(imagePath);
        // Binary wrapper for MongoDB
        Binary data = new Binary(bytes);

        // Upsert into profilepictures: key on username
        Document filter = new Document("username", username);
        Document update = new Document("$set", new Document("data", data)
                                                  .append("contentType", "image/png"));
        pics.updateOne(filter, update, 
            new com.mongodb.client.model.UpdateOptions().upsert(true));
    }

    /** Retrieves the avatar bytes for `username`, or null if none. */
    public byte[] getAvatar(String username) {
        Document doc = pics.find(new Document("username", username)).first();
        if (doc != null && doc.containsKey("data")) {
            Binary bin = doc.get("data", Binary.class);
            return bin.getData();
        }
        return null;
    }
}
