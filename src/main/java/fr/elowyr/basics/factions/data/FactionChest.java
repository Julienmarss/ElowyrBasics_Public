package fr.elowyr.basics.factions.data;

import com.google.common.collect.Lists;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.menu.menus.Size;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Getter
public class FactionChest {

    private ItemStack[] factionChestContents;
    private transient Inventory factionChest;

    public void openFactionChest(Player player, int level) {
        int size = this.getSize(level);
        if (this.factionChestContents == null) {
            this.factionChestContents = new ItemStack[size];
        }
        if (this.factionChest == null) {
            this.factionChest = Bukkit.createInventory(null, size, Utils.color("&7Coffre de Faction"));
            this.factionChest.setContents(this.factionChestContents);
        }
        player.openInventory(this.factionChest);
    }

    public void updateChest(int level) {
        if (this.factionChest != null) {
            List<HumanEntity> entityes = Lists.newArrayList(factionChest.getViewers());
            for (int count = 0; count < entityes.size(); count++) {
                entityes.get(count).closeInventory();
            }
            this.factionChestContents = Arrays.copyOf(this.factionChest.getContents(), this.factionChest.getSize());
            this.factionChest = null;
        }
        ItemStack[] newInventory = new ItemStack[this.getSize(level)];
        if (this.factionChestContents != null) {
            for (int count = 0; count < this.factionChestContents.length; count++) {
                newInventory[count] = this.factionChestContents[count];
            }
        }
        this.factionChestContents = newInventory;
    }

    public void save() {
        if (this.factionChest != null) {
            this.factionChestContents = this.factionChest.getContents().clone();
        }
    }

    public int getSize(int level) {
        Size invSize = level == 1 ? Size.UNE_LIGNE : level == 2 ? Size.DEUX_LIGNE : level == 3 ? Size.TROIS_LIGNE : level == 4 ? Size.QUATRE_LIGNE : Size.UNE_LIGNE;
        return invSize.getSize();
    }
}
