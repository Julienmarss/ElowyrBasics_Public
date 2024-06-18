package fr.elowyr.basics.gifts;

import fr.elowyr.basics.gifts.commands.GiftCommand;
import fr.elowyr.basics.gifts.commands.GiftSendCommand;
import fr.elowyr.basics.gifts.commands.GiftToggleCommand;
import fr.elowyr.basics.module.Module;

public class GiftManager extends Module {
    private static GiftManager instance;

    public GiftManager() {
        super("Gift");
        instance = this;
        registerCommand(new GiftCommand());
        registerCommand(new GiftSendCommand());
        registerCommand(new GiftToggleCommand());

        System.out.println("[Module] Gift Loaded");
    }

    public static GiftManager getInstance() {
        return instance;
    }
}
