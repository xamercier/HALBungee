package ca.xamercier.hal.bungee.listeners.server;

import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class ServerRegisterListener implements Listener {

    @EventHandler
    public void onServerRegister(ServerConnectedEvent event) {
    	/*
    	 *SO FUCKING ANNOYING IN CONSOLE 
        System.out.println("--------");
        System.out.println(event.toString());
        System.out.println("--------");
        */
    }

}
