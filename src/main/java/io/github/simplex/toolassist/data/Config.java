package io.github.simplex.toolassist.data;

import io.github.simplex.toolassist.ToolAssist;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
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

        InputStream stream = plugin.getResource(fileName);
        assert stream != null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            if (!cf.exists()) {
                cf.createNewFile();
                plugin.saveResource(fileName, true);
            }

            oload();

            reader.lines().filter(s -> s.contains(":"))
                    .map(s -> s.split(":")[0])
                    .filter(s -> !super.getValues(true).containsKey(s))
                    .forEach(s -> {
                        plugin.getLogger().severe("Configuration is missing an entry, attempting to replace...");
                        Optional<String> stringStream = reader.lines().filter(c -> c.contains(s)).findFirst();
                        if (stringStream.isEmpty())
                            throw new RuntimeException("Unable to fix your configuration file. Please delete the config.yml in the data folder and restart your server.");
                        String key = stringStream.get().split(":")[0].trim();
                        String value = stringStream.get().split(":")[1].trim();
                        super.addDefault(key, value);
                        osave();
                    });


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

        public Settings(Config config) {
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
    }
}
