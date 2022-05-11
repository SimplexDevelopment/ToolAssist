package io.github.simplex.toolassist;

import io.github.simplex.toolassist.data.Config;
import io.github.simplex.toolassist.play.Command_toolassist;
import io.github.simplex.toolassist.play.MineListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ToolAssist extends JavaPlugin {

    private Config config;
    private Command_toolassist command;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Initializing configuration...");
        config = new Config(this);
        getLogger().info("Configuration loaded! Registering listener...");
        new MineListener(this);
        getLogger().info("Listener registered successfully! Loading command...");
        command = new Command_toolassist(this);
        getLogger().info("Initialization complete!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Saving configuration...");
        config.osave();
        config = null;
        getLogger().info("Configuration saved successfully. Unregistering the command...");
        command.unregister();
        command = null;
        getLogger().info("Termination complete. Goodbye!");
    }

    @Override
    public @NotNull Config getConfig() {
        return config;
    }
}
