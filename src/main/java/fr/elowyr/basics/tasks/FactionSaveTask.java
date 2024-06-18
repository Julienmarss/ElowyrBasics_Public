package fr.elowyr.basics.tasks;

import fr.elowyr.basics.factions.FactionManager;
import org.bukkit.scheduler.BukkitRunnable;

public class FactionSaveTask extends BukkitRunnable {

    @Override
    public void run() {
        FactionManager.getInstance().getProvider().write();
    }
}
