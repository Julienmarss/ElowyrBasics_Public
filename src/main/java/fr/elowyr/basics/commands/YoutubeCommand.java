package fr.elowyr.basics.commands;

import fr.elowyr.basics.utils.CooldownUtils;
import fr.elowyr.basics.utils.DurationFormatter;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class YoutubeCommand {

    @Command(name = "youtube", aliases = {"ytp"}, permission = "elowyrbasics.youtube.broadcast")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() != 1) {
            player.sendMessage(Utils.color("&d&lYouTube &7◆ &c/youtube <https://www.youtube.com/XXX>."));
            return;
        }

        String URL = args.getArgs(0);
        String validURL = "https://www.youtube.com/";
        if (!CooldownUtils.isOnCooldown("video", player)) {
            if (URL.contains(validURL)) {
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(Utils.color("&d&lYouTube &7◆ &fNotre &dPartenaire &5&l" + player.getName() + "&f vient de &aposté &fune nouvelle vidéo !"));
                Bukkit.broadcastMessage(Utils.color("&7" + URL));
                Bukkit.broadcastMessage("");
                CooldownUtils.addCooldown("video", player, 28800);
            } else {
                player.sendMessage(Utils.color("&d&lYouTube &7◆ &fVotre URL est &cinvalide&f !"));
            }
        } else {
            player.sendMessage(Utils.color("&d&lYouTube &7◆ &fVous &cpouvez&f mettre une &dvidéo&f dans &2&l" + DurationFormatter.getRemaining(CooldownUtils.getCooldownForPlayerLong("video", player), false) + "&f."));
        }
    }
}
