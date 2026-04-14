package service;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class StorageService {
    /**
     * Speichert eine Liste von Objekten in eine Datei durch Serialisierung
     *
     * @param list      Liste der zu speichernden Objekte
     * @param dateiPfad Pfad zur Datei
     * @throws IOException bei Schreibfehlern
     */
    public <T> void speichern(List<T> list, String dateiPfad) throws IOException {
        // Extrahiert den Verzeichnisnamen aus dem Pfad (z.B. "storage")
        File file = new File(dateiPfad);
        File parentDir = file.getParentFile();

        // Wenn ein Verzeichnis im Pfad angegeben ist und es nicht existiert, erstelle es
        if (parentDir != null && !parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("Verzeichnis erstellt: " + parentDir.getName());
            }
        }
        // Serialisierung
        try (FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(list);
            System.out.println("Daten wurden erfolgreich in " + dateiPfad + " gespeichert.");
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Lädt eine Liste von Objekten aus einer Datei
     * 
     * @param dateiPfad Pfad zur Datei
     * @return Liste von Objekten oder leere ArrayList, wenn die Datei nicht
     *         gefunden wird
     * @throws IOException             bei Lesefehlern
     * @throws ClassNotFoundException, wenn die Klasse der Objekte nicht gefunden
     *                                 wird
     */
    public <T> List<T> laden(String dateiPfad) throws IOException, ClassNotFoundException {
        File file = new File(dateiPfad);

        if (!file.exists()) {
            System.out.println("Die Datei " + dateiPfad + " existiert nicht. Es wird eine leere Liste zurückgegeben.");
            return new ArrayList<>(); // Rückgabe einer leeren Liste, wenn die Datei nicht existiert
        }

        try (FileInputStream fis = new FileInputStream(dateiPfad);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            List<T> list = (List<T>) ois.readObject();
            System.out.println("Daten wurden erfolgreich aus " + dateiPfad + " geladen.");
            return list;
        }
    }

    // Methode zum Speichern von JSON-Daten in eine Datei
    /**
     * Speichert einen JSON-String in eine Datei
     * 
     * @param jsonString
     * @param dateiPfad
     * @throws IOException
     */
    public void speichereJson(String jsonString, String dateiPfad) throws IOException {
        // // Verwendet try-with-resources, um den FileWriter automatisch zu schließen
        try (FileWriter writer = new FileWriter(dateiPfad, StandardCharsets.UTF_8)) {
            writer.write(jsonString);
            System.out.println("JSON-Daten erfolgreich gespeichert in: " + dateiPfad);
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern der JSON-Daten: " + e.getMessage());
            throw e; // weiterwerfen der Ausnahme, damit sie im Aufrufer behandelt werden kann
        }

    }
}
