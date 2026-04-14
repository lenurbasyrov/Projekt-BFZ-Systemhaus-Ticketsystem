package app;

import controller.MainController;
import model.Kunde;
import model.Ticket;
import repository.Repository;
import service.ApiService;
import service.StorageService;
import ui.MainFrame;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialisierung der Repositories (Datenhaltung)
        Repository<Ticket> ticketRepo = new Repository<>();
        Repository<Kunde> kundeRepo = new Repository<>();

        // Initialisierung der Services (Logik für API und Speicherung)
        StorageService storageService = new StorageService();
        ApiService apiService = new ApiService();

        // Alte Daten beim Start laden, falls vorhanden
        try {
            List<Ticket> geladeneTickets = storageService.laden("storage/tickets.dat");
            geladeneTickets.forEach(ticketRepo::add);

            // Auch Kunden laden, falls vorhanden
            List<Kunde> geladeneKunden = storageService.laden("storage/kunden.dat");
            geladeneKunden.forEach(kundeRepo::add);

            System.out.println("Daten erfolgreich geladen.");
        } catch (Exception e) {
            System.out.println("Keine alten Daten gefunden. Starte neu.");
        }

        // UI starten
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(ticketRepo, kundeRepo);
            // Der Controller verbindet alles miteinander
            new MainController(mainFrame, ticketRepo, kundeRepo, storageService, apiService);

            mainFrame.setVisible(true);
        });
    }
}
