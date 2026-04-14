package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import model.Kunde;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ApiService {
    private final HttpClient httpClient;
    private final Gson gson;

    public ApiService() {
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
        this.gson = new Gson();
    }

    /**
     * Importiert die Kundenliste von der JSONPlaceholder-API
     * 
     * @return List<Kunde> mit Daten aus der API
     */
    public List<Kunde> ladeKunden() {
        try {
            // Sendet eine GET-Anfrage an die API, um die Kundendaten abzurufen
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/users"))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            // Überprüft den Statuscode der Antwort und parst die JSON-Daten in eine Liste
            // von Kunden
            if (response.statusCode() != 200) {
                throw new RuntimeException("HTTP-Fehler: " + response.statusCode());
            }
            return parseKunden(response.body());
        } catch (IOException | InterruptedException e) {
            // InterruptedException behandeln
            Thread.currentThread().interrupt();
            throw new RuntimeException("Verbindungsfehler zur API", e);
        }
    }

    /**
     * Parst eine Liste von Kunden aus einem JSON-String.
     *
     * @param jsonString JSON-String, der die Kundendaten enthält
     * @return Liste von Kunden, die aus dem JSON-String geparst wurden
     */
    public List<Kunde> parseKunden(String jsonString) {
        Type listType = new TypeToken<List<JsonObject>>() {
        }.getType();
        List<JsonObject> jsonObjects = gson.fromJson(jsonString, listType);

        return jsonObjects.stream()
                .map(this::jsonObjectToKunde)
                .collect(Collectors.toList());
    }

    /**
     * Konvertiert eine Kundenliste zurück in einen JSON-String.
     *
     * @param kunden Liste von Kunden
     * @return JSON-String mit Kundendaten
     */
    public String kundenToJson(List<Kunde> kunden) {
        return gson.toJson(kunden);
    }

    /**
     * Konvertiert ein JsonObject in ein Kunde-Objekt.
     *
     * @param jsonObject JsonObject, das die Kundendaten enthält
     * @return Kunde-Objekt, das aus dem JsonObject erstellt wurde
     */
    private Kunde jsonObjectToKunde(JsonObject kundeJson) {
        Long id = kundeJson.get("id").getAsLong();
        String name = kundeJson.get("name").getAsString();
        String email = kundeJson.get("email").getAsString();
        String passwort = "defaultPasswort"; // Da die API kein Passwort liefert, setzen wir ein Standardpasswort

        // Extraktion der verschachtelten Adressdaten
        JsonObject address = kundeJson.getAsJsonObject("address");
        String strasse = address.get("street").getAsString();
        String stadt = address.get("city").getAsString();
        String zipCode = address.get("zipcode").getAsString();
        String adresseFormatiert = strasse + ", " + stadt + " " + zipCode;

        return new Kunde(id, name, email, passwort, adresseFormatiert);
    }
}
