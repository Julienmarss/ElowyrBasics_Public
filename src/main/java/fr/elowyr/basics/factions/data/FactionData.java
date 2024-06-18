package fr.elowyr.basics.factions.data;

import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import fr.elowyr.basics.missions.data.Mission;
import fr.elowyr.basics.upgrade.data.Upgrade;

import java.util.List;

public class FactionData {

    private String factionId;
    private String factionName;
    private int upgradeLevel;
    private List<Upgrade> upgrades;
    private List<Mission> missions;
    private List<Integer> missionIdFinish;
    private List<Integer> missionIdProgress;
    private FactionChest chest;

    public FactionData(Faction faction) {
        this.factionId = faction.getId();
        this.factionName = faction.getTag();
        this.upgradeLevel = 1;
        this.upgrades = Lists.newArrayList();
        this.missions = Lists.newArrayList();
        this.chest = new FactionChest();
        this.missionIdFinish = Lists.newArrayList();
        this.missionIdProgress = Lists.newArrayList();
    }

    public Mission getMissionById(int id) {
        return this.getMissions().stream().filter(mission -> mission.getId() == id).findFirst().orElse(null);
    }

    public String getFactionId() {
        return factionId;
    }

    public void setFactionId(String factionId) {
        this.factionId = factionId;
    }

    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    public List<Integer> getMissionIdFinish() {
        return missionIdFinish;
    }

    public void setMissionIdFinish(List<Integer> missionIdFinish) {
        this.missionIdFinish = missionIdFinish;
    }

    public List<Integer> getMissionIdProgress() {
        return missionIdProgress;
    }

    public void setMissionIdProgress(List<Integer> missionIdProgress) {
        this.missionIdProgress = missionIdProgress;
    }

    public FactionChest getChest() {
        return chest;
    }

    public void setChest(FactionChest chest) {
        this.chest = chest;
    }

    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public List<Upgrade> getUpgrades() {
        return upgrades;
    }

    public void setUpgradeLevel(int upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }

    public void setUpgrades(List<Upgrade> upgrades) {
        this.upgrades = upgrades;
    }

    public Faction toFaction() {
        return Factions.getInstance().getByTag(this.factionName);
    }
}
