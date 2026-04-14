package ui;

import model.Ticket;
import repository.Repository;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TicketTableModel extends AbstractTableModel {
    private final Repository<Ticket> ticketRepository;
    private List<Ticket> tickets;
    // Spaltennamen für die Tabelle
    private final String[] columnNames = {
            "ID", "Titel", "Beschreibung", "Priorität", "Status", "Autor", "Admin"
    };

    public TicketTableModel(Repository<Ticket> ticketRepository) {
        this.ticketRepository = ticketRepository;
        this.tickets = ticketRepository.getAll();
    }

    @Override
    public int getRowCount() {
        return tickets.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ticket ticket = tickets.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return ticket.getId();
            case 1:
                return ticket.getTitel();
            case 2:
                return ticket.getBeschreibung();
            case 3:
                return ticket.getPrioritaet();
            case 4:
                return ticket.getStatus();
            case 5:
                return ticket.getAutor() != null ? ticket.getAutor().getName() : "-";
            case 6:
                return ticket.getVerantwortlicherAdmin() != null ? ticket.getVerantwortlicherAdmin().getName() : "-";
            default:
                return "";
        }
    }

    public Ticket getTicketAt(int rowIndex) {
        return tickets.get(rowIndex);
    }

    public void refresh() {
        this.tickets = ticketRepository.getAll();
        fireTableDataChanged();
    }
}
