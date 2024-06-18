package fr.elowyr.basics.module;

import fr.elowyr.basics.ElowyrBasics;
import org.bukkit.event.Listener;

public abstract class Module {

    private ElowyrBasics plugin = ElowyrBasics.getInstance();
    private boolean active, desactivable;
    private String name;

    public Module(String name) {
        this.active = true;
        this.desactivable = true;
        this.name = name;
    }

    public void onLoad() {
        this.plugin.getLogger().info("Le module " + this.name + " à été load avec succès.");
    }

    public void onUnload() {
        this.plugin.getLogger().info("Le module " + this.name + " à été unload avec succès.");
    }

    public void registerCommand(Object command) {
        this.plugin.getCommandFramework().registerCommands(command);
    }


    public void registerListener(Listener listener) {
        this.plugin.getServer().getPluginManager().registerEvents(listener, this.plugin);
    }

    public ElowyrBasics getPlugin() {
        return plugin;
    }

    public void setPlugin(ElowyrBasics plugin) {
        this.plugin = plugin;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDesactivable() {
        return desactivable;
    }

    public void setDesactivable(boolean desactivable) {
        this.desactivable = desactivable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
