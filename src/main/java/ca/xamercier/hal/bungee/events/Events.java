package ca.xamercier.hal.bungee.events;

import ca.xamercier.hal.bungee.HALBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class Events implements Listener {

	@EventHandler
	public void onPostLogin(PostLoginEvent e) {
	}

	@EventHandler
	public void onJoinServer(ServerConnectedEvent e) {
		ProxiedPlayer p = e.getPlayer();
		if (HALBungee.waitingLine.containsKey(p)) {
			HALBungee.waitingLine.remove(p);
		}

		HALBungee.getInstance().getSQL().setPlayerServer(p, e.getServer().getInfo().getName());
		/*
		 * Se met pas bien a jour erreur a trouver...
		for(GameManager game : GameManager.values()) {
			if(e.getServer().getInfo().getName().contains(game.getNameForEvents())) {
				HALBungee.getInstance().getSQL().setPlayerGameState(p, game.getPlayerGameState());
				break;
			}
		}
		*/
		if (e.getServer().getInfo().getName().contains("BuildServ")) {
			HALBungee.getInstance().getSQL().setPlayerGameState(p, "Build");
		} else if (e.getServer().getInfo().getName().contains("LectusConnect")){
			HALBungee.getInstance().getSQL().setPlayerGameState(p, "Connect");
		} else if (e.getServer().getInfo().getName().contains("hub")) {
			HALBungee.getInstance().getSQL().setPlayerGameState(p, "hub");
		} else {
			HALBungee.getInstance().getSQL().setPlayerGameState(p, "InGame");
		}
	}

	@EventHandler
	public void onDisconnectPlayerEvent(PlayerDisconnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		if (HALBungee.waitingLine.containsKey(p)) {
			HALBungee.waitingLine.remove(p);
		}
		HALBungee.getInstance().getSQL().setPlayerServer(p, "");
		HALBungee.getInstance().getSQL().setPlayerGameState(p, "Deconnecte");
	}
}
