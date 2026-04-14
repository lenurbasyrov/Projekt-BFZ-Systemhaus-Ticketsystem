package repository;
import org.junit.jupiter.api.BeforeEach; // JUnit 5 Annotation für Setup-Methode
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import exception.TicketNotFoundException;
import model.Ticket;
import repository.Repository;

public class RepositoryTest {
    private Repository<Ticket> ticketRepo;

    @BeforeEach
    public void setUp() {
        ticketRepo = new Repository<>();
    }

    @Test
    public void testFindByIdThrowsException() {
        // Testen der leeren Repository und  suchen nach einem nicht existierenden Ticket
        assertThrows(TicketNotFoundException.class, () -> {
            ticketRepo.findById(999L);
        });
    }
}
