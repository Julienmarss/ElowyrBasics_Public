package fr.elowyr.basics.utils.menu.items.type;

import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.menu.items.VirtualItem;
import org.bukkit.Material;

public class CloseItem extends VirtualItem {


	public CloseItem() {
		super(new ItemBuilder(Material.BARRIER).setName("&6&lFermer le menu").toItemStack());

		this.onItemClick(event -> event.getWhoClicked().closeInventory());
	}
}
