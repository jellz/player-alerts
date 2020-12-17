package com.danielgulic.playeralerts;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import xyz.leuo.gooey.action.Action;
import xyz.leuo.gooey.button.Button;
import xyz.leuo.gooey.gui.GUI;

public class RunCommandAction implements Action {
    private final ClickType clickType;
    private final String command;

    @Override
    public void run(Player player, GUI gui, Button button, InventoryClickEvent clickEvent) {
        if (clickEvent.getClick() == clickType) {
            player.performCommand(this.command);
            player.closeInventory();
        }
    }

    public RunCommandAction(ClickType clickType, String command) {
        this.clickType = clickType;
        this.command = command;
    }
}
