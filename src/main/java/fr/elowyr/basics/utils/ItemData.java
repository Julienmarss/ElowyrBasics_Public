package fr.elowyr.basics.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemData {

    private Material material;
    private byte data;
    private int amount;

    public ItemData(Material material, byte data) {
        this.material = material;
        this.data = data;
        this.amount = 1;
    }

    public ItemData(Material material, byte data, int amount) {
        this.material = material;
        this.data = data;
        this.amount = amount;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ItemStack toItemStack() {
        return new ItemBuilder(this.material, this.amount, this.data).toItemStack();
    }

    public boolean isSimilar(ItemData itemData) {
        return itemData.getMaterial() == this.material && itemData.getData() == this.data;
    }
}