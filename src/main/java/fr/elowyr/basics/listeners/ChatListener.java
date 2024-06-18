package fr.elowyr.basics.listeners;

import fr.elowyr.basics.utils.CooldownUtils;
import fr.elowyr.basics.utils.DurationFormatter;
import fr.elowyr.basics.utils.Utils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

public class ChatListener implements Listener {

    private String[] BAD_WORDS = {"lag", "fdp", "ez", "ezz", "tg", "ntm", "connard",
            "enfoiré", "pigeon", "suceur", "pute", "segpa", "gitan", "shivana", "herodia", "pandakmc", "shandera", "menoria", "hardfight", "ironfight", "pute",
            "getafaction", "xcraft", "voltaireloveme", "noob", "tapette", "raclure", "merde", "kikou", "arabe",
            "abruti", "batard", "kikoo", "chintok", "zgeg", "bite", "anus", "rekt", "jk", "jean-kevin", "tgg", "ezzz",
            "salope", "enculer", "salopard", "fuck", "sperme", "youporn", "xnxx", "gangbang", "brazzer",
            "tukif", "RAT", "boot", "DDOS", "pornhub", "bouffon", "rebeu", "negro", "porc", "pd", "suceuse", "branleur",
            "branleuse", "penis", "fist", "fuck", "bouffon", "sodomie", "cul", "niked", "fucking", "con", "8-D",
            "8--D", "8---D", "chatte", "milf", "beurette", "%", "eaglemc", ""};


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if (this.isUpperMessage(message)) {
            message = message.substring(0, 1).toUpperCase() + message.substring(1).toLowerCase();
        }

        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (player.hasPermission("elowyrbasics.i")) {
            if (CooldownUtils.isOnCooldown("[i]", player)) {
                player.sendMessage(Utils.color("&6&lElowyr &7◆ &fVous devez attendre &d"
                        + DurationFormatter.getRemaining(CooldownUtils.getCooldownForPlayerLong("[i]", player), false)) + "&f.");
                return;
            }
            if (itemInHand == null || !itemInHand.hasItemMeta() || itemInHand.getItemMeta().getDisplayName() == null
                    || StringUtils.countMatches(message, "[i]") != 1) {
                event.setMessage(message);
                return;
            }

            String itemMessage = itemInHand.getAmount() > 1 ? "§7(§6§lx" + itemInHand.getAmount() + "§r "
                    + itemInHand.getItemMeta().getDisplayName() + "§7)§r" : itemInHand.getItemMeta().getDisplayName() + "§r";
            event.setMessage(message.replace("[i]", itemMessage));
            CooldownUtils.addCooldown("[i]", player, 5);
        }

        if (this.containsBadWords(event.getMessage())) {
            event.setCancelled(true);
            player.sendMessage(Utils.color("&6&lElowyr &7◆ &fVotre message &econtient &fun ou plusieurs mots &cinterdit&f."));
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onFactionList(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();
        PluginCommand pluginCommand = Bukkit.getPluginCommand(message.split(" ")[0].substring(1));
        if (pluginCommand != null
                && pluginCommand.getPlugin().getName().equals("Factions")
                && message.contains(" list")
                && !message.equals(" listclaims")
                && message.split(" ").length > 2) {
            if (!player.isOp()) {
                event.setCancelled(true);
            }
        }
    }

    private boolean isUpperMessage(String message) {
        int i = 0;
        for (Character character : message.toCharArray()) {
            if (Character.isUpperCase(character)) {
                i++;
            }
        }
        return i >= 10;
    }

    private boolean containsBadWords(String message) {
        String[] split = message.split(" ");
        for (int i = 0; i < split.length; i++) {
            String word = split[i];
            if (ArrayUtils.contains(this.BAD_WORDS, word)) {
                return true;
            }
        }
        return false;
    }
}
