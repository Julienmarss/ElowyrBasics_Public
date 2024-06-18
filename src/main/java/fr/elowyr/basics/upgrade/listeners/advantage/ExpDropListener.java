package fr.elowyr.basics.upgrade.listeners.advantage;

import com.massivecraft.factions.*;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class ExpDropListener implements Listener {

    @EventHandler
    public void onDropExp(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        Faction faction = Factions.getInstance().getFactionById(fPlayer.getFactionId());
        FLocation fLocation = new FLocation(player.getLocation());
        FactionData factionData = FactionManager.getInstance().getProvider().get(faction.getId());
        Faction board = Board.getInstance().getFactionAt(fLocation);
        if (factionData == null) return;
        if (board == null) return;
        if (Board.getInstance().getFactionAt(fLocation).equals(faction)) {
            double multiplicator = 0.0;

            if (factionData.getUpgradeLevel() >= 4) {
                multiplicator = 1.5;
            } else if (factionData.getUpgradeLevel() >= 7) {
                multiplicator = 2.0;
            } else if (factionData.getUpgradeLevel() >= 8) {
                multiplicator = 2.5;
            }

            player.setExp(player.getExp() + (float) (event.getAmount() * multiplicator));
        }
    }
}
