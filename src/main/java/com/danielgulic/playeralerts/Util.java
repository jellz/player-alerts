package com.danielgulic.playeralerts;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Util {
    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 0.7F, 8.0F);
    }
}
