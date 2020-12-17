package com.danielgulic.playeralerts;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import xyz.leuo.gooey.button.Button;
import xyz.leuo.gooey.gui.GUI;

import java.util.Arrays;
import java.util.HashMap;

public class AlertsCommand implements CommandExecutor  {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        AlertDatabase db = PlayerAlerts.get().getDb();

        if (args.length == 0) {
            // Open alert list GUI
            HashMap<Integer, Alert> owned = db.getByPlayer(player.getUniqueId());
            if (owned.size() > 0) {
//            owned.values().forEach(a -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e" + a.id + ". &r" + a.text)));
                int ownedSize = owned.size();
                int guiSize = ownedSize % 9 == 0 ? ownedSize : ownedSize + (9 - (ownedSize % 9));
                GUI gui = new GUI(ChatColor.GOLD + "Your Alerts", guiSize);
                owned.values().forEach(a -> {
                    Button button = new Button(Material.PAPER, 1, ChatColor.YELLOW + "Alert #" + a.id);
                    button.setLore(
                            ChatColor.translateAlternateColorCodes('&', "&7Text: &f" + a.text),
                            ChatColor.GRAY + "Created: " + ChatColor.WHITE + a.createdAt.toString(),
                            "",
                            ChatColor.RED + "Middle click to remove this alert");
                    button.setAction(new RunCommandAction(ClickType.MIDDLE, "alerts remove " + a.id));
                    gui.addButton(button);
                });
                gui.open(player);
            } else {
                sender.sendMessage(ChatColor.RED + "You have no alerts yet.\n" + ChatColor.RED + "Create one with /alerts add <text>");
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.7F, 8.0F);
            }
            return true;
        } else if (args[0].equalsIgnoreCase("remove") && Util.isInteger(args[1])) {
            // Remove alert by ID
            Alert alert = db.get(args[1]);
            if (alert != null) {
                if (player.getUniqueId().equals(alert.player)) {
                    db.remove(alert.id);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eRemoved alert #" + alert.id + ": &r" + alert.text));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.7F, 8.0F);
                } else {
                    player.sendMessage(ChatColor.RED + "You can only remove your own alerts!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "This alert does not exist!");
            }
            return true;
        } else if (args[0].equalsIgnoreCase("add") && args.length > 1) {
            // Add alert
            String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
            String text = String.join(" ", newArgs);

            Alert alert = db.add(player.getUniqueId(), text);

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eAdded alert #" + alert.id + ": &r" + alert.text + "\n&eView all of your alerts with /alerts!"));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, 0.7F, 8.0F);
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "Usage: /alerts [add|remove] <text|id>");
            return true;
        }
    }
}
