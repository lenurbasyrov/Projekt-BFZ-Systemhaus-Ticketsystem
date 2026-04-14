package repository;

import exception.TicketNotFoundException;
import model.Identifiable;

import java.util.ArrayList;
import java.util.List;

public class Repository<T extends Identifiable> {
    private List<T> data;

    public Repository() {
        data = new ArrayList<>();
    }

    // CRUD-Methoden
    // Add, Remove, GetAll, Update, FindById
    public void add(T item) {
        data.add(item);
    }

    public void remove(T item) {
        data.remove(item);
    }

    public List<T> getAll() {
        return new ArrayList<>(data);
    }

    public void update(T newItem) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(newItem.getId())) {
                data.set(i, newItem);
                return;
            }
        }
        throw new TicketNotFoundException("Item mit ID " + newItem.getId() + " wurde nicht gefunden.");
    }

    public T findById(Long id) {
        for (T item : data) {
            if (item.getId().equals(id)) { // Vergleich der IDs für größere Flexibilität
                return item;
            }
        }
        throw new TicketNotFoundException("Item mit ID " + id + " wurde nicht gefunden.");
    }
}
