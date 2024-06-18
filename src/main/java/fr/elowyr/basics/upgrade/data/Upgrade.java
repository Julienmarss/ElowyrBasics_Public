package fr.elowyr.basics.upgrade.data;

import fr.elowyr.basics.utils.ItemData;

import java.util.List;

public class Upgrade {

    private String name;
    private String displayName;
    private List<String> lore;
    private int slot;
    private ItemData itemData;
    private String target;
    private int goal;
    private UpgradeType upgradeType;
    private int actualGoal;

    public Upgrade(Upgrade upgrade) {
        this.setName(upgrade.getName());
        this.setDisplayName(upgrade.getDisplayName());
        this.setLore(upgrade.getLore());
        this.setSlot(upgrade.getSlot());
        this.setItemData(upgrade.getItemData());
        this.setTarget(upgrade.getTarget());
        this.setGoal(upgrade.getGoal());
        this.setUpgradeType(upgrade.getUpgradeType());
        this.setActualGoal(0);
    }

    public Upgrade(String name, String displayName, List<String> lore, int slot, ItemData itemData, String target, int goal, UpgradeType upgradeType) {
        this.name = name;
        this.displayName = displayName;
        this.lore = lore;
        this.slot = slot;
        this.itemData = itemData;
        this.target = target;
        this.goal = goal;
        this.upgradeType = upgradeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public ItemData getItemData() {
        return itemData;
    }

    public void setItemData(ItemData itemData) {
        this.itemData = itemData;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public UpgradeType getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(UpgradeType upgradeType) {
        this.upgradeType = upgradeType;
    }

    public int getActualGoal() {
        return actualGoal;
    }

    public void setActualGoal(int actualGoal) {
        this.actualGoal = actualGoal;
    }
}
