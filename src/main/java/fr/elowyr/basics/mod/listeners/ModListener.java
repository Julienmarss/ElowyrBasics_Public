package fr.elowyr.basics.mod.listeners;

import fr.elowyr.basics.mod.ModManager;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.Utils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ModListener implements Listener {

    private ModManager modManager = ModManager.getInstance();

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (!modManager.isActive()) {
            ModuleManager.moduleDesactivate(player, modManager);
            return;
        }

        if (event.getRightClicked() instanceof Player) {
            Player target = (Player) event.getRightClicked();

            if (player.hasPermission("elowyrbasics.mod") && player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {
                ItemStack item = player.getItemInHand();
                if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                    if (item.getType() == Material.BLAZE_ROD) {
                        modManager.setFrozen(player, target);
                    } else if (item.getType() == Material.BOOK) {
                        player.performCommand("invsee " + target.getName());
                        player.sendMessage(Utils.color(modManager.getStaffConfig().getString("MESSAGES.OPEN-INVENTORY").replace("%player%", target.getName())));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!modManager.isActive()) {
            ModuleManager.moduleDesactivate(player, modManager);
            return;
        }
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());

        if (player.hasPermission("elowyrbasics.mod") && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                && event.getItem() != null && event.getItem().getType() != Material.AIR) {
            ItemStack itemStack = event.getItem();
            boolean isVanished = profileData.isVanish();

            if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
                if (itemStack.getType() == Material.EYE_OF_ENDER) {
                    modManager.randomTeleport(player);
                    if (!isVanished) {
                        player.sendMessage(Utils.color(modManager.getStaffConfig().getString("MESSAGES.TELEPORT-VANISH")));
                    }
                    event.setCancelled(true);
                } else if (itemStack.getType() == Material.INK_SACK) {
                    profileData.changeVanish(player, !isVanished);
                    String display = !isVanished ? "§aVous êtes invisible" : "§cVous êtes visible";
                    int id = !isVanished ? 10 : 8;
                    player.setItemInHand(new ItemBuilder(Material.INK_SACK).setDurability((short) id).setName(display).toItemStack());
                } else if (itemStack.getType() == Material.SKULL_ITEM) {
                    Inventory inventory = Bukkit.createInventory(null, 27, Utils.color("&7Staff en Ligne"));
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (players.hasPermission("elowyrbasics.staff")) {
                            ItemStack staffItem = new ItemBuilder(Material.SKULL_ITEM, (short) 3).setSkullOwner(players.getName()).setName(players.getName())
                                    .setLore(
                                            "",
                                            " §fRank §7▶ " + PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%"),
                                            " §fTemps de Jeu §d" + PlaceholderAPI.setPlaceholders(player, "%statistic_time_played%"),
                                            ""
                                    ).toItemStack();
                            inventory.addItem(staffItem);
                        }
                        player.openInventory(inventory);
                    }
                } else if (itemStack.getType() == Material.REDSTONE) {
                    modManager.removeMod(player);
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equalsIgnoreCase(Utils.color("&7Staff en Ligne"))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!modManager.isActive()) {
            ModuleManager.moduleDesactivate(player, modManager);
            return;
        }
        if (modManager.isStaff(player)) {
            modManager.removeMod(player);
        }
        if (!player.hasPermission("elowyrbasics.mod") && player.getGameMode() == GameMode.CREATIVE) {
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
        }

        if (modManager.isFrozen(player)) {
            modManager.msgStaff(Utils.color(modManager.getStaffConfig().getString("MESSAGES.FREEZE-DECO").replace("%player%", player.getName())));
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!modManager.isActive()) {
            return;
        }

        if (modManager.isFrozen(player)) {
            event.setCancelled(true);
        }
    }
}
