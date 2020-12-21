package com.danielgulic.playeralerts;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class Alert {
    private final int id;
    private final UUID player;
    private final String text;
    private final Date createdAt;

    public Alert(int id, UUID player, String text) {
        this.id = id;
        this.player = player;
        this.text = text;
        this.createdAt = new Date();
    }

    public int getId() {
        return id;
    }

    public UUID getPlayer() {
        return player;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getColouredText() {
        return ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', getText());
    }

    public boolean isOwnedBy(Player player) {
        return getPlayer().equals(player.getUniqueId());
    }
}
