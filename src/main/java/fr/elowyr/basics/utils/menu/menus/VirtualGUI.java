package fr.elowyr.basics.utils.menu.menus;

import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.menu.BoardContainer;
import fr.elowyr.basics.utils.menu.CloseAction;
import fr.elowyr.basics.utils.menu.items.UpdaterItem;
import fr.elowyr.basics.utils.menu.items.VirtualItem;
import fr.elowyr.basics.utils.menu.items.type.BackItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class VirtualGUI implements GUI {
	private String name;
	private Size size;
	private VirtualItem[] items;
	private CloseAction closeAction;
	private boolean allowBackItem;
	private boolean allowClick;

	public VirtualGUI(String name, Size size) {
		this.name = Utils.color(name);
		this.size = size;
		this.items = new VirtualItem[size.getSize()];
	}

	public VirtualGUI allowBackItem() {
		this.allowBackItem = true;
		return this;
	}

	public BoardContainer getBoardContainer(int slotFrom, int length, int width) {
		return new BoardContainer(this, slotFrom, length, width);
	}

	public VirtualGUI allowClick() {
		this.allowClick = true;
		return this;
	}

	public VirtualGUI onCloseAction(CloseAction closeAction) {
		this.closeAction = closeAction;
		return this;
	}

	public VirtualGUI setItem(int position, VirtualItem menuItem) {
		this.items[position] = menuItem;
		return this;
	}

	public VirtualGUI addItem(VirtualItem item) {
		int next = this.getNextEmptySlot();
		if (next == -1) {
			return this;
		} else {
			this.items[next] = item;
			return this;
		}
	}

	private int getNextEmptySlot() {
		for(int i = 0; i < this.items.length; ++i) {
			if (this.items[i] == null) {
				return i;
			}
		}

		return -1;
	}

	public VirtualGUI fillEmptySlots(VirtualItem menuItem) {
		for(int i = 0; i < this.items.length; ++i) {
			if ((!this.allowBackItem || i != this.size.getSize() - 1) && this.items[i] == null) {
				this.items[i] = menuItem;
			}
		}

		return this;
	}

	public void open(Player player) {
		Inventory inventory = Bukkit.createInventory(new VirtualHolder(this, Bukkit.createInventory(player, this.size.getSize())), this.size.getSize(), this.name);
		this.apply(inventory, player);
		player.openInventory(inventory);
	}

	public void apply(Inventory inventory, Player player) {
		if (this.allowBackItem && this.items[inventory.getSize() - 1] == null)
			this.items[inventory.getSize() - 1] = (VirtualItem)new BackItem();
		for (int i = 0; i < this.items.length; i++) {
			VirtualItem item = this.items[i];
			if (item != null) {
				inventory.setItem(i, item.getItem());
				if (item instanceof UpdaterItem) {
					UpdaterItem updater = (UpdaterItem)item;
					updater.startUpdate(player, i);
				}
			}
		}
	}

	public void onInventoryClick(InventoryClickEvent event) {
		int slot = event.getRawSlot();
		if (!this.allowClick)
			event.setCancelled(true);
		if (slot >= 0 && slot < this.size.getSize() && this.items[slot] != null) {
			VirtualItem item = this.items[slot];
			if (item.getAction() != null)
				item.getAction().onClick(event);
		}
	}

	public void onInventoryClose(InventoryCloseEvent event) {
		if (this.closeAction != null) {
			this.closeAction.onClose(event);
			this.closeAction = null;
		}
		for (int i = 0; i < this.items.length; i++) {
			VirtualItem item = this.items[i];
			if (item instanceof UpdaterItem) {
				UpdaterItem updater = (UpdaterItem)item;
				updater.cancelUpdate();
			}
		}
	}

	public void destroy() {
		this.name = null;
		this.size = null;
		this.items = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public VirtualItem[] getItems() {
		return items;
	}

	public void setItems(VirtualItem[] items) {
		this.items = items;
	}

	public CloseAction getCloseAction() {
		return closeAction;
	}

	public void setCloseAction(CloseAction closeAction) {
		this.closeAction = closeAction;
	}

	public boolean isAllowBackItem() {
		return allowBackItem;
	}

	public void setAllowBackItem(boolean allowBackItem) {
		this.allowBackItem = allowBackItem;
	}

	public boolean isAllowClick() {
		return allowClick;
	}

	public void setAllowClick(boolean allowClick) {
		this.allowClick = allowClick;
	}
}
