package fr.elowyr.basics.profiles;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.profiles.commands.BlacklistCommand;
import fr.elowyr.basics.profiles.commands.BlacklistListCommand;
import fr.elowyr.basics.profiles.commands.ProfileCommand;
import fr.elowyr.basics.profiles.commands.UnblacklistCommand;
import fr.elowyr.basics.profiles.data.provider.ProfileProvider;
import fr.elowyr.basics.profiles.data.utils.IOUtil;

public class ProfileManager extends Module {
    private static ProfileManager instance;
    private IOUtil ioUtil;
    private ProfileProvider provider;

    public ProfileManager() {
        super("Profiles");
        setDesactivable(false);
        instance = this;
        registerProvider();
        registerCommand(new ProfileCommand());
        registerCommand(new BlacklistCommand());
        registerCommand(new UnblacklistCommand());
        registerCommand(new BlacklistListCommand());

        registerListener(new ProfileListener());
        System.out.println("[Module] Profiles Loaded");
    }

    private void registerProvider() {
        this.ioUtil = new IOUtil();
        this.provider = new ProfileProvider(ElowyrBasics.getInstance());
        this.provider.read();
    }

    public boolean isBlacklistUsername(String name) {
        return name.equalsIgnoreCase("Notch") || name.equalsIgnoreCase("Dinnerbone") || name.equalsIgnoreCase("Grumm") || name.equalsIgnoreCase("Dustin") || name.equalsIgnoreCase("Hagrid") || name.equalsIgnoreCase("C3PO") || name.equalsIgnoreCase("R2D2") || name.equalsIgnoreCase("md_5");
    }

    public static ProfileManager getInstance() {
        return instance;
    }

    public IOUtil getIoUtil() {
        return ioUtil;
    }

    public ProfileProvider getProvider() {
        return provider;
    }
}
