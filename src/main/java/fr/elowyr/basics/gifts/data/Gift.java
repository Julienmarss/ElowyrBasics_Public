package fr.elowyr.basics.gifts.data;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Gift {

    private List<ItemStack> items;
    private String sender;
    private String message;
    private long createdMillis;

    public Gift(String sender, String message, List<ItemStack> items) {
        this.sender = sender;
        this.message = message;
        this.items = items;
        this.createdMillis = System.currentTimeMillis();
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreatedMillis() {
        return createdMillis;
    }

    public void setCreatedMillis(long createdMillis) {
        this.createdMillis = createdMillis;
    }
}
