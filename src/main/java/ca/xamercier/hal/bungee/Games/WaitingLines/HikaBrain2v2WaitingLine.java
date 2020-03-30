package ca.xamercier.hal.bungee.Games.WaitingLines;

import ca.xamercier.hal.bungee.HALBungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class HikaBrain2v2WaitingLine{
	
	@SuppressWarnings("deprecation")
	public static void add(ProxiedPlayer p){
		/*
		String srv = HikaBrain2v2Utils.getCompGame(p);
		if(srv != null) {
		ServerInfo target = HALBungee.getInstance().getProxy()
				.getServerInfo(srv);
		p.connect(target);
		} else {
		*/
			if(!HALBungee.waitingLine.containsKey(p)) {
			p.sendMessage(
					ChatColor.RED + "[HikaBrain2v2] Tu as ete ajouter dans la fille d'attente pour le HikaBrain2v2");
			HALBungee.waitingLine.put(p, "HikaBrain2v2");
			} else if (HALBungee.waitingLine.get(p).equalsIgnoreCase("HikaBrain2v2")) {
				p.sendMessage(
						ChatColor.RED + "[HikaBrain2v2] Tu as ete enlever dans la fille d'attente pour le HikaBrain2v2");
				HALBungee.waitingLine.remove(p);
			} else {
				if(HALBungee.waitingLine.containsKey(p)) {
					HALBungee.waitingLine.remove(p);
				}
				p.sendMessage(
						ChatColor.RED + "[HikaBrain2v2] Tu as ete ajouter dans la fille d'attente pour le HikaBrain2v2");
				HALBungee.waitingLine.put(p, "HikaBrain2v2");
			}
		//}
	}
	
}
