package ca.xamercier.hal.bungee.Games.WaitingLines;

import ca.xamercier.hal.bungee.HALBungee;
import ca.xamercier.hal.bungee.Games.Utils.HubUtils;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class HubWaitingLine{

	@SuppressWarnings("deprecation")
	public static void add(ProxiedPlayer p){
		String srv = HubUtils.getCompHub();
		if(srv != null) {
		ServerInfo target = HALBungee.getInstance().getProxy()
				.getServerInfo(srv);
		p.connect(target);
		} else {
			p.disconnect("KickOnConnection (could not find an available hub) : Les serveurs sont en surcharge");
		}
	}

}
