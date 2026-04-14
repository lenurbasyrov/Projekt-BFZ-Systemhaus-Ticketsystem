# BFZ-Systemhaus Ticketmanager

Java-Desktop-Anwendung zur Verwaltung von IT-Helpdesk-Tickets für das BFZ-Systemhaus. Das Projekt soll ein objektorientiertes System ohne Datenbank bereitstellen, das Tickets erstellt, verwaltet, Benutzern zuordnet und den Bearbeitungsstatus nachvollziehbar macht.

## Projektidee

Das BFZ-Systemhaus benötigt ein internes Tool, um IT-Anfragen effizient zu verwalten. Die Anwendung soll Tickets erstellen, Mitarbeitern zuweisen, den Status verfolgen, Daten lokal speichern und Dummy-Kundendaten über eine externe Schnittstelle importieren.

Der vorgegebene Projektumfang beträgt 64 Stunden bzw. zwei Wochen. Als Ziel ist eine objektorientierte Desktop-Anwendung in Java definiert.

## Funktionsumfang

Die Anwendung basiert auf einer abstrakten Basisklasse `Benutzer` mit den Unterklassen `Admin` und `Kunde`. Zusätzlich gibt es eine Klasse `Ticket` mit Eigenschaften wie Titel, Beschreibung, Priorität, Erstellungsdatum und Status.

Zur Laufzeit werden Tickets und Benutzer mit `ArrayList` verwaltet. Außerdem soll eine generische Klasse `Repository<T>` Standardoperationen wie Hinzufügen, Löschen, Suchen nach ID und Aktualisieren übernehmen.

## Qualität und Stabilität

Die Geschäftslogik soll mit eigenen Exceptions wie `TicketNotFoundException` oder `InvalidDataException` abgesichert werden. Diese Fehler sollen unter anderem dann ausgelöst werden, wenn ein Ticket ohne Titel angelegt wird oder ein nicht existierendes Ticket gesucht wird.

Für die Kernlogik sind JUnit-Tests vorgesehen. Geprüft werden insbesondere die Ticketerstellung, korrekte Statusübergänge und das zuverlässige Auslösen der benutzerdefinierten Exceptions.

## Persistenz und Schnittstellen

Beim Beenden der Anwendung dürfen keine Daten verloren gehen. Deshalb soll der Zustand der Repositories über `ObjectOutputStream` in lokale Dateien wie `tickets.dat` geschrieben und beim Start über `ObjectInputStream` wieder eingelesen werden.

Die Kundendaten stammen aus einer externen Test-API unter `https://jsonplaceholder.typicode.com/users`. Beim Start der Anwendung wird ein HTTP-Request gesendet, die JSON-Antwort verarbeitet und in lokale `Kunde`-Objekte umgewandelt.

## Benutzeroberfläche

Das Frontend wird mit Java Swing umgesetzt. Die GUI soll ein Hauptfenster mit `JTable`, Eingabemasken für neue Tickets sowie Bedienelemente für Speichern, Laden und API-Import enthalten.

Wichtig ist die klare Trennung zwischen GUI und Geschäftslogik nach dem MVC-Prinzip. Exceptions aus dem Backend sollen in der Oberfläche abgefangen und über verständliche Fehlermeldungen mit `JOptionPane` angezeigt werden.

## Empfohlene Projektstruktur

```text
projekt-root/
├── README.md
├── docs/
│   └── Projekt-BFZ-Systemhaus-Ticketsystem.pdf
├── src/
│   ├── main/
│   │   └── java/
│   │       |
│   │       ├── app/
│   │       │   └── Main.java
│   │       │
│   │       ├── controller/
│   │       │   └── MainController.java
│   │       │
│   │       ├── exception/
│   │       │   ├── TicketNotFoundException.java
│   │       │   └── InvalidDataException.java
│   │       │
│   │       ├── model/
│   │       │   ├── Benutzer.java
│   │       │   ├── Admin.java
│   │       │   ├── Kunde.java
│   │       │   ├── Ticket.java
│   │       │   └── Identifiable.java
│   │       │
│   │       ├── repository/
│   │       │   └── Repository.java
│   │       │
│   │       ├── service/
│   │       │   ├── ApiService.java
│   │       │   └── StorageService.java
│   │       │
│   │       └── ui/
│   │            ├── MainFrame.java
│   │            ├── TicketFormDialog.java
│   │            ├── TicketTableModel.java
│   │            └── UserTableModel.java
│   ├── test/
│   │   └── java/
│   │       ├── model/
│   │       │   └── TicketTest.java
│   │       └── repository/
│   │           └── RepositoryTest.java
│   └── storage/
│       ├── kunden.dat
│       └── tickets.dat
```

## Aufgabe im Projekt ablegen

Damit die Aufgabenstellung direkt im Repository verfügbar ist, kann die Originaldatei in einem Ordner `docs/` abgelegt werden. Eine einfache und gut lesbare Struktur ist zum Beispiel `docs/Projekt-BFZ-Systemhaus-Ticketsystem.pdf`.

Zusätzlich ist es sinnvoll, im `README.md` einen kurzen Hinweis auf die Aufgabenstellung aufzunehmen, damit Lehrkräfte, Prüfer oder Teammitglieder sie sofort finden. Ein passender Hinweis wäre zum Beispiel: „Die originale Projektbeschreibung befindet sich im Ordner `docs/`.“

## Beispiel für README-Hinweis

```md
## Dokumentation

- Projektbeschreibung: `docs/Projekt-BFZ-Systemhaus-Ticketsystem.pdf`
- Technische Dokumentation: siehe dieses README
```

## Nächste Umsetzungsschritte

1. Domänenmodell mit `Benutzer`, `Admin`, `Kunde` und `Ticket` erstellen.
2. Generisches `Repository<T>` und erste Service-Klassen umsetzen.
3. Custom Exceptions und JUnit-Tests ergänzen.
4. Serialisierung für Persistenz implementieren.
5. API-Import entwickeln.
6. Swing-Oberfläche auf Basis des MVC-Musters anschließen.
