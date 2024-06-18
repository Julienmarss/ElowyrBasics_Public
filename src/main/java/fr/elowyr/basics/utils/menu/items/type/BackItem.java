package fr.elowyr.basics.utils.menu.items.type;

import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.menu.ClickAction;
import fr.elowyr.basics.utils.menu.MenuManager;
import fr.elowyr.basics.utils.menu.items.VirtualItem;
import fr.elowyr.basics.utils.menu.menus.VirtualGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BackItem extends VirtualItem {
	
	public BackItem() {
		super(new ItemBuilder(Material.ARROW).setName("&c&lRetour").toItemStack());

		this.onItemClick(new ClickAction() {
			public void onClick(InventoryClickEvent event) {
				Player player = (Player) event.getWhoClicked();
				VirtualGUI gui = (VirtualGUI) MenuManager.getInstance().getGuis().get(player.getUniqueId());
				if (gui != null) {
					gui.open(player);
				}
			}
		});
	}
}
