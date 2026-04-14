package model;
import java.io.Serializable;

public abstract class Benutzer implements Identifiable, Serializable {
    private static final long serialVersionUID = 1L; // Für die Serialisierung
    private Long id;
    private String name;
    private String email;
    private String passwort;
    // Konstruktor
    public Benutzer(Long id, String name, String email, String passwort) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwort = passwort;
    }

    // Implementierung der getId() Methode aus dem Identifiable Interface
    @Override
    public Long getId() {
        return id;
    }
    // Getter und Setter
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPasswort() {
        return passwort;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
    // Methode zum Anmelden
    public void anmelden() {
        System.out.println(name + " is angemeldet.");
    }
    // Methode zum Abmelden
    public void abmelden() {
        System.out.println(name + " is abgemeldet.");
    }
    // Methode zum Anzeigen der Benutzerdaten
    public void zeigeDaten() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
    }
    // Abstrakte Methode zur Anzeige der Rolle
    public abstract void rolleAnzeigen();
}
