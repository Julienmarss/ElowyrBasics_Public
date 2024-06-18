package fr.elowyr.basics.mobstacker.listeners;

import fr.elowyr.basics.mobstacker.MobStackerManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class MobStackerListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity spawnedMob = event.getEntity();

        if (MobStackerManager.getInstance().isStackableMob(spawnedMob)) {
            List<Entity> nearbyEntities = spawnedMob.getNearbyEntities(5, 5, 5);
            for (Entity entity : nearbyEntities) {
                if (entity.getType() == spawnedMob.getType() && entity instanceof LivingEntity) {
                    LivingEntity baseMob = (LivingEntity) entity;

                    MobStackerManager.getInstance().stackMobs(baseMob, spawnedMob);
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity deadMob = event.getEntity();

        if (MobStackerManager.getInstance().isStackableMob(deadMob)) {

            int currentCount = MobStackerManager.getInstance().getMobStacks().getOrDefault(deadMob, 1);
            int newCount = currentCount - 1;
            System.out.println(deadMob);
            System.out.println(newCount);

            if (newCount > 0) {
                System.out.println("yi");

                LivingEntity newMob = (LivingEntity) deadMob.getWorld().spawnEntity(deadMob.getLocation(), deadMob.getType());
                int stackCount = MobStackerManager.getInstance().getMobStacks().getOrDefault(deadMob, 1);
                newMob.setCustomName("§e§l" + stackCount + "x §6§l" + deadMob.getName());
                newMob.setCustomNameVisible(true);
                MobStackerManager.getInstance().getMobStacks().put(newMob, newCount);
            } else {
                MobStackerManager.getInstance().getMobStacks().remove(deadMob);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity attackedEntity = event.getEntity();

        if (attackedEntity instanceof LivingEntity) {
            LivingEntity attackedMob = (LivingEntity) attackedEntity;
            if (MobStackerManager.getInstance().getMobStacks().containsKey(attackedMob)) {
                attackedMob.setVelocity(new Vector(0, 0, 0));
                System.out.println(attackedMob.getVelocity());
            }
        }
    }
}
