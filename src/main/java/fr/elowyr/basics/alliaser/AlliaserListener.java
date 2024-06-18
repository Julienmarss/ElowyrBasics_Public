package fr.elowyr.basics.alliaser;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.Utils;
import net.minelink.ctplus.CombatTagPlus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class AlliaserListener implements Listener {
    private final List<Alliaser> alliasers;

    private final CombatTagPlus combatTagPlus = (CombatTagPlus) ElowyrBasics.getInstance().getServer().getPluginManager().getPlugin("CombatTagPlus");

    public AlliaserListener(List<Alliaser> alliasers) {
        this.alliasers = alliasers;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage().toLowerCase();
        String[] command = msg.split(" ");
        this.alliasers.stream().filter(s -> {
            if (!AlliaserManager.getInstance().isActive()) {
                ModuleManager.moduleDesactivate(event.getPlayer(), AlliaserManager.getInstance());
                return false;
            }
            int i = 0;
            for (String string : s.getFrom().split(" ")) {
                if (i >= command.length || !string.equalsIgnoreCase(command[i]))
                    return false;
                i++;
            }
            return true;
        }).findFirst().ifPresent(alliaser -> {
            if (combatTagPlus.getTagManager().isTagged(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Utils.color("&4Combat &7◆ &cCette commande est désactivée en combat !"));
                return;
            }
            event.setCancelled(true);
            event.getPlayer().performCommand(alliaser.getTo());
        });
    }
}
