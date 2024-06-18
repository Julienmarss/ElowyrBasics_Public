package fr.elowyr.basics;

import com.google.gson.Gson;
import fr.elowyr.basics.alliaser.AlliaserManager;
import fr.elowyr.basics.assets.AssetManager;
import fr.elowyr.basics.assets.commands.AssetCommands;
import fr.elowyr.basics.blackmarket.BlackMarketManager;
import fr.elowyr.basics.blocked.BlockedEnchantment;
import fr.elowyr.basics.blocked.BlockedPotions;
import fr.elowyr.basics.bottlexp.BottleXPManager;
import fr.elowyr.basics.commands.*;
import fr.elowyr.basics.cooldown.CooldownManager;
import fr.elowyr.basics.expansions.ChunloExpansion;
import fr.elowyr.basics.expansions.CombatExpansion;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.gifts.GiftManager;
import fr.elowyr.basics.glitch.GlitchManager;
import fr.elowyr.basics.joyaux.JoyauxManager;
import fr.elowyr.basics.lang.Lang;
import fr.elowyr.basics.limiters.LimiterManager;
import fr.elowyr.basics.listeners.*;
import fr.elowyr.basics.missions.MissionManager;
import fr.elowyr.basics.mobstacker.MobStackerManager;
import fr.elowyr.basics.mod.ModManager;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.module.commands.ModuleCommand;
import fr.elowyr.basics.module.commands.ModuleToggleCommand;
import fr.elowyr.basics.points.PointsManager;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.randomtp.RTPManager;
import fr.elowyr.basics.spawners.SpawnersManager;
import fr.elowyr.basics.tasks.ConnectTimeTask;
import fr.elowyr.basics.tasks.PlayerAreaTask;
import fr.elowyr.basics.titles.TitleManager;
import fr.elowyr.basics.upgrade.UpgradeManager;
import fr.elowyr.basics.utils.commands.CommandFramework;
import fr.elowyr.basics.utils.menu.MenuManager;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ElowyrBasics extends JavaPlugin {
    private static ElowyrBasics instance;

    private CommandFramework commandFramework = new CommandFramework(this);
    private HeadDatabaseAPI headDatabaseAPI;
    private ModuleManager moduleManager;
    private Gson gson;

    private boolean chatEnable;

    private int taskID;

    @Override
    public void onEnable() {
        instance = this;
        chatEnable = true;
        this.moduleManager = new ModuleManager();
        registerWorlds();
        saveDefaultConfig();
        registerManagers();
        registerCommands();
        registerListeners();
        registerTasks();
        registerPlaceholder();
    }

    private void registerWorlds() {
        this.getServer().createWorld(new WorldCreator("dimension"));
        this.getServer().createWorld(new WorldCreator("domination"));
        this.getServer().createWorld(new WorldCreator("Mine"));
        this.getServer().createWorld(new WorldCreator("pres"));
        this.getServer().createWorld(new WorldCreator("presentation"));
        this.getServer().createWorld(new WorldCreator("utils"));
        this.getServer().createWorld(new WorldCreator("citadelle"));
        this.getServer().createWorld(new WorldCreator("koth1"));
    }

    private void registerManagers() {
        MenuManager.getInstance().register(this);
        Lang.set(new Lang(this));
        Lang.get().load();

        if (this.getServer().getPluginManager().isPluginEnabled("HeadDatabase")) {
            this.headDatabaseAPI = new HeadDatabaseAPI();
        }

        moduleManager.registerManager(new ProfileManager());
        moduleManager.registerManager(new UpgradeManager());
        moduleManager.registerManager(new GiftManager());
        moduleManager.registerManager(new PointsManager());
        moduleManager.registerManager(new JoyauxManager());
        moduleManager.registerManager(new FactionManager());
        moduleManager.registerManager(new AssetManager());
        moduleManager.registerManager(new CooldownManager());
        moduleManager.registerManager(new BlackMarketManager());
        moduleManager.registerManager(new MissionManager());
        moduleManager.registerManager(new SpawnersManager());
        moduleManager.registerManager(new LimiterManager());
        moduleManager.registerManager(new AlliaserManager());
        moduleManager.registerManager(new TitleManager());
        moduleManager.registerManager(new RTPManager());
        moduleManager.registerManager(new BottleXPManager());
        moduleManager.registerManager(new GlitchManager());
        moduleManager.registerManager(new ModManager());
        moduleManager.registerManager(new MobStackerManager());
    }

    private void registerCommands() {
        commandFramework.registerCommands(new ModuleCommand());
        commandFramework.registerCommands(new ModuleToggleCommand());
        commandFramework.registerCommands(new ActionBarCommand());
        commandFramework.registerCommands(new AssetCommands());
        commandFramework.registerCommands(new YoutubeCommand());
        commandFramework.registerCommands(new EffectInfoCommand());
        commandFramework.registerCommands(new ReloadCommand());
        commandFramework.registerCommands(new PluginCommand());
        commandFramework.registerCommands(new EXPCommand());
        commandFramework.registerCommands(new TitleCommand());
        commandFramework.registerCommands(new SoutienCommand());
        commandFramework.registerCommands(new BoostCommand());
        commandFramework.registerCommands(new StarterPacketCommand());
    }


    private void registerListeners() {
        PluginManager listener = Bukkit.getPluginManager();
        listener.registerEvents(new PlayerListener(), this);
        listener.registerEvents(new EnchantsListener(), this);
        listener.registerEvents(new BlockedEnchantment(), this);
        listener.registerEvents(new BlockedPotions(), this);
        listener.registerEvents(new PlaceBlockListener(), this);
        listener.registerEvents(new ItemPlacedListener(), this);
        listener.registerEvents(new GrowListener(), this);
        listener.registerEvents(new CreatureSpawnListener(), this);
        listener.registerEvents(new ChatListener(), this);
    }

    private void registerTasks() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new PlayerAreaTask(), 1L, 20L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new ConnectTimeTask(), 1L, 1200L);
    }

    private void registerPlaceholder() {
        if (this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new CombatExpansion().register();
            new ChunloExpansion().register();
        }
    }

    @Override
    public void onDisable() {
        for (Module module : this.getModuleManager().getModules()) {
            module.onUnload();
        }
        ProfileManager.getInstance().getProvider().write();
        TitleManager.getInstance().getProvider().write();
        FactionManager.getInstance().getProvider().write();
        BlackMarketManager.getInstance().getProvider().write();
    }

    /**
     * Log a info message.
     */
    public static void info(final String message) {
        instance.getLogger().info(message);
    }

    /**
     * Log a warning message.
     */
    public static void warn(final String message) {
        instance.getLogger().warning(message);
    }

    /**
     * Log a severe message.
     */
    public static void severe(final String message) {
        instance.getLogger().severe(message);
    }

    //* GETTER AND SETTER *//

    public static ElowyrBasics getInstance() {
        return instance;
    }

    public static void setInstance(ElowyrBasics instance) {
        ElowyrBasics.instance = instance;
    }

    public CommandFramework getCommandFramework() {
        return commandFramework;
    }

    public void setCommandFramework(CommandFramework commandFramework) {
        this.commandFramework = commandFramework;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public boolean isChatEnable() {
        return chatEnable;
    }

    public void setChatEnable(boolean chatEnable) {
        this.chatEnable = chatEnable;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public HeadDatabaseAPI getHeadDatabaseAPI() {
        return headDatabaseAPI;
    }
}
