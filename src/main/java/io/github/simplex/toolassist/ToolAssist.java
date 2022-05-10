package io.github.simplex.toolassist;

import io.github.simplex.toolassist.data.Config;
import io.github.simplex.toolassist.play.Command_toolassist;
import io.github.simplex.toolassist.play.MineListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ToolAssist extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Initializing configuration...");
        this.config = new Config(this);
        getLogger().info("Configuration loaded! Registering listener...");
        new MineListener(this);
        getLogger().info("Listener registered successfully! Loading command...");
        new Command_toolassist(this);
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
