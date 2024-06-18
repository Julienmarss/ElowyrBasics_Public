package fr.elowyr.basics.tasks;

import fr.elowyr.basics.mod.ModManager;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishTask implements Runnable {

    @Override
    public void run() {
        if (!ModManager.getInstance().isActive()) return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!ModManager.getInstance().isStaff(player)) return;
            ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
            if (profileData == null) return;

            if (profileData.isVanish()) {
                BukkitUtils.sendActionBar(player, "§a§lVous êtes invisible");
                profileData.changeVanish(player, true);
            } else {
                BukkitUtils.sendActionBar(player, "§c§lVous êtes visible");
            }
        }
    }
}
