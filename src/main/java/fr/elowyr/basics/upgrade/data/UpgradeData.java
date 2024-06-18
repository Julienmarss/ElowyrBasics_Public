package fr.elowyr.basics.upgrade.data;

import java.util.List;

public class UpgradeData {

    private int level;
    private int slot;
    private double price;
    private List<String> rewards;
    private List<Upgrade> upgrades;
    private String name;
    private List<String> lore;
    private String itemLocked;
    private String itemUnLocked;

    public UpgradeData(int level, int slot, double price, List<String> rewards, List<Upgrade> upgrades, String name, List<String> lore, String itemLocked, String itemUnLocked) {
        this.level = level;
        this.slot = slot;
        this.price = price;
        this.rewards = rewards;
        this.upgrades = upgrades;
        this.name = name;
        this.lore = lore;
        this.itemLocked = itemLocked;
        this.itemUnLocked = itemUnLocked;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getRewards() {
        return rewards;
    }

    public void setRewards(List<String> rewards) {
        this.rewards = rewards;
    }

    public List<Upgrade> getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(List<Upgrade> upgrades) {
        this.upgrades = upgrades;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public String getItemLocked() {
        return itemLocked;
    }

    public void setItemLocked(String itemLocked) {
        this.itemLocked = itemLocked;
    }

    public String getItemUnLocked() {
        return itemUnLocked;
    }

    public void setItemUnLocked(String itemUnLocked) {
        this.itemUnLocked = itemUnLocked;
    }
}
