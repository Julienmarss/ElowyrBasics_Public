package fr.elowyr.basics.assets.commands;

import fr.elowyr.basics.assets.AssetManager;
import fr.elowyr.basics.lang.Lang;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class AssetCommands {

    @Command(name = "asset", aliases = {"atout", "atouts"}, inGameOnly = true)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        if (!AssetManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, AssetManager.getInstance());
            return;
        }
        if (args.length() == 0 || args.getArgs(0).equalsIgnoreCase("help"))
            if (player.hasPermission("elowyrbasics.atouts.help")) {
                Lang.send(player, "assets.help");
            } else {
                Lang.send(player, "assets.no-permission");
            }
        if (args.length() == 2)
            if (args.getArgs(0).equalsIgnoreCase("enable")) {
                if (args.getArgs(1).equalsIgnoreCase("norod")) {
                    if (player.hasPermission("elowyrbasics.atouts.norod")) {
                        AssetManager.getInstance().enableNoRod(player);
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("aquatique")) {
                    if (player.hasPermission("elowyrbasics.atouts.aquatique")) {
                        AssetManager.getInstance().enableAsset(player, PotionEffectType.WATER_BREATHING, 1, "aquatique");
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("nofall")) {
                    if (player.hasPermission("elowyrbasics.atouts.nofall")) {
                        AssetManager.getInstance().enableNoFall(player);
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("speed")) {
                    if (player.hasPermission("elowyrbasics.atouts.speed")) {
                        AssetManager.getInstance().enableAsset(player, PotionEffectType.SPEED, 1, "speed");
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("force")) {
                    if (player.hasPermission("elowyrbasics.atouts.force")) {
                        AssetManager.getInstance().enableAsset(player, PotionEffectType.INCREASE_DAMAGE, 0, "force");
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("fireresi")) {
                    if (player.hasPermission("elowyrbasics.atouts.fireresi")) {
                        AssetManager.getInstance().enableAsset(player, PotionEffectType.FIRE_RESISTANCE, 0, "fireresi");
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("haste")) {
                    if (player.hasPermission("elowyrbasics.atouts.haste")) {
                        AssetManager.getInstance().enableAsset(player, PotionEffectType.FAST_DIGGING, 1, "haste");
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("noFood")) {
                    if (player.hasPermission("elowyrbasics.atouts.noFood")) {
                        AssetManager.getInstance().enableNoFood(player);
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("anticleanup")) {
                    if (player.hasPermission("elowyrbasics.atouts.anticleanup")) {
                        AssetManager.getInstance().enableAntiCleanUP(player);
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else {
                    Lang.send(player, "assets.noExist");
                }
            } else if (args.getArgs(0).equalsIgnoreCase("disable")) {
                if (args.getArgs(1).equalsIgnoreCase("norod")) {
                    if (player.hasPermission("elowyrbasics.atouts.norod")) {
                        AssetManager.getInstance().disableNoRod(player);
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("aquatique")) {
                    if (player.hasPermission("elowyrbasics.atouts.aquatique")) {
                        AssetManager.getInstance().disableAsset(player, PotionEffectType.WATER_BREATHING, 1, "aquatique");
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("noFall")) {
                    if (player.hasPermission("elowyrbasics.atouts.noFall")) {
                        AssetManager.getInstance().disableNoFall(player);
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("speed")) {
                    if (player.hasPermission("elowyrbasics.atouts.speed")) {
                        AssetManager.getInstance().disableAsset(player, PotionEffectType.SPEED, 1, "speed");
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("force")) {
                    if (player.hasPermission("elowyrbasics.atouts.force")) {
                        AssetManager.getInstance().disableAsset(player, PotionEffectType.INCREASE_DAMAGE, 0, "force");
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("fireresi")) {
                    if (player.hasPermission("elowyrbasics.atouts.fireresi")) {
                        AssetManager.getInstance().disableAsset(player, PotionEffectType.FIRE_RESISTANCE, 0, "fireresi");
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("haste")) {
                    if (player.hasPermission("elowyrbasics.atouts.haste")) {
                        AssetManager.getInstance().disableAsset(player, PotionEffectType.FAST_DIGGING, 1, "haste");
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("noFood")) {
                    if (player.hasPermission("elowyrbasics.atouts.noFood")) {
                        AssetManager.getInstance().disableNoFood(player);
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else if (args.getArgs(1).equalsIgnoreCase("anticleanup")) {
                    if (player.hasPermission("elowyrbasics.atouts.anticleanup")) {
                        AssetManager.getInstance().disableAntiCleanUP(player);
                    } else {
                        Lang.send(player, "assets.no-permission");
                    }
                } else {
                    Lang.send(player, "assets.noExist");
                }
            }
    }
}
