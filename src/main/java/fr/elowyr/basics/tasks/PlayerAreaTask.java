package fr.elowyr.basics.tasks;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.utils.Cuboid;
import fr.elowyr.basics.utils.RegionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerAreaTask implements Runnable {

    private ArrayList<Player> players = new ArrayList<>();
    private Location location = new Location(Bukkit.getWorld("world"), -89.5, 70, -307.5);
    private boolean isEnableWall;
    Cuboid wall;

    public PlayerAreaTask() {
        Location loc1 = new Location(Bukkit.getWorld("world"), -98.5, 70, -314.5);
        Location loc2 = new Location(Bukkit.getWorld("world"), -88.5, 74, -314.5);
        wall = new Cuboid(loc1, loc2);
        this.isEnableWall = false;
    }

    @Override
    public void run() {

        if (players.size() < 2) {
            return;
        }

        if (RegionUtils.getPlayerCountInRegion("pvp1v1") >= 2) {
            spawnGlass();
            isEnableWall = true;
        } else {
            breakGlass();
            isEnableWall = false;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {

            if (players.size() == 2) {
                Faction firstPlayer = FPlayers.getInstance().getByPlayer(players.get(0)).getFaction();
                Faction secondPlayer = FPlayers.getInstance().getByPlayer(players.get(1)).getFaction();

                if (firstPlayer == secondPlayer) {
                    players.get(0).teleport(location);
                    players.get(1).teleport(location);
                }
            }

            if (RegionUtils.inArea(player.getLocation(), player.getWorld(), "pvp1v1")) {
                if (!players.contains(player) && (RegionUtils.getPlayerCountInRegion("pvp1v1") < 2 || RegionUtils.getPlayerCountInRegion("pvp1v1") == 2)) {
                    players.add(player);
                    continue;
                }

                if (!players.contains(player) && RegionUtils.getPlayerCountInRegion("pvp1v1") > 2 && !player.hasPermission("elowyrbasics.fightzone.bypass")) {
                    player.teleport(location);
                }
            } else {
                players.remove(player);
            }
        }
    }

    public void spawnGlass() {
        Bukkit.getScheduler().runTask(ElowyrBasics.getInstance(), () -> wall.blockList().stream().filter(block -> block.getType() == Material.AIR).forEach(block -> {
            block.setType(Material.STAINED_GLASS);
            block.setData((byte) 8);
        }));
    }

    public void breakGlass() {
        Bukkit.getScheduler().runTaskLater(ElowyrBasics.getInstance(), () -> {
            wall.blockList().stream().filter(block -> block.getType() == Material.STAINED_GLASS)
                    .forEach(block -> block.setType(Material.AIR));
        }, 45 * 20);
        isEnableWall = false;
    }
}
