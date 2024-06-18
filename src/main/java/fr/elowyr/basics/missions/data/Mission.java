package fr.elowyr.basics.missions.data;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class Mission {

    private final int id;
    private final String description;
    private final MissionType missionType;
    private final EntityType entityType;
    private Material material;
    private int progress;
    private final int objective;
    private final int rewards;
    private boolean finish;

    public Mission(int id, String description, MissionType missionType, Material material, EntityType entityType, int progress, int objective, int rewards, boolean finish) {
        this.id = id;
        this.description = description;
        this.missionType = missionType;
        this.material = material;
        this.entityType = entityType;
        this.progress = progress;
        this.objective = objective;
        this.rewards = rewards;
        this.finish = finish;
    }

    public void incrementProgress(int i) {
        if (this.getProgress() + i > this.getObjective())
            this.setProgress(this.getObjective());
        else
            this.setProgress(this.getProgress() + i);
    }

    @Override
    public Mission clone() {
        return new Mission(this.getId(), this.getDescription(), this.getMissionType(), this.getMaterial(), this.getEntityType(), this.getProgress(), this.getObjective(), this.getRewards(), this.isFinish());
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public MissionType getMissionType() {
        return missionType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getObjective() {
        return objective;
    }

    public int getRewards() {
        return rewards;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}
