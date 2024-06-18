package fr.elowyr.basics.factions;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.event.FactionCreateEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.factions.data.FactionData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FactionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if(!FPlayers.getInstance().getByPlayer(player).hasFaction()) return;
        Faction faction = FPlayers.getInstance().getByPlayer(player).getFaction();
        FactionData factionData = FactionManager.getInstance().getProvider().get(faction.getId());
        if(factionData != null) return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(ElowyrBasics.getInstance(), () -> {
            FactionData factionData1 = new FactionData(faction);
            FactionManager.getInstance().getProvider().provide(faction.getId(), factionData1);
            FactionManager.getInstance().getProvider().write(factionData1);
        }, 10L);
    }

    @EventHandler
    public void onFactionCreate(FactionCreateEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(ElowyrBasics.getInstance(), () -> {
            FactionData factionData = new FactionData(event.getFPlayer().getFaction());
            FactionManager.getInstance().getProvider().provide(event.getFPlayer().getFactionId(), factionData);
            FactionManager.getInstance().getProvider().write(factionData);
        }, 10L);
    }

    @EventHandler
    public void onFactionDelete(FactionDisbandEvent event) {
        String id = event.getFaction().getId();
        Bukkit.getScheduler().scheduleAsyncDelayedTask(ElowyrBasics.getInstance(), () -> {
            FactionManager.getInstance().getProvider().remove(id);
        }, 10L);
    }
}
