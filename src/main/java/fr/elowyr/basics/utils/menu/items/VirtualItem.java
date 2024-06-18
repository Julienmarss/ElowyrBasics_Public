package fr.elowyr.basics.utils.menu.items;

import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.menu.ClickAction;
import org.bukkit.inventory.ItemStack;

public class VirtualItem {
  private final ItemStack item;

  private ClickAction action;

  public void setAction(ClickAction action) {
    this.action = action;
  }

  public ItemStack getItem() {
    return this.item;
  }

  public ClickAction getAction() {
    return this.action;
  }

  public VirtualItem(ItemStack item) {
    this.item = item;
    this.action = null;
  }

  public VirtualItem(ItemBuilder item) {
    this.item = item.toItemStack();
    this.action = null;
  }

  public VirtualItem onItemClick(ClickAction action) {
    this.action = action;
    return this;
  }
}