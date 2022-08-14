package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

public class MemTracker implements Store {
    private int ids = 1;

    List<Item> items = new ArrayList<>();

    public Item add(Item item) {
        items.add(item);
        item.setId(ids++);
        return item;
    }

    public List<Item> findAll() {
        return items;
    }

    public List<Item> findByName(String key) {
        List<Item> rsl = new ArrayList<>();
        int i = 0;
        for (Item name : items) {
            if (key.equals(items.get(i).getName())) {
                rsl.add(name);
            }
            i++;
        }
        return rsl;
    }

    private int indexOf(int id) {
        int rsl = -1;
        int i = 0;
        for (Item name : items) {
            if (name.getId() == id) {
                rsl = i;
                break;
            }
            i++;
        }
        return rsl;
    }

    public Item findById(int id) {
        int index = indexOf(id);
        return index != -1 ? items.get(index) : null;
    }

    public boolean replace(int id, Item item) {
        int index = indexOf(id);
        boolean rsl = index != -1;
        if (rsl) {
            item.setId(id);
            items.set(index, item);
        }
        return rsl;
    }

    public boolean delete(int id) {
        int index = indexOf(id);
        boolean rsl = index != -1;
        if (rsl) {
            items.remove(index);
        }
        return rsl;
    }
}