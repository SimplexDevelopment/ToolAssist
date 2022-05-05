package io.github.simplex.toolassist.data;

import io.github.simplex.toolassist.ToolAssist;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BlockIdentifier {
    private final List<Block> blockList = new ArrayList<>();
    private final List<Location> blockLocations = new ArrayList<>();
    private final ToolAssist plugin;

    public BlockIdentifier(ToolAssist plugin) {
        this.plugin = plugin;
    }

    public List<Block> populateAndRetrieve(Block block) {
        Location start = block.getLocation().clone();


        return blockList;
    }

    public boolean checkBlock(Block block, ItemStack targetItem) {
        AtomicBoolean isValid = new AtomicBoolean(false);

        if (plugin.getConfig().getSettings().useTags()) {
            TagBox.oreTagList().forEach(tag -> {
                if (tag.isTagged(block.getType())) isValid.set(true);
            });
            return isValid.get();
        }

        Material m = block.getType();
        Material item = targetItem.getType();

        // TODO: Tag or Material checks for the item, then the respective block type.

        return isValid.get();
    }
}
