package io.github.simplex.toolassist;

import io.github.simplex.toolassist.data.Config;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ToolAssist extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.config = new Config(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public @NotNull Config getConfig() {
        return config;
    }
}
