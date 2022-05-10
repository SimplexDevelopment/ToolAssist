package io.github.simplex.toolassist;

import io.github.simplex.toolassist.data.Config;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ToolAssist extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Initializing configuration...");
        this.config = new Config(this);
        getLogger().info("Initialization complete! Registering listener...");
        new MineListener(this);
        getLogger().info("Initialization complete!");
    }

    @Override
    public void onDisable() {
        this.config.osave();
        this.config = null;
        getLogger().info("Goodbye!");
    }

    @Override
    public @NotNull Config getConfig() {
        return config;
    }
}
