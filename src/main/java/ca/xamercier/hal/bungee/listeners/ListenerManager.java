package ca.xamercier.hal.bungee.listeners;

import ca.xamercier.hal.bungee.HALBungee;
import ca.xamercier.hal.bungee.events.Events;
import ca.xamercier.hal.bungee.listeners.server.ServerRegisterListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class ListenerManager {

    /**
     * Register all listeners used by HAL on bungeecord
     */
    public void register() {
        PluginManager pluginManager = HALBungee.getInstance().getProxy().getPluginManager();
        Plugin plugin = HALBungee.getInstance();

        pluginManager.registerListener(plugin, new ServerRegisterListener());
        pluginManager.registerListener(plugin, new Events());
    }

}
