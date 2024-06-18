package fr.elowyr.basics.upgrade.listeners.type;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.upgrade.UpgradeManager;
import fr.elowyr.basics.upgrade.data.Upgrade;
import fr.elowyr.basics.upgrade.data.UpgradeType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CraftUpgradeListener implements Listener {

    private UpgradeManager upgradeManager = UpgradeManager.getInstance();

    @EventHandler
    public void onBreak(CraftItemEvent event) {

        if (event.isCancelled()) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack result = event.getRecipe().getResult().clone();

        Faction faction = FPlayers.getInstance().getByPlayer(player).getFaction();
        FactionData factionData = upgradeManager.getFactionByName(faction.getTag());
        if (factionData == null) return;
        List<Upgrade> upgrades = upgradeManager.getUpgradeByTarget(factionData, result.getType().name());
        if (upgrades == null) return;

        upgrades.forEach(upgrade -> {
            if (upgrade.getUpgradeType() != UpgradeType.CRAFT) return;
            if (upgrade.getActualGoal() >= upgrade.getGoal()) return;

            int possibleCreations = getPossibleCreations(event);
            int amountOfItems = event.getRecipe().getResult().getAmount() * possibleCreations;

            upgrade.setActualGoal(upgrade.getActualGoal() + amountOfItems);
        });


    }

    private static int getPossibleCreations(CraftItemEvent event) {
        int itemsChecked = 0;
        int possibleCreations = 1;
        if (event.isShiftClick()) {
            for (ItemStack item : event.getInventory().getMatrix()) {
                if (item != null && !item.getType().equals(Material.AIR)) {
                    if (itemsChecked == 0)
                        possibleCreations = item.getAmount();
                    else
                        possibleCreations = Math.min(possibleCreations, item.getAmount());
                    itemsChecked++;
                }
            }
        }

        return possibleCreations;
    }

    private int getMaxCraftAmount(CraftingInventory inv) {
        if (inv.getResult() == null)
            return 0;

        int resultCount = inv.getResult().getAmount();
        int materialCount = Integer.MAX_VALUE;

        for (ItemStack is : inv.getMatrix())
            if (is != null && is.getAmount() < materialCount)
                materialCount = is.getAmount();

        return resultCount * materialCount;
    }

    private int fits(ItemStack stack, Inventory inv) {
        ItemStack[] contents = inv.getContents();
        int result = 0;

        for (ItemStack is : contents)
            if (is == null)
                result += stack.getMaxStackSize();
            else if (is.isSimilar(stack))
                result += Math.max(stack.getMaxStackSize() - is.getAmount(), 0);

        return result;
    }
}
