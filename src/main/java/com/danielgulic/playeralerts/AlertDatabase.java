package com.danielgulic.playeralerts;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AlertDatabase {
    private final Map<Integer, Alert> db;
    private int currentId;

    public AlertDatabase() {
        db = new HashMap<>();
        currentId = 0;
    }

    public Alert add(UUID player, String text) {
        int id = ++currentId;
        db.put(id, new Alert(id, player, text));
        return this.get(id);
    }

    public void remove(Integer id) {
        db.remove(id);
    }

    public Alert get(String id) {
        return db.get(Integer.parseInt(id));
    }

    public Alert get(Integer id) {
        return db.get(id);
    }

    public Map<Integer, Alert> getByPlayer(UUID player) {
        Map<Integer, Alert> owned = new HashMap<>(db);
        owned.values().removeIf(a -> !a.getPlayer().equals(player));
        return owned;
    }
}
