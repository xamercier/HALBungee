package ca.xamercier.hal.bungee.Games.Utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ca.xamercier.hal.bungee.HALBungee;
import net.md_5.bungee.api.config.ServerInfo;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class HubUtils {

	@SuppressWarnings("rawtypes")
	public static String getCompHub() {
		Map<String, ServerInfo> map = HALBungee.getInstance().getProxy().getServers();
		Iterator<Entry<String, ServerInfo>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String name = (String) entry.getKey();
			String splittedName = name;
			String[] nameANDport;
			String srvPort;
			if (name.contains("_")) {
				nameANDport = splittedName.split("_");
				srvPort = nameANDport[1];
				if (name.contains("hub_")
						&& HALBungee.getInstance().getProxy().getServerInfo(name).getPlayers().size() < 20
						&& HALBungee.getInstance().getSQL().getState(Integer.parseInt(srvPort))
								.equalsIgnoreCase("online")) {
					return name;
				}
			}
		}
		return null;
	}
}
