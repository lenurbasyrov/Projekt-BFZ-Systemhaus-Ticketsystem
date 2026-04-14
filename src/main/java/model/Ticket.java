package model;
import java.io.Serializable;
import java.time.LocalDateTime;

import exception.InvalidDataException;

public class Ticket implements Identifiable, Serializable{
    private static final long serialVersionUID = 1L; // Für die Serialisierung
    private final Long id;   // ID ist final, da sie nicht geändert werden soll
    private String titel;
    private String beschreibung;
    public enum Prioritaet {
        NIEDRIG, MITTEL, HOCH
    }
    private Prioritaet prioritaet;
    private LocalDateTime erstellungsDatum;
    public enum Status {
        OFFEN, IN_ARBEIT, GESCHLOSSEN
    }
    private Status status;
    private final Kunde autor;
    private Admin verantwortlicherAdmin;

    // Konstruktor
    public Ticket(Long id, String titel, String beschreibung, Prioritaet prioritaet, Kunde autor, Admin verantwortlicherAdmin) {
        this.id = id;
        if (titel == null || titel.trim().isEmpty()) {
            throw new InvalidDataException("Der Titel darf nicht leer sein.");
        }
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.prioritaet = prioritaet;
        this.erstellungsDatum = LocalDateTime.now();
        this.status = Status.OFFEN;
        this.autor = autor;
        this.verantwortlicherAdmin = verantwortlicherAdmin;
    }

    // Implementierung der getId() Methode aus dem Identifiable Interface
    @Override
    public Long getId() {
        return id;
    }

    // Getter und Setter
    public String getTitel() {
        return titel;
    }
    public String getBeschreibung() {
        return beschreibung;
    }
    public Prioritaet getPrioritaet() {
        return prioritaet;
    }
    public LocalDateTime getErstellungsDatum() {
        return erstellungsDatum;
    }
    public Status getStatus() {
        return status;
    }
    public Kunde getAutor() {
        return autor;
    }
    public Admin getVerantwortlicherAdmin() {
        return verantwortlicherAdmin;
    }

    public void setTitel(String titel) {
        if (titel == null || titel.trim().isEmpty()) {
            throw new InvalidDataException("Der Titel darf nicht leer sein.");
        }
        this.titel = titel;
    }
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    public void setPrioritaet(Prioritaet neuePrioritaet) {
        this.prioritaet = neuePrioritaet;   // Aktualisieren der Priorität
    }
    public void setErstellungsDatum(LocalDateTime erstellungsDatum) {
        this.erstellungsDatum = erstellungsDatum;
    }
    public void setStatus(Status neueStatus) {
        this.status = neueStatus;   // Aktualisieren des Status
    }
    public void setVerantwortlicherAdmin(Admin admin) {
        this.verantwortlicherAdmin = admin;   // Aktualisieren des verantwortlichen Admins
    }

    // Methode zum Anzeigen der Ticket-Informationen
    public void ticketInfo() {
        System.out.println("Ticket ID: " + id);
        System.out.println("Titel: " + titel);
        System.out.println("Beschreibung: " + beschreibung);
        System.out.println("Priorität: " + prioritaet);
        System.out.println("Erstellt am: " + erstellungsDatum);
        System.out.println("Status: " + status);
        System.out.println("Autor: " + (autor != null ? autor.getName() : "kein Autor"));   // Überprüfen, ob der Autor null ist, bevor der Name angezeigt wird
        System.out.println("Verantwortlicher Admin: " + (verantwortlicherAdmin != null ? verantwortlicherAdmin.getName() : "nicht zugewiesen"));
    }
}
