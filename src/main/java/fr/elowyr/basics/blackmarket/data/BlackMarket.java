package fr.elowyr.basics.blackmarket.data;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
public class BlackMarket {

    private int id;
    private ItemStack item;
    private int price;
    private int quantity;
    private int timeLeft;

    public BlackMarket(int id, ItemStack item, int price, int quantity, int timeLeft) {
        this.id = id;
        this.item = item;
        this.price = price;
        this.quantity = quantity;
        this.timeLeft = timeLeft;
    }
}
