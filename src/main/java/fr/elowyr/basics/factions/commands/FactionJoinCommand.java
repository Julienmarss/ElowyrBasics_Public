package fr.elowyr.basics.factions.commands;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.cmd.CmdJoin;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.event.FPlayerJoinEvent;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.util.TL;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.Bukkit;

public class FactionJoinCommand extends CmdJoin {

    @Override
    public void perform(CommandContext commandContext) {
        Faction faction = commandContext.argAsFaction(0);
        if (faction == null) return;

        FPlayer fPlayer = commandContext.argAsBestFPlayerMatch(1, commandContext.fPlayer, false);
        int i = fPlayer == commandContext.fPlayer ? 1 : 0;
        if ((i == 0) && (!Permission.JOIN_OTHERS.has(commandContext.sender, false))) {
            commandContext.msg(TL.COMMAND_JOIN_CANNOTFORCE);
            return;
        }
        if (!faction.isNormal()) {
            commandContext.msg(TL.COMMAND_JOIN_SYSTEMFACTION);
            return;
        }
        if (faction == fPlayer.getFaction()) {
            commandContext.msg(TL.COMMAND_JOIN_ALREADYMEMBER, fPlayer.describeTo(commandContext.fPlayer, true), i != 0 ? "are" : "is", faction.getTag(commandContext.fPlayer));
            return;
        }
        FactionData factionData = FactionManager.getInstance().getProvider().get(faction.getId());
        if (factionData == null) return;

        int membersLimit = 12;

        if (factionData.getUpgradeLevel() >= 2) {
            membersLimit = 13;
        } else if (factionData.getUpgradeLevel() >= 4) {
            membersLimit = 14;
        } else if (factionData.getUpgradeLevel() >= 6) {
            membersLimit = 15;
        }
        if (faction.getFPlayers().size() >= membersLimit) {
            commandContext.msg(Utils.color("&6&lFaction &7◆ &fVous ne &cpouvez &fpas &erejoindre &fcette faction. &7(" + membersLimit + ")"));
            return;
        }

        if (fPlayer.hasFaction()) {
            commandContext.msg(TL.COMMAND_JOIN_INOTHERFACTION, fPlayer.describeTo(commandContext.fPlayer, true), i != 0 ? "your" : "their");
            return;
        }
        if ((!faction.getOpen()) && (!faction.isInvited(fPlayer)) && (!commandContext.fPlayer.isAdminBypassing()) && (!Permission.JOIN_ANY.has(commandContext.sender, false)))
        {
            commandContext.msg(TL.COMMAND_JOIN_REQUIRESINVITATION);
            if (i != 0) {
                faction.msg(TL.COMMAND_JOIN_ATTEMPTEDJOIN, fPlayer.describeTo(faction, true));
            }
            return;
        }
        if ((i != 0) && (!commandContext.canAffordCommand(FactionsPlugin.getInstance().conf().economy().getCostJoin(), TL.COMMAND_JOIN_TOJOIN.toString()))) {
            return;
        }
        FPlayerJoinEvent fPlayerJoinEvent = new FPlayerJoinEvent(FPlayers.getInstance().getByPlayer(commandContext.player), faction, FPlayerJoinEvent.PlayerJoinReason.COMMAND);
        Bukkit.getServer().getPluginManager().callEvent(fPlayerJoinEvent);
        if (fPlayerJoinEvent.isCancelled()) {
            return;
        }
        if ((i != 0) && (!commandContext.payForCommand(FactionsPlugin.getInstance().conf().economy().getCostJoin(), TL.COMMAND_JOIN_TOJOIN.toString(), TL.COMMAND_JOIN_FORJOIN.toString()))) {
            return;
        }
        commandContext.fPlayer.msg(TL.COMMAND_JOIN_SUCCESS, fPlayer.describeTo(commandContext.fPlayer, true), faction.getTag(commandContext.fPlayer));
        if (i == 0) {
            fPlayer.msg(TL.COMMAND_JOIN_MOVED, commandContext.fPlayer.describeTo(fPlayer, true), faction.getTag(fPlayer));
        }
        faction.msg(TL.COMMAND_JOIN_JOINED, fPlayer.describeTo(faction, true));

        fPlayer.resetFactionData();
        fPlayer.setFaction(faction);
        faction.deinvite(fPlayer);
        if (FactionsPlugin.getInstance().conf().logging().isFactionJoin()) {
            if (i != 0) {
                FactionsPlugin.getInstance().log(TL.COMMAND_JOIN_JOINEDLOG.toString(), fPlayer.getName(), faction.getTag());
            } else {
                FactionsPlugin.getInstance().log(TL.COMMAND_JOIN_MOVEDLOG.toString(), commandContext.fPlayer.getName(), fPlayer.getName(), faction.getTag());
            }
        }
    }
}
