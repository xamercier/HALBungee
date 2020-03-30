package ca.xamercier.hal.bungee.commands;

import ca.xamercier.hal.bungee.HALBungee;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class CommandManager {

    /**
     * Register all commandes used by HAL on bungeecord
     */
    public void register() {
        PluginManager pluginManager = HALBungee.getInstance().getProxy().getPluginManager();
        Plugin plugin = HALBungee.getInstance();

        pluginManager.registerCommand(plugin, new HALCommand());
    }

}
