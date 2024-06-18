package fr.elowyr.basics.assets;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.lang.Lang;
import fr.elowyr.basics.module.Module;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AssetManager extends Module {
    private static AssetManager instance;

    private Map<UUID, Boolean> noFall;
    private Map<UUID, Boolean> noFood;
    private Map<UUID, Boolean> noRod;
    private Map<UUID, Boolean> antiCleanUP;

    public AssetManager() {
        super("Atouts");
        instance = this;
        registerListener(new AssetListener());

        this.noFall = new HashMap<>();
        this.noFood = new HashMap<>();
        this.noRod = new HashMap<>();
        this.antiCleanUP = new HashMap<>();

        System.out.println("[Module] Atouts Loaded");
    }

    public void enableNoRod(Player player) {
        if (this.noRod.containsKey(player.getUniqueId()) && this.noRod.get(player.getUniqueId())) {
            Lang.send(player, "assets.already");
        } else {
            String assetName = Lang.get().getString("assets.names.noRod");
            Lang.send(player, "assets.enable", "asset", assetName);
            this.noRod.put(player.getUniqueId(), true);
        }
    }

    public void disableNoRod(Player player) {
        if (this.noRod.containsKey(player.getUniqueId()) && this.noRod.get(player.getUniqueId())) {
            String assetName = Lang.get().getString("assets.names.noRod");
            Lang.send(player, "assets.disable", "asset", assetName);
            this.noRod.replace(player.getUniqueId(), true, false);
        } else {
            Lang.send(player, "assets.notEnable");
        }
    }

    public boolean hasNoRod(Player player) {
        if (this.noRod.containsKey(player.getUniqueId()))
            return this.noRod.get(player.getUniqueId());
        return false;
    }

    public void enableNoFall(Player player) {
        if (this.noFall.containsKey(player.getUniqueId()) && this.noFall.get(player.getUniqueId())) {
            Lang.send(player, "assets.already");
        } else {
            String assetName = Lang.get().getString("assets.names.noFall");
            Lang.send(player, "assets.enable", "asset", assetName);
            this.noFall.put(player.getUniqueId(), true);
        }
    }

    public void disableNoFall(Player player) {
        if (this.noFall.containsKey(player.getUniqueId()) && this.noFall.get(player.getUniqueId())) {
            String assetName = Lang.get().getString("assets.names.noFall");
            Lang.send(player, "assets.disable", "asset", assetName);
            this.noFall.replace(player.getUniqueId(), true, false);
        } else {
            Lang.send(player, "assets.notEnable");
        }
    }

    public boolean hasNoFall(Player player) {
        if (this.noFall.containsKey(player.getUniqueId()))
            return this.noFall.get(player.getUniqueId());
        return false;
    }

    public void enableAntiCleanUP(Player player) {
        if (this.antiCleanUP.containsKey(player.getUniqueId()) && this.antiCleanUP.get(player.getUniqueId())) {
            Lang.send(player, "assets.already");
        } else {
            String assetName = Lang.get().getString("assets.names.antiCleanUP");
            Lang.send(player, "assets.enable", "asset", assetName);
            this.antiCleanUP.put(player.getUniqueId(), true);
        }
    }

    public void disableAntiCleanUP(Player player) {
        if (this.antiCleanUP.containsKey(player.getUniqueId()) && this.antiCleanUP.get(player.getUniqueId())) {
            String assetName = Lang.get().getString("assets.names.antiCleanUP");
            Lang.send(player, "assets.disable", "asset", assetName);
            this.antiCleanUP.replace(player.getUniqueId(), true, false);
        } else {
            Lang.send(player, "assets.notEnable");
        }
    }

    public boolean hasAntiCleanUP(Player player) {
        if (this.antiCleanUP.containsKey(player.getUniqueId()))
            return this.antiCleanUP.get(player.getUniqueId());
        return false;
    }

    public void enableNoFood(Player player) {
        if (this.noFood.containsKey(player.getUniqueId()) && this.noFood.get(player.getUniqueId())) {
            Lang.send(player, "assets.already");
        } else {
            String assetName = Lang.get().getString("assets.names.noFood");
            Lang.send(player, "assets.enable", "asset", assetName);
            this.noFood.put(player.getUniqueId(), true);
        }
    }

    public void disableNoFood(Player player) {
        if (this.noFood.containsKey(player.getUniqueId()) && this.noFood.get(player.getUniqueId())) {
            String assetName = Lang.get().getString("assets.names.noFood");
            Lang.send(player, "assets.disable", "asset", assetName);
            this.noFood.replace(player.getUniqueId(), true, false);
        } else {
            Lang.send(player, "assets.notEnable");
        }
    }

    public boolean hasNoFood(Player player) {
        if (this.noFood.containsKey(player.getUniqueId()))
            return this.noFood.get(player.getUniqueId());
        return false;
    }

    public void enableAsset(Player player, PotionEffectType type, int level, String name) {
        PotionEffect potionEffect = new PotionEffect(type, 52857285, level);
        if (player.hasPotionEffect(potionEffect.getType())) {
            Lang.send(player, "assets.already");
        } else {
            String assetName = Lang.get().getString("assets.names." + name);
            Lang.send(player, "assets.enable", "asset", assetName);
            player.addPotionEffect(potionEffect);
        }
    }

    public void disableAsset(Player player, PotionEffectType type, int level, String name) {
        PotionEffect potionEffect = new PotionEffect(type, 5895252, level);
        if (player.hasPotionEffect(potionEffect.getType())) {
            String assetName = Lang.get().getString("assets.names." + name);
            Lang.send(player, "assets.disable", "asset", assetName);
            player.removePotionEffect(potionEffect.getType());
        } else {
            Lang.send(player, "assets.notEnable");
        }
    }

    public static AssetManager getInstance() {
        return instance;
    }

    public static void setInstance(AssetManager instance) {
        AssetManager.instance = instance;
    }

    public Map<UUID, Boolean> getNoFall() {
        return noFall;
    }

    public void setNoFall(Map<UUID, Boolean> noFall) {
        this.noFall = noFall;
    }

    public Map<UUID, Boolean> getNoFood() {
        return noFood;
    }

    public void setNoFood(Map<UUID, Boolean> noFood) {
        this.noFood = noFood;
    }

    public Map<UUID, Boolean> getNoRod() {
        return noRod;
    }

    public void setNoRod(Map<UUID, Boolean> noRod) {
        this.noRod = noRod;
    }

    public Map<UUID, Boolean> getAntiCleanUP() {
        return antiCleanUP;
    }

    public void setAntiCleanUP(Map<UUID, Boolean> antiCleanUP) {
        this.antiCleanUP = antiCleanUP;
    }
}
