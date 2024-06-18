package fr.elowyr.basics.upgrade.listeners.advantage;

import com.massivecraft.factions.*;
import com.massivecraft.factions.perms.Relation;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.utils.Utils;
import net.ess3.api.events.UserTeleportHomeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class HomeListener implements Listener {

    @EventHandler
    public void onHome(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        FLocation fLocation = new FLocation(player.getLocation());
        Faction faction = Board.getInstance().getFactionAt(fLocation);
        FactionData factionData = FactionManager.getInstance().getProvider().get(faction.getId());
        Faction board = Board.getInstance().getFactionAt(fLocation);
        if (factionData == null) return;
        if (board == null) return;
        String command = event.getMessage().toLowerCase();
        if (player.hasPermission("elowyrbasics.upgrade.bypass")) return;
        if (!command.startsWith("/sethome") && !command.startsWith("/esethome") && !command.startsWith("/createhome") && !command.startsWith("/ecreatehome")) {
            return;
        }
        if (Board.getInstance().getFactionAt(fLocation).equals(faction)) {
            if (factionData.getUpgradeLevel() == 8) {
                final Faction playerFaction;
                if (faction.getRelationWish(playerFaction = FPlayers.getInstance().getByPlayer(player).getFaction()) == Relation.ENEMY && !playerFaction.getId().equals(faction.getId())) {
                    event.setCancelled(true);
                    player.sendMessage(Utils.color("&6&lFaction &7◆ &fLa faction &aposséde &fl'amélioration &eAnti-Home&f !"));
                }
            }
        }
    }

    @EventHandler
    public void onHomeTeleport(UserTeleportHomeEvent event) {
        Player player = event.getUser().getBase();
        if (player.hasPermission("elowyrbasics.upgrade.bypass")) return;
        FLocation fLocation = new FLocation(event.getHomeLocation());
        Faction faction = Board.getInstance().getFactionAt(fLocation);
        FactionData factionData = FactionManager.getInstance().getProvider().get(faction.getId());
        Faction board = Board.getInstance().getFactionAt(fLocation);
        if (factionData == null) return;
        if (board == null) return;
        if (Board.getInstance().getFactionAt(fLocation).equals(faction)) {
            if (factionData.getUpgradeLevel() == 8) {
                final Faction playerFaction;
                if (faction.getRelationWish(playerFaction = FPlayers.getInstance().getByPlayer(player).getFaction()) == Relation.ENEMY && !playerFaction.getId().equals(faction.getId())) {
                    event.setCancelled(true);
                    player.sendMessage(Utils.color("&6&lFaction &7◆ &fLa faction &aposséde &fl'amélioration &eAnti-Home&f !"));
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("elowyrbasics.upgrade.bypass")) return;
        FLocation fLocation = new FLocation(player.getLocation());
        Faction faction = Board.getInstance().getFactionAt(fLocation);
        FactionData factionData = FactionManager.getInstance().getProvider().get(faction.getId());
        Faction board = Board.getInstance().getFactionAt(fLocation);
        if (factionData == null) return;
        if (board == null) return;
        if (Board.getInstance().getFactionAt(fLocation).equals(faction)) {
            if (factionData.getUpgradeLevel() == 8) {
                final Faction playerFaction;
                if (faction.getRelationWish(playerFaction = FPlayers.getInstance().getByPlayer(player).getFaction()) == Relation.ENEMY && !playerFaction.getId().equals(faction.getId())) {
                    player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                    player.sendMessage(Utils.color("&6&lFaction &7◆ &fLa faction &aposséde &fl'amélioration &eAnti-Home&f ! &7(" + faction.getTag() + ")"));
                }
            }
        }
    }
}
