package ui;

import model.Kunde;
import repository.Repository;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
    private final Repository<Kunde> kundeRepo;
    private List<Kunde> kunden;

    private final String[] columnNames = { "ID", "Name", "Email", "Adresse / Stadt" };

    public UserTableModel(Repository<Kunde> kundeRepo) {
        this.kundeRepo = kundeRepo;
        this.kunden = kundeRepo.getAll();
    }

    @Override
    public int getRowCount() {
        return kunden.size();
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
        Kunde kunde = kunden.get(rowIndex);
        // Rückgabe der entsprechenden Kundendaten basierend auf der Spalte
        return switch (columnIndex) {
            case 0 -> kunde.getId();
            case 1 -> kunde.getName();
            case 2 -> kunde.getEmail();
            case 3 -> (kunde.getAdresse() != null) ? kunde.getAdresse() : "Keine Adresse";
            default -> null;
        };
    }

    // Methode zum Abrufen eines Kunden basierend auf der Zeilennummer
    public Kunde getKundeAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < kunden.size()) {
            return kunden.get(rowIndex);
        }
        return null;
    }

    public void refresh() {
        this.kunden = kundeRepo.getAll();
        fireTableDataChanged(); // Benachrichtigt die Tabelle, dass sich die Daten geändert haben
    }
}