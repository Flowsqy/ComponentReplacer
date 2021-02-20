package fr.flowsqy.componentreplacer;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class ComponentReplacerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "ComponentReplacer API correctly loaded");
    }
}
