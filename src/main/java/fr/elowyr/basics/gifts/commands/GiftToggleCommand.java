package fr.elowyr.basics.gifts.commands;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.gifts.GiftManager;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class GiftToggleCommand {

    @Command(name = "gift.toggle", aliases = {"gifts.toggle", "don.toggle", "dons.toggle"}, permission = "elowyrbasics.gift.toggle")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!GiftManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, GiftManager.getInstance());
            return;
        }

        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());

       boolean statut = !profileData.isToggleGifts();
       profileData.setToggleGifts(statut);
       player.sendMessage(Utils.color("&6&lDon &7◆ &fVous venez " + (statut ? "de &cdésactiver" : "&ad'activer") + "&f la réception des dons."));
    }
}
