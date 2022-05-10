package io.github.simplex.toolassist;

import io.github.simplex.toolassist.data.BlockIdentifier;
import io.github.simplex.toolassist.data.Config;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MineListener implements Listener {
    private final BlockIdentifier identifier;
    private final Config.Settings settings;

    public MineListener(ToolAssist plugin) {
        this.settings = plugin.getConfig().getSettings();
        this.identifier = new BlockIdentifier(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void activate(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItemInMainHand();
        Block block = event.getBlock();
        String permission = settings.permission();
        List<Block> blocks = identifier.populateAndRetrieve(block, stack);

        if (!player.hasPermission(permission)) return;

        if (!player.isSneaking() && settings.useSneak()) return;

        blocks.forEach(Block::breakNaturally);
    }
}
