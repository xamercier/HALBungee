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
			if (name.contains("hub_") && HALBungee.getInstance().getProxy().getServerInfo(name).getPlayers().size() < 15) {
				return name;
			}
		}
		return null;
	}
}
