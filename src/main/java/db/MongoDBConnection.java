package db;

import com.mongodb.client.*;

public class MongoDBConnection {
    private static final String URI =
    "mongodb://6bmd55:HyPZgO18n9RxZp0B@ac-yo5hryx-shard-00-00.ovgp8js.mongodb.net:27017," +
    "ac-yo5hryx-shard-00-01.ovgp8js.mongodb.net:27017," +
    "ac-yo5hryx-shard-00-02.ovgp8js.mongodb.net:27017/" +
    "?replicaSet=atlas-nbsp7o-shard-0&ssl=true&authSource=admin&retryWrites=true&w=majority&appName=Cluster0";
    private static MongoClient mongoClient = MongoClients.create(URI);

    public static MongoDatabase getDatabase(String dbName) {
        return mongoClient.getDatabase(dbName);
    }
}
