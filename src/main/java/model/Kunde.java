package model;
public class Kunde extends Benutzer {
    private String adresse;

    // Konstruktor
    public Kunde(Long id, String name, String email, String passwort, String adresse) {
        super(id, name, email, passwort);
        this.adresse = adresse;
    }

    // Getter und Setter
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    // Methode zum Bestellen von Produkten
    public void bestelleProdukt() {
        System.out.println("Kunde " + getName() + " bestellt ein Produkt.");
    }

    // Überschreiben der Methode zur Anzeige der Rolle
    @Override
    public void rolleAnzeigen() {
        System.out.println("Rolle: Kunde");
    }

    // Überschreiben der Methode zum Anzeigen der Benutzerdaten
    @Override
    public void zeigeDaten() {
        super.zeigeDaten();
        System.out.println("Adresse: " + adresse);
    }

    @Override
    public String toString() {
        return this.getName(); // Damit der Kundenname in der ComboBox angezeigt wird
    }
}
