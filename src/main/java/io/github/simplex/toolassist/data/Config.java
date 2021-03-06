package io.github.simplex.toolassist.data;

import io.github.simplex.toolassist.ToolAssist;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Config extends YamlConfiguration {

    private final Settings settings;
    private final File cf;

    public Config(ToolAssist plugin) {
        this.settings = new Settings(this);
        String fileName = "config.yml";
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) dataFolder.mkdirs();
        cf = new File(dataFolder, fileName);
        try {
            if (cf.createNewFile()) {
                plugin.saveResource(fileName, true);
            }
        } catch (IOException ex) {
            plugin.getLogger().severe(ex.getMessage());
        }
        oload();
    }

    public void osave() {
        try {
            super.save(cf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void oload() {
        try {
            super.load(cf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Settings getSettings() {
        return settings;
    }

    public static class Settings {
        private final ConfigurationSection plugin_settings;
        private final ConfigurationSection tool_settings;
        private final Config config;

        public Settings(Config config) {
            this.config = config;
            this.plugin_settings = config.getConfigurationSection("plugin_settings");
            this.tool_settings = config.getConfigurationSection("tool_settings");
        }

        public final String permission() {
            return plugin_settings.getString("permission", "toolassist.activate");
        }

        public final boolean noConfig() {
            return plugin_settings.getBoolean("no_config", false);
        }

        public final boolean useSneak() {
            return plugin_settings.getBoolean("sneak_activation", true);
        }

        public final int radius() {
            return plugin_settings.getInt("search_radius", 15);
        }

        public final Set<Material> pickaxeMaterials() {
            return tool_settings.getStringList("pickaxe")
                    .stream()
                    .map(Material::matchMaterial)
                    .collect(Collectors.toSet());
        }

        public final Set<Material> axeMaterials() {
            return tool_settings.getStringList("axe")
                    .stream()
                    .map(Material::matchMaterial)
                    .collect(Collectors.toSet());
        }

        public final Set<Material> shovelMaterials() {
            return tool_settings.getStringList("shovel")
                    .stream()
                    .map(Material::matchMaterial)
                    .collect(Collectors.toSet());
        }

        public final Set<Material> hoeMaterials() {
            return tool_settings.getStringList("hoe")
                    .stream()
                    .map(Material::matchMaterial)
                    .collect(Collectors.toSet());
        }

        public final Set<Material> swordMaterials() {
            return tool_settings.getStringList("sword")
                    .stream()
                    .map(Material::matchMaterial)
                    .collect(Collectors.toSet());
        }

        public final Set<Material> shearMaterials() {
            return tool_settings.getStringList("shears")
                    .stream()
                    .map(Material::matchMaterial)
                    .collect(Collectors.toSet());
        }

        public final void modifyToolEntry(String name, String value, boolean addOrRemove) {
            List<String> materialList = tool_settings.getStringList(name);

            // This is to use a tertiary statement instead of an if-else.
            // The respective method will be called and then the result will be set to the ignored boolean, which can be safely ignored.
            boolean ignored = addOrRemove ? materialList.add(value) : materialList.remove(value);

            tool_settings.set(name, materialList);
            config.osave();
        }

        public final void removeToolEntry(String name, String value) {
            List<String> materialList = tool_settings.getStringList(name);
            materialList.remove(value);
            tool_settings.set(name, materialList);
            config.osave();
        }

        public final void setPluginEntry(String name, Object value) {
            plugin_settings.set(name, value);
            config.osave();
        }
    }
}
