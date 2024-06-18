package fr.elowyr.basics.blocked;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.lang.Lang;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class BlockedPotions implements Listener {

    private final Short[] shorts = {16388, 16420, 16452, 16424, 16456, 16426, 16458, 16425, 8233, 8203, 8235, 8267, 16397, 16427, 16459, 8268, 8236, 16460, 16428, 8238, 8270, 16430, 16462};

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }
        for (short durability : shorts) {
            if (item.getDurability() == durability) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSplash(PotionSplashEvent event) {
        ItemStack item = event.getPotion().getItem();
        if (item == null) {
            return;
        }
        for (short durability : shorts) {
            if (item.getDurability() == durability) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerSpashPotion(PotionSplashEvent event) {
        for (Integer id : ElowyrBasics.getInstance().getConfig().getIntegerList("blocked.potions")) {
            if (event.getPotion().getItem().getDurability() == id) {
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    Lang.send(player, "blocked.potions");
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerSplashPotion(PotionSplashEvent event) {
        for (Integer id : ElowyrBasics.getInstance().getConfig().getIntegerList("blocked.potions")) {
            if (event.getPotion().getItem().getDurability() == id) {
                if (event.getEntity() instanceof Player) {
                    Player player = (Player)event.getEntity();
                    Lang.send(player, "blocked.potions");
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerConsumePotion(PlayerItemConsumeEvent event) {
        for (Integer id : ElowyrBasics.getInstance().getConfig().getIntegerList("blocked.potions")) {
            if (event.getItem().getItemMeta() instanceof org.bukkit.inventory.meta.PotionMeta &&
                    event.getItem().getDurability() == id) {
                event.setCancelled(true);
                Lang.send(event.getPlayer(), "blocked.potions");
            }
        }
    }
}
