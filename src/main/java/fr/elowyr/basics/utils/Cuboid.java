package fr.elowyr.basics.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cuboid {

    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;
    private final int zMin;
    private final int zMax;
    private final World world;

    public Cuboid(final Location point1, final Location point2) {
        this.xMin = Math.min(point1.getBlockX(), point2.getBlockX());
        this.xMax = Math.max(point1.getBlockX(), point2.getBlockX());
        this.yMin = Math.min(point1.getBlockY(), point2.getBlockY());
        this.yMax = Math.max(point1.getBlockY(), point2.getBlockY());
        this.zMin = Math.min(point1.getBlockZ(), point2.getBlockZ());
        this.zMax = Math.max(point1.getBlockZ(), point2.getBlockZ());
        this.world = point1.getWorld();
    }

    public List<Block> getWalls() {
        List<Block> blocks = new ArrayList<>();

        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                blocks.add(world.getBlockAt(x, y, zMin));
                blocks.add(world.getBlockAt(x, y, zMax));
            }
        }
        for (int y = yMin; y <= yMax; y++) {
            for (int z = zMin; z <= zMax; z++) {
                blocks.add(world.getBlockAt(xMin, y, z));
                blocks.add(world.getBlockAt(xMax, y, z));
            }
        }
        return blocks;
    }

    public List<Block> getMaterialWalls(Material material) {
        return getWalls().stream().filter(block -> block.getType() == material).collect(Collectors.toList());
    }

    public boolean isIn(final Location location) {
        return location.getWorld().equals(this.world)
                && location.getBlockX() >= this.xMin && location.getBlockX() <= this.xMax
                && location.getBlockZ() >= this.zMin && location.getBlockZ() <= this.zMax
                && location.getBlockY() >= this.yMin && location.getBlockY() <= this.yMax;
    }

    public List<Block> blockList() {
        List<Block> bL = new ArrayList<>(this.getTotalBlockSize());
        for (int x = this.xMin; x <= this.xMax; ++x) {
            for (int y = this.yMin; y <= this.yMax; ++y) {
                for (int z = this.zMin; z <= this.zMax; ++z) {
                    Block b = this.world.getBlockAt(x, y, z);
                    bL.add(b);
                }
            }
        }
        return bL;
    }

    public int getTotalBlockSize() {
        return this.getHeight() * this.getXWidth() * this.getZWidth();
    }

    public int getHeight() {
        return this.yMax - this.yMin + 1;
    }

    public int getXWidth() {
        return this.xMax - this.xMin + 1;
    }

    public int getZWidth() {
        return this.zMax - this.zMin + 1;
    }

    public List<Player> getPlayers() {
        return world.getPlayers().stream().filter(this::isIn).filter(player -> !player.isDead()).collect(Collectors.toList());
    }

    public boolean isIn(final Player player) {
        return this.isIn(player.getLocation());
    }

    public World getWorld() {
        return world;
    }

    public int getxMin() {
        return xMin;
    }

    public int getzMax() {
        return zMax;
    }

    public int getyMax() {
        return yMax;
    }

    public int getyMin() {
        return yMin;
    }

    public int getxMax() {
        return xMax;
    }

    public int getzMin() {
        return zMin;
    }

    /**
     * Get the the centre {@link Location} of this {@link Cuboid}
     *
     * @return the {@link Location} at the centre
     */
    public Location getCenter() {
        int x1 = this.xMax + 1;
        int y1 = this.yMax + 1;
        int z1 = this.zMax + 1;
        return new Location(this.getWorld(),
                this.xMin + (x1 - this.xMin) / 2.0,
                this.yMin + (y1 - this.yMin) / 2.0,
                this.zMin + (z1 - this.zMin) / 2.0);
    }
}

