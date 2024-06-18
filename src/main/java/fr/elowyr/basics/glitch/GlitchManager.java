package fr.elowyr.basics.glitch;

import fr.elowyr.basics.glitch.commands.CommandDEOP;
import fr.elowyr.basics.glitch.commands.CommandListOP;
import fr.elowyr.basics.glitch.commands.CommandOP;
import fr.elowyr.basics.glitch.listeners.GlitchListener;
import fr.elowyr.basics.module.Module;

public class GlitchManager extends Module {
    private static GlitchManager instance;

    public GlitchManager() {
        super("Glitch");
        instance = this;

        registerCommand(new CommandOP());
        registerCommand(new CommandDEOP());
        registerCommand(new CommandListOP());

        registerListener(new GlitchListener());
    }

    public static GlitchManager getInstance() {
        return instance;
    }
}
