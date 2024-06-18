package fr.elowyr.basics.mobstacker;

import fr.elowyr.basics.mobstacker.listeners.MobStackerListener;
import fr.elowyr.basics.module.Module;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class MobStackerManager extends Module {
    private static MobStackerManager instance;

    private Map<LivingEntity, Integer> mobStacks = new HashMap<>();

    public MobStackerManager() {
        super("MobStacker");
        instance = this;

        registerListener(new MobStackerListener());
    }

    public boolean isStackableMob(LivingEntity entity) {
        return entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.SKELETON
                || entity.getType() == EntityType.COW || entity.getType() == EntityType.PIG_ZOMBIE
                || entity.getType() == EntityType.IRON_GOLEM || entity.getType() == EntityType.SPIDER ||
                entity.getType() == EntityType.CAVE_SPIDER;
    }

    public void stackMobs(LivingEntity baseMob, LivingEntity spawnedMob) {

        int currentCount = mobStacks.getOrDefault(baseMob, 1);

        baseMob.setCustomName(null);
        mobStacks.put(baseMob, currentCount + 1);

        baseMob.setCustomName("§e§l" + mobStacks.get(baseMob) + "x §6§l" + baseMob.getName());
        baseMob.setCustomNameVisible(true);

        spawnedMob.remove();
    }

    public static MobStackerManager getInstance() {
        return instance;
    }

    public Map<LivingEntity, Integer> getMobStacks() {
        return mobStacks;
    }
}
