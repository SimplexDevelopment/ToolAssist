package io.github.simplex.toolassist.data;

import org.bukkit.Material;
import org.bukkit.Tag;

import java.util.List;

public class TagBox {
    public static List<Tag<Material>> oreTagList() {
        return List.of(Tag.COAL_ORES,
                Tag.IRON_ORES,
                Tag.COPPER_ORES,
                Tag.GOLD_ORES,
                Tag.DIAMOND_ORES,
                Tag.REDSTONE_ORES,
                Tag.EMERALD_ORES,
                Tag.LAPIS_ORES);
    }

    public static Tag<Material> leaves() {
        return Tag.LEAVES;
    }

    public static Tag<Material> sandType() {
        return Tag.SAND;
    }

    public static Tag<Material> wart() {
        return Tag.WART_BLOCKS;
    }

    public static Tag<Material> logs() {
        return Tag.LOGS;
    }

    public static Tag<Material> vines() {
        return Tag.CAVE_VINES;
    }
}
