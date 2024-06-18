package fr.elowyr.basics.spawners.pickaxe;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.utils.nbt.RItemUnsafe;
import fr.elowyr.basics.utils.nbt.RNBTItem;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractPickaxe implements Listener {

     abstract public RItemUnsafe getRItemUnsafe();

     private final String name;
     private boolean active;
     private boolean offline;
     private final int id;
     private boolean listener;
     private final ElowyrBasics plugin = ElowyrBasics.getInstance();

     public AbstractPickaxe(String name, int id, boolean listener) {
          this.name = name;
          this.active = true;
          this.offline = true;
          this.id = id;
          this.listener = listener;

          if(this.listener) {
               this.getPlugin().getServer().getPluginManager().registerEvents(this, this.getPlugin());
          }
     }

     public static boolean isItems(int id, ItemStack item) {
          if(item == null || item.getType() == Material.AIR) {
               return false;
          }
          RNBTItem rnbtItem = new RNBTItem(item);
          if(rnbtItem.getInt("id") == id) {
               return true;
          }
          return false;
     }

     public String getName() {
          return name;
     }

     public boolean isActive() {
          return active;
     }

     public void setActive(boolean active) {
          this.active = active;
     }

     public boolean isOffline() {
          return offline;
     }

     public void setOffline(boolean offline) {
          this.offline = offline;
     }

     public int getId() {
          return id;
     }

     public boolean isListener() {
          return listener;
     }

     public void setListener(boolean listener) {
          this.listener = listener;
     }

     public ElowyrBasics getPlugin() {
          return plugin;
     }
}
