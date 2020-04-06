package ca.xamercier.hal.bungee.Games.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ca.xamercier.hal.bungee.HALBungee;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class HikaBrain1v1Utils {

	@SuppressWarnings({ "rawtypes", "unused"})
	public static String getCompGame(ProxiedPlayer p) {
		ArrayList<String> FoundGames = new ArrayList<String>();
		FoundGames.clear();
		ArrayList<String> CompGames = new ArrayList<String>();
		CompGames.clear();
		ArrayList<String> ReturnedGames = new ArrayList<String>();
		ReturnedGames.clear();
		Map<String, ServerInfo> map = HALBungee.getInstance().getProxy().getServers();
		Iterator<Entry<String, ServerInfo>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String name = (String) entry.getKey();
			if (name.contains("HikaBrain1v1_")) {
				FoundGames.add(name);
				String[] nameANDport;
				nameANDport = name.split("_");
				String srvType;
				String srvPort;
				srvType = nameANDport[0];
				srvPort = nameANDport[1];
				if (HALBungee.getInstance().getSQL().getState(Integer.parseInt(srvPort)).equalsIgnoreCase("WAITING")) {
					CompGames.add(name);
				}
			}
		}
		for (String name : CompGames) {
			String[] nameANDport;
			nameANDport = name.split("_");
			String srvType;
			String srvPort;
			srvType = nameANDport[0];
			srvPort = nameANDport[1];
			if(HALBungee.getInstance().getProxy().getServerInfo(srvType + "_" + srvPort).getPlayers().size() == 1) {
				ReturnedGames.add(name);
				return name;
			}
		}
		if (ReturnedGames.size() == 0) {
			if (CompGames.size() != 0) {
				return CompGames.get(0);
			}
		}
		if (ReturnedGames.size() == 0) {
			return null;
		}
		return null;
	}
	
	
	@SuppressWarnings({ "unused","rawtypes" })
	public static String getCompEmptyGame() {
		ArrayList<String> FoundGames = new ArrayList<String>();
		FoundGames.clear();
		ArrayList<String> CompGames = new ArrayList<String>();
		CompGames.clear();
		ArrayList<String> ReturnedGames = new ArrayList<String>();
		ReturnedGames.clear();
		Map<String, ServerInfo> map = HALBungee.getInstance().getProxy().getServers();
		Iterator<Entry<String, ServerInfo>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String name = (String) entry.getKey();
			if (name.contains("HikaBrain1v1_")) {
				FoundGames.add(name);
				String[] nameANDport;
				nameANDport = name.split("_");
				String srvType;
				String srvPort;
				srvType = nameANDport[0];
				srvPort = nameANDport[1];
				if (HALBungee.getInstance().getSQL().getState(Integer.parseInt(srvPort)).equalsIgnoreCase("WAITING")) {
					CompGames.add(name);
				}
			}
		}
		for (String name : CompGames) {
			String[] nameANDport;
			nameANDport = name.split("_");
			String srvType;
			String srvPort;
			srvType = nameANDport[0];
			srvPort = nameANDport[1];
			if (HALBungee.getInstance().getProxy().getServerInfo(srvType + "_" + srvPort).getPlayers().size() == 0) {
				ReturnedGames.add(name);
				return name;
			}
		}
		if (ReturnedGames.size() == 0) {
			return null;
		}
		return null;
	}
	

}
