package ca.xamercier.hal.bungee.Games.WaitingLines;

import ca.xamercier.hal.bungee.HALBungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class HikaBrain1v1WaitingLine{

	@SuppressWarnings("deprecation")
	public static void add(ProxiedPlayer p){
		/*
		String srv = HikaBrain1v1Utils.getCompGame(p);
		if(srv != null) {
		ServerInfo target = HALBungee.getInstance().getProxy()
				.getServerInfo(srv);
		p.connect(target);
		} else {
		*/
			if(!HALBungee.waitingLine.containsKey(p)) {
			p.sendMessage(
					ChatColor.RED + "[HikaBrain1v1] Tu as ete ajouter dans la fille d'attente pour le HikaBrain1v1");
			HALBungee.waitingLine.put(p, "HikaBrain1v1");
			} else if (HALBungee.waitingLine.get(p).equalsIgnoreCase("HikaBrain1v1")) {
				p.sendMessage(
						ChatColor.RED + "[HikaBrain1v1] Tu as ete enlever dans la fille d'attente pour le HikaBrain1v1");
				HALBungee.waitingLine.remove(p);
			} else {
				if(HALBungee.waitingLine.containsKey(p)) {
					HALBungee.waitingLine.remove(p);
				}
				p.sendMessage(
						ChatColor.RED + "[HikaBrain1v1] Tu as ete ajouter dans la fille d'attente pour le HikaBrain1v1");
				HALBungee.waitingLine.put(p, "HikaBrain1v1");
			}
		//}
	}
	
}
