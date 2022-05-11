package io.github.simplex.toolassist.play;

import io.github.simplex.toolassist.ToolAssist;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Command_toolassist extends Command implements PluginIdentifiableCommand, TabCompleter {
    private final ToolAssist plugin;

    public Command_toolassist(ToolAssist plugin) {
        super("toolassist");
        super.setLabel("toolassist");
        super.setPermission("toolassist.admin");
        super.setDescription("A way to modify the configuration in game.");
        super.setUsage("/toolassist <plugin | (tool <add | remove>)> <setting> <value>");
        super.setAliases(List.of("ta", "miner", "assist", "vm"));
        super.permissionMessage(Component.empty().content("You must have the toolassist.admin permission to use this command.")
                .color(TextColor.color(ChatColor.RED.asBungee().getColor().getRGB())));
        super.register(plugin.getServer().getCommandMap());
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args[0].equalsIgnoreCase("plugin")) {
            if (args.length != 3) return false;

            switch (args[1]) {
                case "radius" -> {
                    plugin.getConfig().getSettings().setPluginEntry("search_radius", args[2]);
                    return true;
                }
                case "sneak" -> {
                    plugin.getConfig().getSettings().setPluginEntry("sneak_activation", args[2]);
                    return true;
                }
                case "use_config" -> {
                    plugin.getConfig().getSettings().setPluginEntry("no_config", args[2]);
                    return true;
                }
                case "permission" -> {
                    plugin.getConfig().getSettings().setPluginEntry("permission", args[2]);
                    return true;
                }
                default -> {
                    return false;
                }
            }
        }

        if (args[0].equalsIgnoreCase("tools")) {
            if (args.length != 4) return false;

            if (args[1].equalsIgnoreCase("add")) {
                switch (args[2]) {
                    case "pickaxe", "axe", "sword", "shears", "shovel", "hoe" -> {
                        plugin.getConfig().getSettings().modifyToolEntry(args[2], args[3], true);
                        return true;
                    }
                    default -> {
                        return false;
                    }
                }
            }

            if (args[1].equalsIgnoreCase("remove")) {
                switch (args[2]) {
                    case "pickaxe", "axe", "sword", "shears", "shovel", "hoe" -> {
                        plugin.getConfig().getSettings().modifyToolEntry(args[2], args[3], false);
                        return true;
                    }
                    default -> {
                        return false;
                    }
                }
            }

            return false;
        }

        return false;
    }

    @Override
    public @NotNull ToolAssist getPlugin() {
        return plugin;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        switch (args.length) {
            case 1 -> {
                completions = List.of("tools", "plugin");
            }
            case 2 -> {
                if (args[0].equalsIgnoreCase("tools")) {
                    completions = List.of("add", "remove");
                }
                if (args[0].equalsIgnoreCase("plugin")) {
                    completions = List.of("permission", "radius", "sneak", "use_config");
                }
            }
            case 3 -> {
                if (args[0].equalsIgnoreCase("tools")) {
                    if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove")) {
                        completions = Stream.of("pickaxe", "axe", "sword", "shears", "shovel", "hoe").sorted().toList();
                    }
                }

                if (args[0].equalsIgnoreCase("plugin")) {
                    if (args[1].equalsIgnoreCase("use_config") || args[1].equalsIgnoreCase("sneak")) {
                        completions = List.of("true", "false");
                    }
                }
            }
        }

        return completions.stream().filter(p -> p.startsWith(args[args.length - 1])).toList();
    }

    public void unregister() {
        unregister(plugin.getServer().getCommandMap());
    }
}
