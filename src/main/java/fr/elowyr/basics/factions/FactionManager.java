package fr.elowyr.basics.factions;

import com.massivecraft.factions.cmd.FCmdRoot;
import fr.elowyr.basics.factions.commands.FMenuCommand;
import fr.elowyr.basics.factions.commands.FactionJoinCommand;
import fr.elowyr.basics.factions.commands.FactionSetWarpCommand;
import fr.elowyr.basics.factions.commands.PrestigesSaveCommand;
import fr.elowyr.basics.factions.data.provider.FactionProvider;
import fr.elowyr.basics.factions.data.utils.IOUtil;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.tasks.FactionSaveTask;
import fr.elowyr.basics.upgrade.commands.FChestCommand;

public class FactionManager extends Module {
    private static FactionManager instance;

    private IOUtil ioUtil;
    private FactionProvider provider;

    public FactionManager() {
        super("FactionsProfile");
        setDesactivable(false);
        instance = this;
        registerProvider();
        registerCommand(new PrestigesSaveCommand());

        registerListener(new FactionListener());

        FCmdRoot root = FCmdRoot.getInstance();
        root.subCommands.remove(root.cmdJoin);
        root.subCommands.remove(root.cmdSetWarp);
        root.addSubCommand(new FactionSetWarpCommand());
        root.addSubCommand(new FactionJoinCommand());
        root.addSubCommand(new FMenuCommand());
        root.addSubCommand(new FChestCommand());

        this.getPlugin().getServer().getScheduler().scheduleAsyncDelayedTask(this.getPlugin(), new FactionSaveTask(), (20 * 60L) * 3);

        System.out.println("[Module] FactionsProfile Loaded");
    }

    private void registerProvider() {
        this.ioUtil = new IOUtil();
        this.provider = new FactionProvider(this.getPlugin());
        this.provider.read();
    }

    public IOUtil getIoUtil() {
        return ioUtil;
    }

    public FactionProvider getProvider() {
        return provider;
    }

    public static FactionManager getInstance() {
        return instance;
    }
}
