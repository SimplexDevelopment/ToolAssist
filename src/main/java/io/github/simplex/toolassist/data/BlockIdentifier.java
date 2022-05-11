package io.github.simplex.toolassist.data;

import io.github.simplex.toolassist.ToolAssist;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BlockIdentifier {
    private final ToolAssist plugin;
    boolean isValid = false;

    public BlockIdentifier(ToolAssist plugin) {
        this.plugin = plugin;
    }

    public List<Block> populateAndRetrieve(Block block, ItemStack requiredItem) {
        List<Block> surroundingBlocks = new ArrayList<>();
        Location start = block.getLocation().clone();
        int radius = plugin.getConfig().getSettings().radius();
        for (double x = start.getX() - radius; x <= start.getX() + radius; x++) {
            for (double y = start.getY() - radius; y <= start.getY() + radius; y++) {
                for (double z = start.getZ() - radius; z <= start.getZ() + radius; z++) {
                    Location location = new Location(start.getWorld(), x, y, z);
                    if (checkBlock(location.getBlock(), requiredItem)) {
                        surroundingBlocks.add(location.getBlock());
                    }
                }
            }
        }
        return surroundingBlocks;
    }

    public boolean checkBlock(Block block, ItemStack targetItem) {
        if (plugin.getConfig().getSettings().noConfig()) {
            return block.isValidTool(targetItem) && all.stream().anyMatch(a -> a.isTagged(block.getType()));
        }

        checkBlockConfig(block, targetItem);
        return isValid;
    }

    private void checkBlockConfig(Block block, ItemStack targetItem) {
        String itemName = targetItem.getType().name();
        Config.Settings settings = plugin.getConfig().getSettings();
        if (itemName.endsWith("pickaxe")) {
            if (settings.pickaxeMaterials().contains(block.getType())) isValid = true;
            return;
        }
        if (!itemName.endsWith("pickaxe") && itemName.endsWith("axe")) {
            if (settings.axeMaterials().contains(block.getType())) isValid = true;
            return;
        }
        if (itemName.endsWith("shears")) {
            if (settings.shearMaterials().contains(block.getType())) isValid = true;
            return;
        }
        if (itemName.endsWith("shovel")) {
            if (settings.shovelMaterials().contains(block.getType())) isValid = true;
            return;
        }
        if (itemName.endsWith("hoe")) {
            if (settings.hoeMaterials().contains(block.getType())) isValid = true;
            return;
        }
        if (itemName.endsWith("sword")) {
            if (settings.swordMaterials().contains(block.getType())) isValid = true;
            return;
        }
        isValid = false;
    }

    public List<Tag<Material>> all = List.of(Tag.COAL_ORES,
            Tag.COPPER_ORES,
            Tag.GOLD_ORES,
            Tag.IRON_ORES,
            Tag.DIAMOND_ORES,
            Tag.LAPIS_ORES,
            Tag.EMERALD_ORES,
            Tag.REDSTONE_ORES,
            Tag.SAND,
            Tag.CAVE_VINES,
            Tag.WART_BLOCKS,
            Tag.LOGS,
            Tag.LOGS_THAT_BURN,
            Tag.LEAVES,
            Tag.WARPED_STEMS);
}
