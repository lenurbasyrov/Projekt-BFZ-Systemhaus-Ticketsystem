package model;
public class Admin extends Benutzer {
    private String bereich;
    // Konstruktor
    public Admin(Long id, String name, String email, String passwort, String bereich) {
        super(id, name, email, passwort);
        this.bereich = bereich;
    }
    // Getter und Setter
    public String getBereich() {
        return bereich;
    }
    public void setBereich(String bereich) {
        this.bereich = bereich;
    }
    // Methode zum Verwalten von Benutzern
    public void verwalteBenutzer() {
        System.out.println("Admin " + getName() + " verwaltet Benutzer.");
    }

    // Überschreiben der Methode zur Anzeige der Rolle
    @Override
    public void rolleAnzeigen() {
        System.out.println("Rolle: Admin");
    }
    // Überschreiben der Methode zum Anzeigen der Benutzerdaten
    @Override
    public void zeigeDaten() {
        super.zeigeDaten();
        System.out.println("Bereich: " + bereich);
    }
}
