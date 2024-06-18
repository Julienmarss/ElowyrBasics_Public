package fr.elowyr.basics.tasks;

import fr.elowyr.basics.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import java.util.TimerTask;

public class ClearLagTask extends TimerTask {

    private int timer = 1000;

    @Override
    public void run() {

        switch (timer) {
            case 120:
                Lang.broadcast("clearlag.120-seconds");
                break;
            case 60:
                Lang.broadcast("clearlag.60-seconds");
                break;
            case 30:
                Lang.broadcast("clearlag.30-seconds");
                break;
            case 15:
                Lang.broadcast("clearlag.15-seconds");
                break;
            case 5:
                Lang.broadcast("clearlag.5-seconds");
                break;
        }

        if (this.timer == 0) {
            Bukkit.getServer().getWorlds().forEach((world) -> {
                world.getEntities().forEach((entity) -> {
                    if (entity instanceof Item) {
                        entity.remove();
                    } else if (entity instanceof FallingBlock) {
                        entity.remove();
                    } else if (entity instanceof Boat) {
                        if (entity.isEmpty()) {
                            entity.remove();
                        }
                    } else if (entity instanceof Projectile) {
                        entity.remove();
                    } else if (entity instanceof TNTPrimed) {
                        entity.remove();
                    } else if (entity instanceof ExperienceOrb) {
                        entity.remove();
                    } else if (entity instanceof Creature) {
                        entity.remove();
                    } else if (entity instanceof Animals) {
                        entity.remove();
                    }
                });
            });
            Lang.broadcast("clearlag.clear");
            this.timer = 1000;
        }
        this.timer--;
    }
}
