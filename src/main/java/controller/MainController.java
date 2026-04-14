package controller;

import model.Kunde;
import model.Ticket;
import repository.Repository;
import service.ApiService;
import service.StorageService;
import ui.MainFrame;
import ui.TicketFormDialog;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class MainController {
    private final MainFrame view;
    private final Repository<Ticket> ticketRepo;
    private final Repository<Kunde> kundeRepo;
    private final StorageService storageService;
    private final ApiService apiService;

    public MainController(MainFrame view, Repository<Ticket> ticketRepo, Repository<Kunde> kundeRepo,
            StorageService storageService, ApiService apiService) {
        this.view = view;
        this.ticketRepo = ticketRepo;
        this.kundeRepo = kundeRepo;
        this.storageService = storageService;
        this.apiService = apiService;

        // Ereignis-Listener für die UI-Elemente initialisieren
        initListeners();
    }

    // Alle Listener für die UI-Elemente hier definieren
    private void initListeners() {
        // Logik für die Menü-Items
        view.getSpeichernMenuItem().addActionListener(e -> saveAllData());
        view.getImportMenuItem().addActionListener(e -> importKundenFromApi());
        view.getBeendenMenuItem().addActionListener(e -> beenden());

        // Logik für die Ticket-Buttons
        view.getNeuButton().addActionListener(e -> createTicket());
        view.getBearbeitenButton().addActionListener(e -> editTicket());
        view.getLoeschenButton().addActionListener(e -> deleteTicket());

        // Logik für die Benutzerverwaltung
        view.getUserImportButton().addActionListener(e -> importKundenFromApi());
        view.getUserDeleteButton().addActionListener(e -> deleteKunde());

    }

    // === ALLGEMEINE FUNKTIONEN ===
    private void saveAllData() {
        try {
            storageService.speichern(ticketRepo.getAll(), "storage/tickets.dat");
            storageService.speichern(kundeRepo.getAll(), "storage/kunden.dat");
            JOptionPane.showMessageDialog(view, "Alle Daten wurden in 'storage/' gespeichert.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Fehler beim Speichern: " + e.getMessage(), "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void beenden() {
        int confirm = JOptionPane.showConfirmDialog(view, "Möchten Sie das Programm beenden?", "Beenden",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // === BENUTZERVERWALTUNG (API) ===
    private void importKundenFromApi() {
        try {
            List<Kunde> kunden = apiService.ladeKunden();
            int count = 0;
            for (Kunde k : kunden) {
                // Einfachste Logik: Wenn Kunde mit dieser ID nicht existiert, hinzufügen
                try {
                    kundeRepo.findById(k.getId());
                } catch (Exception e) {
                    kundeRepo.add(k);
                    count++;
                }
            }
            view.getUserTableModel().refresh();
            JOptionPane.showMessageDialog(view, count + " neue Kunden importiert.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "API Fehler: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteKunde() {
        Kunde selectedKunde = view.getSelectedKunde();
        if (selectedKunde != null) {
            int confirm = JOptionPane.showConfirmDialog(view, "Kunde wirklich löschen?", "Löschen",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                kundeRepo.remove(selectedKunde);
                view.getUserTableModel().refresh();
            }
        }
    }

    // === TICKETVERWALTUNG ===
    private void createTicket() {
        TicketFormDialog dialog = new TicketFormDialog(view, null, kundeRepo.getAll());
        Ticket newTicket = dialog.showDialog();

        if (newTicket != null) {
            ticketRepo.add(newTicket);
            view.getTicketTableModel().refresh();
        }
    }

    private void editTicket() {
        Ticket selectedTicket = view.getSelectedTicket();
        if (selectedTicket == null) {
            JOptionPane.showMessageDialog(view, "Bitte wählen Sie zuerst ein Ticket aus.",
                    "Hinweis", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TicketFormDialog dialog = new TicketFormDialog(view, selectedTicket, kundeRepo.getAll());
        Ticket updatedTicket = dialog.showDialog();

        if (updatedTicket != null) {
            ticketRepo.update(updatedTicket);
            view.getTicketTableModel().refresh();
        }
    }

    private void deleteTicket() {
        Ticket selectedTicket = view.getSelectedTicket();

        if (selectedTicket == null) {
            JOptionPane.showMessageDialog(view, "Bitte wählen Sie zuerst ein Ticket aus.",
                    "Hinweis", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Möchten Sie das ausgewählte Ticket wirklich löschen?",
                "Löschen bestätigen", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            ticketRepo.remove(selectedTicket);
            view.getTicketTableModel().refresh();
        }
    }

    // private void saveTickets() {
    // try {
    // storageService.speichern(ticketRepo.getAll(), "storage/tickets.dat");
    // JOptionPane.showMessageDialog(view, "Tickets wurden erfolgreich
    // gespeichert!",
    // "Speichern erfolgreich", JOptionPane.INFORMATION_MESSAGE);
    // } catch (Exception e) {
    // JOptionPane.showMessageDialog(view, "Fehler beim Speichern!",
    // "Fehler", JOptionPane.ERROR_MESSAGE);
    // }
    // }
}
