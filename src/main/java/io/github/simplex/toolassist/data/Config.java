package io.github.simplex.toolassist.data;

import io.github.simplex.toolassist.ToolAssist;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Config extends YamlConfiguration {

    private final Settings settings;

    public Config(ToolAssist plugin) {
        this.settings = new Settings(this);

        String fileName = "config.yml";

        File dataFolder = plugin.getDataFolder();

        if (!dataFolder.exists()) dataFolder.mkdirs();

        File cf = new File(dataFolder, fileName);

        InputStream stream = plugin.getResource(fileName);
        assert stream != null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            if (!cf.exists()) {
                cf.createNewFile();
                plugin.saveResource(fileName, true);
            }

            oload(cf);

            reader.lines().filter(s -> s.contains(":"))
                    .map(s -> s.split(":")[0])
                    .filter(s -> !super.getValues(true).containsKey(s))
                    .forEach(s -> {
                        plugin.getLogger().severe("Configuration is missing an entry, attempting to replace...");
                        Optional<String> stringStream = reader.lines().filter(c -> c.contains(s)).findFirst();
                        assert stringStream.isPresent();
                        String key = stringStream.get().split(":")[0].trim();
                        String value = stringStream.get().split(":")[1].trim();
                        super.addDefault(key, value);
                        osave(cf);
                    });


        } catch (IOException ex) {
            plugin.getLogger().severe(ex.getMessage());
        }

        oload(cf);
    }

    public void osave(File cf) {
        try {
            super.save(cf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void oload(File cf) {
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

        public final boolean useTags() {
            return plugin_settings.getBoolean("use_tags_no_config", false);
        }

        public final boolean useSneak() {
            return plugin_settings.getBoolean("sneak_activation", true);
        }

        public final int radius() {
            return plugin_settings.getInt("search_radius", 15);
        }

        public final Set<Material> pickaxeMaterials() {
            Set<Material> materials = new HashSet<>();
            tool_settings.getStringList("pickaxe")
                    .stream()
                    .map(Material::matchMaterial)
                    .forEach(materials::add);
            return materials;
        }

        public final Set<Material> axeMaterials() {
            Set<Material> materials = new HashSet<>();
            tool_settings.getStringList("axe")
                    .stream()
                    .map(Material::matchMaterial)
                    .forEach(materials::add);
            return materials;
        }

        public final Set<Material> shovelMaterials() {
            Set<Material> materials = new HashSet<>();
            tool_settings.getStringList("shovel")
                    .stream()
                    .map(Material::matchMaterial)
                    .forEach(materials::add);
            return materials;
        }

        public final Set<Material> hoeMaterials() {
            Set<Material> materials = new HashSet<>();
            tool_settings.getStringList("hoe")
                    .stream()
                    .map(Material::matchMaterial)
                    .forEach(materials::add);
            return materials;
        }

        public final Set<Material> swordMaterials() {
            Set<Material> materials = new HashSet<>();
            tool_settings.getStringList("sword")
                    .stream()
                    .map(Material::matchMaterial)
                    .forEach(materials::add);
            return materials;
        }

        public final Set<Material> shearMaterials() {
            Set<Material> materials = new HashSet<>();
            tool_settings.getStringList("shears")
                    .stream()
                    .map(Material::matchMaterial)
                    .forEach(materials::add);
            return materials;
        }
    }
}
