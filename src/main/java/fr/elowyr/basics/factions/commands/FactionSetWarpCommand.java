package fr.elowyr.basics.factions.commands;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.cmd.CmdSetWarp;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.perms.Relation;
import com.massivecraft.factions.util.LazyLocation;
import com.massivecraft.factions.util.TL;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionData;

public class FactionSetWarpCommand extends CmdSetWarp {

    /*public FactionSetWarpCommand() {
        this.aliases.add("setwarp");
        this.aliases.add("sw");
        this.requiredArgs.add("warp");
        this.optionalArgs.put("password", "password");
        this.requirements = (new CommandRequirements.Builder(Permission.SETWARP)).memberOnly().withAction(PermissibleActions.SETWARP).build();
    }*/

    @Override
    public void perform(CommandContext commandContext) {
        FactionData factionData = FactionManager.getInstance().getProvider().get(commandContext.faction.getId());
        if (factionData == null) {
            return;
        }
        int warps = 0;

        if (factionData.getUpgradeLevel() >= 3) {
            warps = 1;
        } else if (factionData.getUpgradeLevel() >= 5) {
            warps = 2;
        } else if (factionData.getUpgradeLevel() >= 7) {
            warps = 3;
        }

        if (commandContext.fPlayer.getRelationToLocation() != Relation.MEMBER) {
            commandContext.fPlayer.msg(TL.COMMAND_SETFWARP_NOTCLAIMED);
        } else {
            if (warps <= commandContext.faction.getWarps().size() && warps != 0) {
                commandContext.fPlayer.msg(TL.COMMAND_SETFWARP_LIMIT, warps);
            } else if (this.transact(commandContext.fPlayer, commandContext)) {
                String warp = commandContext.argAsString(0);
                String password = commandContext.argAsString(1);
                LazyLocation loc = new LazyLocation(commandContext.fPlayer.getPlayer().getLocation());
                commandContext.faction.setWarp(warp, loc);
                if (password != null) {
                    commandContext.faction.setWarpPassword(warp, password);
                }

                commandContext.fPlayer.msg(TL.COMMAND_SETFWARP_SET, warp, password != null ? password : "");
            }
        }
    }

    private boolean transact(FPlayer player, CommandContext context) {
        return player.isAdminBypassing() || context.payForCommand(FactionsPlugin.getInstance().conf().economy().getCostSetWarp(), TL.COMMAND_SETFWARP_TOSET.toString(), TL.COMMAND_SETFWARP_FORSET.toString());
    }

    public TL getUsageTranslation() {
        return TL.COMMAND_SETFWARP_DESCRIPTION;
    }
}
