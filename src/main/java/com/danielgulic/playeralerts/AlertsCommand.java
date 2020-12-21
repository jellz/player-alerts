package com.danielgulic.playeralerts;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import xyz.leuo.gooey.button.Button;
import xyz.leuo.gooey.gui.GUI;

import java.util.Map;

@CommandAlias("alerts")
@Description("Manage your alerts")
public class AlertsCommand extends BaseCommand {

    @Subcommand("list")
    @Default
    public static void listAlerts(Player player) {
        AlertDatabase db = PlayerAlerts.get().getDb();
        Map<Integer, Alert> ownedAlerts = db.getByPlayer(player.getUniqueId());

        if (ownedAlerts.size() > 0) {
            int ownedSize = ownedAlerts.size();
            int guiSize = ownedSize % 9 == 0 ? ownedSize : ownedSize + (9 - (ownedSize % 9)); // Get nearest multiple of 9 for GUI size

            GUI gui = new GUI(ChatColor.GOLD + "Your Alerts", guiSize);

            ownedAlerts.values().forEach(a -> {
                Button button = new Button(Material.PAPER, 1, ChatColor.YELLOW + "Alert #" + a.getId());

                button.setLore(
                        ChatColor.GRAY + "Text: " + a.getColouredText(),
                        ChatColor.GRAY + "Created: " + ChatColor.WHITE + a.getCreatedAt().toString(),
                        "",
                        ChatColor.RED + "Middle click to remove this alert");

                button.setAction(new RunCommandAction(ClickType.MIDDLE, "alerts remove " + a.getId()));
                gui.addButton(button);
            });

            gui.open(player);
        } else {
            player.sendMessage(ChatColor.RED + "You have no alerts yet.\n"
                    + ChatColor.RED + "Create one with /alerts add <text>");
            Util.playSound(player, Sound.BLOCK_ANVIL_LAND);
        }
    }

    @Subcommand("add")
    public static void addAlert(Player player, String text) {
        AlertDatabase db = PlayerAlerts.get().getDb();
        Alert alert = db.add(player.getUniqueId(), text);
        player.sendMessage(ChatColor.YELLOW + "Added alert #" + alert.getId() + ": " + alert.getColouredText() +
                "\n" + ChatColor.YELLOW + "View all of your alerts with /alerts!");
        Util.playSound(player, Sound.BLOCK_ANVIL_FALL);
    }

    @Subcommand("remove")
    public static void removeAlert(Player player, Alert alert) {
        AlertDatabase db = PlayerAlerts.get().getDb();
        db.remove(alert.getId());
        player.sendMessage(ChatColor.YELLOW + "Removed alert #" + alert.getId() + ": " + alert.getColouredText());
        Util.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING);
    }

}
