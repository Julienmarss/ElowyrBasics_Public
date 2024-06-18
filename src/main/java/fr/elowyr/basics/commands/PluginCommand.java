package fr.elowyr.basics.commands;

import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class PluginCommand {

    @Command(name = "plugins", aliases = {"pl", "bukkit:pl", "bukkit:plugins", "pluginss"})
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        player.sendMessage(Utils.color("&7[&aPlugMan&7] &9Plugins (24): &aClueScrolls, CMILib, CrazyCrates, DecentHolograms, DeluxeMenus, ElowyrCore, ElowyrBasics, Essentials, Factions, FastAsyncWorldEdit, HeadDatabase, Jobs, LPX, LuckPerms, LunarClient-API, PlaceholderAPI, PlugMan, ServerNPC, spark, TAB, Vault, ViaVersion, WorldEdit, WorldGuard"));
    }
}
