package ui;

import model.Ticket;
import model.Kunde;
import repository.Repository;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    // Repositories (Datenhaltung)
    private final Repository<Ticket> ticketRepo;
    private final Repository<Kunde> kundeRepo;
    // Tabellenmodelle (Brücken zwischen Daten und Visualisierung)
    private TicketTableModel ticketTableModel;
    private UserTableModel userTableModel;

    // Tabellen für Tickets und Benutzerverwaltung
    private JTable ticketTable;
    private JTable userTable;

    // Komponenten menü
    private JMenuItem importMenuItem;
    private JMenuItem speichernMenuItem;
    private JMenuItem beendenMenuItem;

    // Knöpfe zur Verwaltung von Tickets
    private JButton neuButton;
    private JButton bearbeitenButton;
    private JButton loeschenButton;

    // Knöpfe zur Verwaltung von Benutzern
    private JButton userImportButton;
    private JButton userDeleteButton;

    public MainFrame(Repository<Ticket> ticketRepo, Repository<Kunde> kundeRepo) {
        this.ticketRepo = ticketRepo;
        this.kundeRepo = kundeRepo;

        setTitle("BFZ-Systemhaus Manager Pro");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialisierung der Modelle des Daten für die Tabellen
        this.ticketTableModel = new TicketTableModel(this.ticketRepo);
        this.userTableModel = new UserTableModel(this.kundeRepo); // Model mit Repository verbinden

        // Initialisierung der Tabellen
        this.ticketTable = new JTable(ticketTableModel);
        this.userTable = new JTable(userTableModel); // Tabelle mit Model verbinden

        // Erstellung interface
        initMenuBar();
        initComponents();
    }

    // Initialisierung der Menüleiste
    private void initComponents() {
        // Erstellung der TabbedPane für die beiden Hauptbereiche: Tickets und
        // Benutzerverwaltung
        JTabbedPane tabbedPane = new JTabbedPane();

        // Hinzufügen Tabs für Tickets und Benutzerverwaltung
        tabbedPane.addTab("Tickets", createTicketPanel());
        tabbedPane.addTab("Benutzer (Kunden)", createUserPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    // Panel für die Ticketverwaltung
    private JPanel createTicketPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel mit den Buttons (oben)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        neuButton = new JButton("Neu");
        bearbeitenButton = new JButton("Bearbeiten");
        loeschenButton = new JButton("Löschen");

        buttonPanel.add(neuButton);
        buttonPanel.add(bearbeitenButton);
        buttonPanel.add(loeschenButton);

        // Tabelle (zentral)
        ticketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ticketTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(ticketTable);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Panel für die Benutzerverwaltung
    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel mit den Buttons (oben)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userImportButton = new JButton("API Import (JSON)");
        userDeleteButton = new JButton("Benutzer löschen");

        buttonPanel.add(userImportButton);
        buttonPanel.add(userDeleteButton);

        // Tabelle für Benutzer (zentral)
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(userTable);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Initialisierung der Menüleiste
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu dateiMenu = new JMenu("Datei");

        importMenuItem = new JMenuItem("Daten Importieren");
        speichernMenuItem = new JMenuItem("Alles Speichern");
        beendenMenuItem = new JMenuItem("Beenden");

        dateiMenu.add(importMenuItem);
        dateiMenu.add(speichernMenuItem);
        dateiMenu.addSeparator();
        dateiMenu.add(beendenMenuItem);

        menuBar.add(dateiMenu);
        setJMenuBar(menuBar);
    }

    // Methoden für die Interaktion mit dem Controller
    public void refreshTable() {
        ticketTableModel.refresh();
        userTableModel.refresh();
    }

    public Ticket getSelectedTicket() {
        int selectedRow = ticketTable.getSelectedRow();
        if (selectedRow == -1) return null;
        int modelRow = ticketTable.convertRowIndexToModel(selectedRow);
        return ticketTableModel.getTicketAt(modelRow);
    }

    public Kunde getSelectedKunde() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) return null;
        // Hier kann in UserTableModel eine Methode hinzugefügt werden, die getTicketAt ähnelt
        return kundeRepo.getAll().get(userTable.convertRowIndexToModel(selectedRow));
    }

    // Getters für Kontroller
    public TicketTableModel getTicketTableModel() { return ticketTableModel; }
    public UserTableModel getUserTableModel() { return userTableModel; }

    public JMenuItem getImportMenuItem() { return importMenuItem; }
    public JMenuItem getSpeichernMenuItem() { return speichernMenuItem; }
    public JMenuItem getBeendenMenuItem() { return beendenMenuItem; }

    public JButton getNeuButton() { return neuButton; }
    public JButton getBearbeitenButton() { return bearbeitenButton; }
    public JButton getLoeschenButton() { return loeschenButton; }

    public JButton getUserImportButton() { return userImportButton; }
    public JButton getUserDeleteButton() { return userDeleteButton; }

}
