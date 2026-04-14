package model;

import org.junit.jupiter.api.BeforeEach; // JUnit 5 Annotation für Setup-Methode
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import exception.InvalidDataException;
import model.Admin;
import model.Kunde;
import model.Ticket;

public class TicketTest {
    private Admin admin;
    private Kunde kunde;

    @BeforeEach
    public void setUp() {
        admin = new Admin(1L, "Hans Schmidt", "hans.schmidt@example.com", "adminpass", "IT");
        kunde = new Kunde(2L, "Max Mustermann", "max.mustermann@example.com", "passwort123", "Musterstraße 1");
    }

    @Test
    public void testTicketCreation() {
        // Erstellen eines Tickets
        Ticket ticket = new Ticket(1L, "Problem mit Bestellung", "Die Bestellung wurde nicht geliefert.", Ticket.Prioritaet.HOCH, kunde, admin);

        // Überprüfen der Ticket-Informationen
        assertEquals(Long.valueOf(1L), ticket.getId());
        assertEquals("Problem mit Bestellung", ticket.getTitel());
        assertEquals("Die Bestellung wurde nicht geliefert.", ticket.getBeschreibung());
        assertEquals(Ticket.Prioritaet.HOCH, ticket.getPrioritaet());
        assertEquals(Ticket.Status.OFFEN, ticket.getStatus());
        assertEquals(kunde, ticket.getAutor());
        assertEquals(admin, ticket.getVerantwortlicherAdmin());
    }

    @Test
    public void testStatusUpdate() {
         // Erstellen eines Tickets
        Ticket ticket = new Ticket(1L, "Problem mit Bestellung", "Die Bestellung wurde nicht geliefert.", Ticket.Prioritaet.HOCH, kunde, admin);
        // Testen der Aktualisierung des Status
        ticket.setStatus(Ticket.Status.IN_ARBEIT);
        assertEquals(Ticket.Status.IN_ARBEIT, ticket.getStatus());
    }

    @Test
    public void testPrioritaetUpdate() {
        // Erstellen eines Tickets
        Ticket ticket = new Ticket(1L, "Problem mit Bestellung", "Die Bestellung wurde nicht geliefert.", Ticket.Prioritaet.HOCH, kunde, admin);
        // Testen der Aktualisierung der Priorität
        ticket.setPrioritaet(Ticket.Prioritaet.NIEDRIG);
        assertEquals(Ticket.Prioritaet.NIEDRIG, ticket.getPrioritaet());
    }

    @Test
    public void testInvalidTitleThrowsException() {
        // Testen der Erstellung eines Tickets mit ungültigem Titel
        assertThrows(InvalidDataException.class, () -> {
            new Ticket(1L, "", "Die Bestellung wurde nicht geliefert.", Ticket.Prioritaet.HOCH, kunde, admin);
        });
    }

    @Test
    public void testSetInvalidTitleThrowsException() {
        // Erstellen eines Tickets
        Ticket ticket = new Ticket(1L, "Problem mit Bestellung", "Die Bestellung wurde nicht geliefert.", Ticket.Prioritaet.HOCH, kunde, admin);
        // Testen der Aktualisierung des Titels mit ungültigem Wert
        assertThrows(InvalidDataException.class, () -> {
            ticket.setTitel("");
        });
    }
}
