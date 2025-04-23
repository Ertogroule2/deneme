package service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BinanceService {
    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * Returns the latest price for the given symbol, e.g. "BTCUSDT" → 29318.34
     */
    public double fetchPrice(String symbol) throws IOException, InterruptedException {
        String url = "https://data-api.binance.vision/api/v3/ticker/price?symbol=" 
                     + symbol.toUpperCase();
        HttpRequest req = HttpRequest.newBuilder()
                               .uri(URI.create(url))
                               .GET()
                               .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        // response: {"symbol":"BTCUSDT","price":"29318.34000000"}
        String body = resp.body();
        int i = body.indexOf("\"price\":\"");
        String p = body.substring(i + 9, body.indexOf("\"", i + 9));
        return Double.parseDouble(p);
    }
}
