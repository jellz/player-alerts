package com.danielgulic.playeralerts;

import java.util.Date;
import java.util.UUID;

public class Alert {
    final int id;
    final UUID player;
    final String text;
    final Date createdAt;

    public Alert(int id, UUID player, String text) {
        this.id = id;
        this.player = player;
        this.text = text;
        this.createdAt = new Date();
    }
}
