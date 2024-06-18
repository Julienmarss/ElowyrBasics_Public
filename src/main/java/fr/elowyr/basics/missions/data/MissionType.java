package fr.elowyr.basics.missions.data;

import org.bukkit.Material;

public enum MissionType {

    BREAK_BLOCK(1, Material.IRON_BLOCK),
    FISH(2, Material.FISHING_ROD),
    KILL(3, Material.DIAMOND_SWORD),
    CRAFT(4, Material.WORKBENCH),
    BLOCK_PLACE(5, Material.BARRIER);

    private final int id;
    private final Material material;

    MissionType(int id, Material material) {
        this.id = id;
        this.material = material;
    }

    public int getId() {
        return id;
    }

    public Material getMaterial() {
        return material;
    }
}
