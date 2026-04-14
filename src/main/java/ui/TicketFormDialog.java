package ui;

import model.Kunde;
import model.Ticket;

import javax.swing.*;
import java.awt.*;
import java.util.List; // java.awt.List alt, für  grafische Komponenten (z.B. Layouts, Frames)

public class TicketFormDialog extends JDialog {

    private JTextField idField;
    private JTextField titelField;
    private JTextField beschreibungField;
    private JComboBox<Ticket.Prioritaet> prioritaetBox;
    private JComboBox<Ticket.Status> statusBox;
    private JComboBox<Kunde> kundeBox;

    private Ticket result;

    public TicketFormDialog(Frame owner, Ticket ticket, List<Kunde> verfuegbareKunden) {
        super(owner, true);
        setTitle(ticket == null ? "Neues Ticket" : "Ticket bearbeiten");
        setSize(450, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        initComponents(ticket, verfuegbareKunden);
    }

    private void initComponents(Ticket ticket, List<Kunde> verfuegbareKunden) {
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        idField = new JTextField();
        titelField = new JTextField();
        beschreibungField = new JTextField();
        prioritaetBox = new JComboBox<>(Ticket.Prioritaet.values());
        statusBox = new JComboBox<>(Ticket.Status.values());

        // Kunden-ComboBox mit verfügbaren Kunden füllen
        kundeBox = new JComboBox<>();
        kundeBox.addItem(null); // Option für "Kein Kunde" hinzufügen
        for (Kunde k : verfuegbareKunden) {
            kundeBox.addItem(k);
        }

        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);

        formPanel.add(new JLabel("Titel:"));
        formPanel.add(titelField);

        formPanel.add(new JLabel("Beschreibung:"));
        formPanel.add(beschreibungField);

        formPanel.add(new JLabel("Priorität:"));
        formPanel.add(prioritaetBox);

        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusBox);

        formPanel.add(new JLabel("Kunde (Autor):"));
        formPanel.add(kundeBox);

        if (ticket != null) {
            idField.setText(String.valueOf(ticket.getId()));
            idField.setEditable(false);

            titelField.setText(ticket.getTitel());
            beschreibungField.setText(ticket.getBeschreibung());
            prioritaetBox.setSelectedItem(ticket.getPrioritaet());
            statusBox.setSelectedItem(ticket.getStatus());
            kundeBox.setSelectedItem(ticket.getAutor());
        }

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Abbrechen");

        okButton.addActionListener(e -> onSave(ticket));
        cancelButton.addActionListener(e -> {
            result = null;
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onSave(Ticket originalTicket) {
        try {
            Long id = Long.parseLong(idField.getText().trim());
            String titel = titelField.getText().trim();
            String beschreibung = beschreibungField.getText().trim();
            Ticket.Prioritaet prioritaet = (Ticket.Prioritaet) prioritaetBox.getSelectedItem();
            Ticket.Status status = (Ticket.Status) statusBox.getSelectedItem();
            Kunde ausgewaehlterKunde = (Kunde) kundeBox.getSelectedItem();

            if (originalTicket == null) {
                result = new Ticket(id, titel, beschreibung, prioritaet, ausgewaehlterKunde, null);
                result.setStatus(status);
            } else {
                result = new Ticket(id, titel, beschreibung, prioritaet, ausgewaehlterKunde,
                        originalTicket.getVerantwortlicherAdmin());
                result.setStatus(status);
            }

            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ungültige Eingabe: " + ex.getMessage(),
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public Ticket showDialog() {
        setVisible(true);
        return result;
    }
}
