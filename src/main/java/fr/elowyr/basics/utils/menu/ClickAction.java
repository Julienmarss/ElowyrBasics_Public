package fr.elowyr.basics.utils.menu;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface ClickAction {
  void onClick(InventoryClickEvent event);
}

