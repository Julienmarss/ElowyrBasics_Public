package fr.elowyr.basics.mod.commands;

import fr.elowyr.basics.mod.ModManager;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.BukkitUtils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class VanishCommand {

    private ModManager modManager = ModManager.getInstance();

    @Command(name = "vanish", aliases = {"v", "elovanish"}, permission = "elowyrbasics.mod.vanish")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!modManager.isActive()) {
            ModuleManager.moduleDesactivate(player, modManager);
            return;
        }

        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
        if (profileData == null) return;

        if (modManager.getVanished().contains(player.getUniqueId())) {
            profileData.changeVanish(player, false);
            BukkitUtils.sendActionBar(player, modManager.getStaffConfig().getString("MESSAGES.VANISH.DISABLED"));
        } else {
            profileData.changeVanish(player, true);
            BukkitUtils.sendActionBar(player, modManager.getStaffConfig().getString("MESSAGES.VANISH.ENABLED"));
        }
    }
}
