package fr.elowyr.basics.commands;

import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class EffectInfoCommand {

    @Command(name = "effects.info", aliases = {"effet.info"}, permission = "elowyrbasics.effetinfo")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() != 1) {
            player.sendMessage(Utils.color("§4§l✘ &7&l• &c/effet info <player>."));
            return;
        }
        Player target = Bukkit.getPlayer(args.getArgs(0));

        if (target == null) {
            player.sendMessage(Utils.color("§4§l✘ &7&l•&cLe joueur n'existe pas."));
            return;
        }

        for (PotionEffect potionEffect : target.getActivePotionEffects()) {

            if (!target.hasPotionEffect(potionEffect.getType())) {
                player.sendMessage(Utils.color("§4§l✘ &7&l• &a" + target.getName() + " &cn'a pas d'effet !"));
                return;
            }
            player.sendMessage(Utils.color("&2&l✔ &7&l• &a" + target.getName() + "&f à les effets: &d" + potionEffect.getType().getName() + "&f:&c" + potionEffect.getAmplifier()));
        }
    }
}
