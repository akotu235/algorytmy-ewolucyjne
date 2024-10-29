package io.github.akotu235.tsp.service;

import io.github.akotu235.tsp.model.geo.Coordinates;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class LocationService {
    public static Coordinates getCoordinates(String city) throws IOException, URISyntaxException {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        URI uri = new URI("https://nominatim.openstreetmap.org/search?format=json&limit=1&q=" + encodedCity);
        URL url = uri.toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");

        Scanner scanner = new Scanner(conn.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        JSONArray jsonArray = new JSONArray(response);
        if (!jsonArray.isEmpty()) {
            JSONObject location = jsonArray.getJSONObject(0);
            double lat = location.getDouble("lat");
            double lon = location.getDouble("lon");
            return new Coordinates(lat, lon);
        }

        throw new IllegalArgumentException("Nie znaleziono współrzędnych dla miasta: " + city);
    }
}
