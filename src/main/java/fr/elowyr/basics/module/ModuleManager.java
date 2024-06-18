package fr.elowyr.basics.module;

import com.google.common.collect.Lists;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ModuleManager {

    private List<Module> modules;

    public ModuleManager() {
        this.modules = Lists.newArrayList();

        System.out.println("[Module] Module Loaded");
    }

    public void registerManager(Module module) {
        this.modules.add(module);
    }

    public static String moduleDesactivate(CommandSender sender, Module module) {
        String errorMessage = Utils.color("&6&lModule &7◆ &fLa fonctionnalité &e" + module.getName().toLowerCase() + " &fest &ftemporairement &cdésactivé&f.");
        sender.sendMessage(errorMessage);
        return errorMessage;
    }

    public Module getModuleByName(String name) {
        for (Module modules : this.getModules()) {
            if (modules.getName().equalsIgnoreCase(name)) {
                return modules;
            }
        }
        return null;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
