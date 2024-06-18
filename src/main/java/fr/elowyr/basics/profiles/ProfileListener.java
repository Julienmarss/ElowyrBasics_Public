package fr.elowyr.basics.profiles;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.DurationFormatter;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ProfileListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
        if(profileData != null) {
            if (profileData.isBlacklist()) {
                player.kickPlayer(Utils.color("&6&lServeur &7◆ &fVous êtes &cblacklist&f du serveur."));
                return;
            }
            player.sendMessage(Utils.color(""));
            player.sendMessage(Utils.color("&fBienvenue sur &6Elowyr&f Version &61"));
            player.sendMessage(Utils.color(""));
            player.sendMessage(Utils.color("&7◆ &fJoueurs en ligne: &e" + Bukkit.getServer().getOnlinePlayers().size()));
            player.sendMessage(Utils.color("&7◆ &fPremière connexion: &e" + DurationFormatter.getDurationDate(profileData.getDateJoinFaction())));
            player.sendMessage(Utils.color(""));
            player.sendMessage(Utils.color("&7◆ &eSite: &bhttps://elowyr.fr"));
            player.sendMessage(Utils.color("&7◆ &9Discord: &bhttps://discord.gg/elowyr"));
            player.sendMessage(Utils.color("&7◆ &2Vote: &bhttps://elowyr.fr/vote"));
            player.sendMessage(Utils.color(""));
            return;
        };
        if (ProfileManager.getInstance().isBlacklistUsername(player.getName())) {
            player.kickPlayer(Utils.color("&fImpossible de &crejoindre&f Elowyr avec ce pseudo."));
            return;
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(ElowyrBasics.getInstance(), () -> {
            ProfileData newProfileData = new ProfileData(player);
            ProfileManager.getInstance().getProvider().provide(player.getUniqueId().toString(), newProfileData);
            ProfileManager.getInstance().getProvider().write(newProfileData);
        }, 10L);
    }
}
