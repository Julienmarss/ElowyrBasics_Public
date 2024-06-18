package fr.elowyr.basics.lang;

import fr.elowyr.basics.interfaces.TypedMessage;
import org.bukkit.entity.Player;

public class NoneMessage implements TypedMessage
{
    public static final NoneMessage INSTANCE = new NoneMessage();
    
    @Override
    public void send(final Player player, final Object... replacements) {
    }
    
    @Override
    public void broadcast(final Object... replacements) {
    }

}
