package com.danielgulic.playeralerts;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.leuo.gooey.Gooey;

public final class PlayerAlerts extends JavaPlugin {

    private static PlayerAlerts instance;
    public static PlayerAlerts get() { return instance; }

    private final AlertDatabase db = new AlertDatabase();
    public AlertDatabase getDb() { return db; }

    private Gooey gooey;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        gooey = new Gooey(this);
        this.getCommand("alerts").setExecutor(new AlertsCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
        gooey = null;
    }
}
