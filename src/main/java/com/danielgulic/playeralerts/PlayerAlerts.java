package com.danielgulic.playeralerts;

import co.aikar.commands.BukkitCommandExecutionContext;
import co.aikar.commands.CommandContexts;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.leuo.gooey.Gooey;

public final class PlayerAlerts extends JavaPlugin {

    private static PlayerAlerts instance;
    public static PlayerAlerts get() { return instance; }

    private final AlertDatabase db = new AlertDatabase();
    public AlertDatabase getDb() { return db; }

    private Gooey gooey;
    public Gooey getGooey() {
        return gooey;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        gooey = new Gooey(this);

        PaperCommandManager manager = new PaperCommandManager(this);

        registerCommandContexts(manager);
        manager.enableUnstableAPI("help");
        manager.registerCommand(new AlertsCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
        gooey = null;
    }

    private void registerCommandContexts(PaperCommandManager manager) {
        CommandContexts<BukkitCommandExecutionContext> commandContexts = manager.getCommandContexts();

        commandContexts.registerContext(Alert.class, c -> {
            String id = c.popFirstArg();
            try {
                Integer.parseInt(id);
            } catch (NumberFormatException error) {
                throw new InvalidCommandArgument("Alert ID must be an integer");
            }

            Alert alert = getDb().get(id);
            if (alert == null) throw new InvalidCommandArgument("This alert does not exist");

            CommandSender sender = c.getSender();
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!alert.isOwnedBy(player)) throw new InvalidCommandArgument("You can only manage your own alerts");
            }
            return alert;
        });
    }
}
