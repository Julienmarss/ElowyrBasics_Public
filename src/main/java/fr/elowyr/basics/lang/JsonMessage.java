package fr.elowyr.basics.lang;

import fr.elowyr.basics.interfaces.TypedMessage;
import fr.elowyr.basics.utils.BukkitUtils;
import fr.elowyr.basics.utils.Utils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JsonMessage implements TypedMessage {

    private final String message;
    
    public JsonMessage(final String message) {
        this.message = message;
    }
    
    @Override
    public void send(final Player player, final Object... replacements) {
        final IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a(Utils.replaceAll(this.message, replacements));
        BukkitUtils.sendPacket(player, new PacketPlayOutChat(component));
    }
    
    @Override
    public void broadcast(final Object... replacements) {
        final IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a(Utils.replaceAll(this.message, replacements));
        final PacketPlayOutChat packet = new PacketPlayOutChat(component);
        for (final Player player : Bukkit.getOnlinePlayers()) {
            BukkitUtils.sendPacket(player, packet);
        }
    }
}
