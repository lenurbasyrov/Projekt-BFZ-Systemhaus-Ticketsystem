/*
package main.java.app;
import java.util.List;

import main.java.model.Admin;
import main.java.model.Benutzer;
import main.java.model.Kunde;
import main.java.model.Ticket;
import main.java.repository.Repository;
import main.java.service.ApiService;
import main.java.service.StorageService;

public class TestBenutzerTicket {
    public static void main(String[] args) {
        // Erstellen eines Admins
        Admin admin = new Admin(1L, "Hans Schmidt", "hans.schmidt@example.com", "adminpass", "IT");
        // Erstellen eines Kunden
        Kunde kunde = new Kunde(2L, "Max Mustermann", "max.mustermann@example.com", "passwort123",
                "Musterstraße 1, 12345 Musterstadt");

        // Erstellen von Repositories
        Repository<Benutzer> benutzerRepo = new Repository<>();
        Repository<Ticket> ticketRepo = new Repository<>();
        // Hinzufügen von Benutzern zum Repository
        benutzerRepo.add(admin);
        benutzerRepo.add(kunde);

        // Anzeigen der Benutzerdaten
        admin.anmelden();
        admin.zeigeDaten();
        admin.rolleAnzeigen();
        admin.abmelden();
        System.out.println();
        kunde.anmelden();
        kunde.zeigeDaten();
        kunde.rolleAnzeigen();
        kunde.abmelden();
        System.out.println();

        // Erstellen eines Tickets
        Ticket ticket = new Ticket(1L, "Problem mit Bestellung", "Die Bestellung wurde nicht geliefert.",
                Ticket.Prioritaet.HOCH, kunde, admin);
        ticketRepo.add(ticket); // Hinzufügen des Tickets zum Repository

        // Anzeigen der Ticket-Informationen
        ticket.ticketInfo();
        System.out.println();

        // Testen der Repository-Klasse
        Long suchId = 1L; // ID des Benutzers, den wir suchen möchten
        Long suchTicketId = 1L; // ID des Tickets, das wir suchen möchten
        Benutzer gefBenutzer = benutzerRepo.findById(suchId);
        Ticket gefTicket = ticketRepo.findById(suchTicketId);

        if (gefBenutzer != null) {
            System.out.println("Gefundener Benutzer: " + gefBenutzer.getName());
        } else {
            System.out.println("Benutzer mit ID " + suchId + " nicht gefunden.");
        }

        if (gefTicket != null) {
            System.out.println("Gefundenes Ticket: " + gefTicket.getTitel());
        } else {
            System.out.println("Ticket mit ID " + suchTicketId + " nicht gefunden.");
        }

        System.out.println();

        // Testen der StorageService Klasse
        StorageService storageService = new StorageService();
        List<Benutzer> benutzerListe = benutzerRepo.getAll();
        List<Ticket> ticketListe = ticketRepo.getAll();

        try {
            // Speichern der Benutzer und Tickets
            storageService.speichern(benutzerListe, "benutzer.dat");
            storageService.speichern(ticketListe, "tickets.dat");

            // Laden der Benutzer und Tickets
            List<Benutzer> geladeneBenutzer = storageService.laden("benutzer.dat");
            List<Ticket> geladeneTickets = storageService.laden("tickets.dat");

            System.out.println("Geladene Benutzer:");
            for (Benutzer b : geladeneBenutzer) {
                System.out.println("- " + b.getName());
            }

            System.out.println("Geladene Tickets:");
            for (Ticket t : geladeneTickets) {
                System.out.println("- " + t.getTitel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();

        // Testen der ApiService Klasse
        ApiService apiService = new ApiService();
        List<Kunde> kunden = apiService.ladeKunden();
        System.out.println("Kunden aus der API:" + kunden.size() + " Kunden gefunden.");
        for (Kunde k : kunden) {
            System.out.println(k.getId() + ": " + k.getName() + " - " + k.getEmail() + " - " + k.getAdresse());
        }

        // Speichern der Kundenliste als JSON in einer Datei
        String jsonKunden = apiService.kundenToJson(kunden);
        String dateiName = "importierte_kunden.json";
        try {
            storageService.speichereJson(jsonKunden, dateiName);
        } catch (Exception e) {
            System.out.println("Fehler beim Speichern der JSON-Daten: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
    */