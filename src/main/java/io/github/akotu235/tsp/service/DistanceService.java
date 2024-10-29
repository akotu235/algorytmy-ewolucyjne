package io.github.akotu235.tsp.service;

import io.github.akotu235.tsp.model.geo.Coordinates;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class DistanceService {
    public double getDistance(Coordinates coordinates1, Coordinates coordinates2) {
        try {
            return calculateDistance(coordinates1, coordinates2) / 1000; // Zwraca odległość w km
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Obliczanie odległości między między miastami nie powiodło się.");
        }
    }

    private static double calculateDistance(Coordinates fromCoords, Coordinates toCoords) throws IOException, URISyntaxException {
        String urlStr = String.format(
                "http://router.project-osrm.org/route/v1/driving/%s,%s;%s,%s?overview=false",
                fromCoords.getLon(),
                fromCoords.getLat(),
                toCoords.getLon(),
                toCoords.getLat()
        );

        URI uri = new URI(urlStr);
        URL url = uri.toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        Scanner scanner = new Scanner(conn.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        JSONObject jsonResponse = new JSONObject(response);
        if (jsonResponse.getString("code").equals("Ok")) {
            return jsonResponse.getJSONArray("routes")
                    .getJSONObject(0)
                    .getDouble("distance");
        }
        throw new IllegalArgumentException("Błąd w obliczeniach odległości: " + jsonResponse.getString("code"));
    }
}
