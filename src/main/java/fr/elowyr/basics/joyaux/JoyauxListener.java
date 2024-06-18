package fr.elowyr.basics.joyaux;

import com.massivecraft.factions.event.LandClaimEvent;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.BukkitUtils;
import fr.elowyr.basics.utils.RegionUtils;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class JoyauxListener implements Listener {

    private JoyauxManager joyauxManager = JoyauxManager.getInstance();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        Player player = event.getEntity().getKiller();
        if (event.getEntity().getKiller() == null) return;
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
        int drop = Utils.randomInt(0, 100);
        if (drop <= 50) {
            if (entity.getType() == EntityType.ZOMBIE || entity instanceof Skeleton || entity instanceof Spider || entity instanceof Blaze) {
                int joyaux = 2;
                if (event.getEntity().getKiller().getWorld().getName().equalsIgnoreCase("dimension")) {
                    profileData.setJoyaux(profileData.getJoyaux() + (joyaux * 2));
                    BukkitUtils.sendActionBar(player, Utils.color(joyauxManager.getConfig().getString("MESSAGES.WIN").replace("%joyaux%", String.valueOf(joyaux * 2))));
                    return;
                }
                profileData.setJoyaux(profileData.getJoyaux() + joyaux);
                BukkitUtils.sendActionBar(player, Utils.color(joyauxManager.getConfig().getString("MESSAGES.WIN").replace("%joyaux%", String.valueOf(joyaux))));
            } else if (entity.getType() == EntityType.PIG_ZOMBIE || entity instanceof Enderman || entity instanceof Creeper || entity instanceof MagmaCube) {
                int joyaux = 3;
                if (event.getEntity().getKiller().getWorld().getName().equalsIgnoreCase("dimension")) {
                    profileData.setJoyaux(profileData.getJoyaux() + (joyaux * 2));
                    BukkitUtils.sendActionBar(player, Utils.color(joyauxManager.getConfig().getString("MESSAGES.WIN").replace("%joyaux%", String.valueOf(joyaux * 2))));
                    return;
                }
                profileData.setJoyaux(profileData.getJoyaux() + joyaux);
                BukkitUtils.sendActionBar(player, Utils.color(joyauxManager.getConfig().getString("MESSAGES.WIN").replace("%joyaux%", String.valueOf(joyaux))));
            }
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {

        ProfileData profileData = ProfileManager.getInstance().getProvider().get(event.getPlayer().getUniqueId().toString());
        if (!event.getPlayer().getWorld().getName().equalsIgnoreCase("dimension")) return;
        event.setCancelled(true);
        if (event.getBlock().getType() == Material.CROPS || event.getBlock().getType() == Material.CARROT || event.getBlock().getType() == Material.POTATO) {
            if (event.getBlock().getData() == 7) {
                event.getBlock().setData((byte) 0);
                int joyaux = Utils.randomInt(1, 3);
                int drop = Utils.randomInt(0, 100);
                if (drop <= 30) {
                    profileData.setJoyaux(profileData.getJoyaux() + joyaux);
                    BukkitUtils.sendActionBar(event.getPlayer(), Utils.color(joyauxManager.getConfig().getString("MESSAGES.WIN").replace("%joyaux%", String.valueOf(joyaux))));
                }
            }
        }
    }

    @EventHandler
    public void onClaim(LandClaimEvent event) {
        if (RegionUtils.inArea(event.getfPlayer().getPlayer().getLocation(), event.getfPlayer().getPlayer().getWorld(), "dimension")) {
            event.setCancelled(true);
            event.getfPlayer().sendMessage(Utils.color("&6&lDimension &7&l• &fVous ne &cpouvez &fpas claim cet endroit."));
        }
    }
}
