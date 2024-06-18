package fr.elowyr.basics.utils.menu;

import fr.elowyr.basics.utils.menu.menus.Size;
import fr.elowyr.basics.utils.menu.menus.VirtualGUI;
import org.bukkit.entity.Player;

public abstract class ComplexVirtualGUI extends VirtualGUI {
    public ComplexVirtualGUI(String name, Size size) {
        super(name, size);
    }

    public abstract void load(Player paramPlayer);

    public void open(Player player) {
        load(player);
        super.open(player);
    }
}
