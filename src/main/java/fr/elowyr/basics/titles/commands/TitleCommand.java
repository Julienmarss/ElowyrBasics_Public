package fr.elowyr.basics.titles.commands;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.titles.TitleManager;
import fr.elowyr.basics.titles.gui.TitleGUI;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class TitleCommand {

    private TitleManager titleManager = TitleManager.getInstance();

    @Command(name = "title", aliases = {"tag", "tags", "titres", "titles", "titre"}, permission = "elowyrbasics.title.gui")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!titleManager.isActive()) {
            ModuleManager.moduleDesactivate(args.getPlayer(), TitleManager.getInstance());
            return;
        }

        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());

        if (profileData == null) return;

        TitleGUI gui = new TitleGUI(1, player, TitleManager.getInstance());
        gui.open(player);
    }
}
