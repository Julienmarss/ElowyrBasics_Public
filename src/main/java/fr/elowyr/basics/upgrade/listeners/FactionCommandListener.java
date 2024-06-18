package fr.elowyr.basics.upgrade.listeners;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.upgrade.UpgradeManager;
import fr.elowyr.basics.upgrade.data.Upgrade;
import fr.elowyr.basics.upgrade.gui.UpgradeGUI;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class FactionCommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);

        if (message.equalsIgnoreCase("/f upgrade") || message.equalsIgnoreCase("/f prestige")) {
            event.setCancelled(true);

            FactionData factionData = FactionManager.getInstance().getProvider().get(FPlayers.getInstance().getByPlayer(player).getFactionId());

            if (fPlayer.getFaction().isWilderness() || factionData == null) {
                player.sendMessage(Utils.color(UpgradeManager.getInstance().getUpgradeConfig().getString("PLAYER_NOT_HAVE_FACTION")));
                return;
            }

            if (factionData.getUpgrades().isEmpty()) {
                List<Upgrade> upgrades = new ArrayList<>(UpgradeManager.getInstance().getUpgradesByLevel(1));
                factionData.setUpgradeLevel(1);
                factionData.setUpgrades(upgrades);
                FactionManager.getInstance().getProvider().write(factionData);
            }

            UpgradeGUI gui = new UpgradeGUI(1, player, factionData);
            gui.open(player);
        }
    }
}
