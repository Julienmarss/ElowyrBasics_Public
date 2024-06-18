package fr.elowyr.basics.gifts.commands;

import fr.elowyr.basics.gifts.GiftManager;
import fr.elowyr.basics.gifts.gui.GiftGUI;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class GiftCommand {

    @Command(name = "gift", aliases = {"gifts", "don", "dons"}, permission = "elowyrbasics.gift.gui")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!GiftManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, GiftManager.getInstance());
            return;
        }

        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
        if (profileData == null) {
            player.sendMessage(Utils.color("&6&lDon &7◆ &fVotre profil &cn'existe&f pas."));
            return;
        }
        GiftGUI gui = new GiftGUI(player);
        gui.open(player);
    }
}
