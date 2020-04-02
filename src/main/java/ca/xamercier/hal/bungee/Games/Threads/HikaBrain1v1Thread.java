package ca.xamercier.hal.bungee.Games.Threads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import ca.xamercier.hal.bungee.HALBungee;
import ca.xamercier.hal.bungee.Games.Utils.HikaBrain1v1Utils;
import ca.xamercier.hal.bungee.halClient.thread.MainHalClientThread;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class HikaBrain1v1Thread extends Thread {

	
	ArrayList<ProxiedPlayer> toRemove = new ArrayList<ProxiedPlayer>();

	private void removePlayers() {
		for (ProxiedPlayer p : toRemove) {
			if (HALBungee.waitingLine.containsKey(p)) {
				HALBungee.waitingLine.remove(p);
			}
		}
		toRemove.clear();
	}

	@SuppressWarnings({ "rawtypes", "unused", })
	public void run() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		JSONObject initHika = new JSONObject();
		initHika.put("action", "start");
		initHika.put("serverType", "HikaBrain1v1");
		MainHalClientThread.getClient().send(initHika.toString());
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (true) {

			try {
				Thread.sleep(3500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
						
			
			for (ProxiedPlayer p : HALBungee.getInstance().getProxy().getPlayers()) {
				if (HALBungee.getInstance().getProxy().getPlayers().contains(p) && HALBungee.waitingLine.containsKey(p)) {

					
					
					
					if (HALBungee.waitingLine.get(p).equalsIgnoreCase("HikaBrain1v1")) {
						String srv = HikaBrain1v1Utils.getCompGame(p);
						if (srv != null) {
							ServerInfo target = HALBungee.getInstance().getProxy().getServerInfo(srv);
							p.connect(target);
							toRemove.add(p);
						}
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					
					
					
					
					
				} else {
					toRemove.add(p);
				}
			}
			removePlayers();

			
			
			
			
			
			
			
			
			
			
			
			
			ArrayList<String> HikaBrain1v1 = new ArrayList<String>();
			HikaBrain1v1.clear();
			Map<String, ServerInfo> map = HALBungee.getInstance().getProxy().getServers();
			Iterator<Entry<String, ServerInfo>> entries = map.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				ServerInfo value = (ServerInfo) entry.getValue();
				String srvType;
				String srvPort;
				if (name.contains("HikaBrain1v1_")) {
					String[] nameANDport;
					nameANDport = name.split("_");
					srvType = nameANDport[0];
					srvPort = nameANDport[1];
					HikaBrain1v1.add(srvPort);
				}
			}
			int playersInHikaBrain1v1 = 0;
			for (String port : HikaBrain1v1) {
				playersInHikaBrain1v1 = playersInHikaBrain1v1 + HALBungee.getInstance().getProxy().getServers()
						.get("HikaBrain1v1_" + port).getPlayers().size();
			}

			ArrayList<String> Hubs = new ArrayList<String>();
			Hubs.clear();
			Map<String, ServerInfo> map1 = HALBungee.getInstance().getProxy().getServers();
			Iterator<Entry<String, ServerInfo>> entries1 = map1.entrySet().iterator();
			while (entries1.hasNext()) {
				Map.Entry entry = (Map.Entry) entries1.next();
				String name = (String) entry.getKey();
				ServerInfo value = (ServerInfo) entry.getValue();
				String srvType;
				String srvPort;
				if (name.contains("hub_")) {
					String[] nameANDport;
					nameANDport = name.split("_");
					srvType = nameANDport[0];
					srvPort = nameANDport[1];
					Hubs.add(srvPort);
				}
			}

			double playersInHubs = 0;
			for (String port : Hubs) {
				playersInHubs = playersInHubs
						+ HALBungee.getInstance().getProxy().getServers().get("hub_" + port).getPlayers().size();
			}

			int howmanyHubINTEGER = 0;
			howmanyHubINTEGER = (int) Math.round(playersInHubs);
			howmanyHubINTEGER = howmanyHubINTEGER / 4;

			double howmanyHikaBrain1v1;
			howmanyHikaBrain1v1 = playersInHikaBrain1v1 / 2;
			howmanyHikaBrain1v1 = howmanyHikaBrain1v1 + 2;

			howmanyHikaBrain1v1 = howmanyHikaBrain1v1 + howmanyHubINTEGER;

			System.out.println("howmanyHikaBrain1v1: " + howmanyHikaBrain1v1);
			int howmanyHikaBrain1v1INTEGER = (int) Math.ceil(howmanyHikaBrain1v1);
			System.out.println("howmanyHikaBrain1v1INTEGER: " + howmanyHikaBrain1v1INTEGER);

			if (HikaBrain1v1.size() == howmanyHikaBrain1v1INTEGER) {
				continue;

			} else if (HikaBrain1v1.size() < howmanyHikaBrain1v1INTEGER) {
				JSONObject startServer = new JSONObject();
				startServer.put("action", "start");
				startServer.put("serverType", "HikaBrain1v1");
				MainHalClientThread.getClient().send(startServer.toString());
				System.out.println("STARTING A HikaBrain1v1");

			} else if (HikaBrain1v1.size() > howmanyHikaBrain1v1INTEGER) {
				/*
				 * Boolean hasOneClosed = false; boolean
				 * isAHikaBrain1v1NotStarted = false;
				 * 
				 * for (String port : HikaBrain1v1) { if
				 * (!HALBungee.getInstance().getSQL().getState(Integer.parseInt(
				 * port)) .equalsIgnoreCase("BOOTING")) { } else {
				 * isAHikaBrain1v1NotStarted = true; } }
				 * 
				 * if (isAHikaBrain1v1NotStarted == false) { for (String port :
				 * HikaBrain1v1) { if
				 * (HALBungee.getInstance().getProxy().getServers().get(
				 * "HikaBrain1v1_" + port).getPlayers() .size() == 0 &&
				 * hasOneClosed == false &&
				 * !HALBungee.getInstance().getSQL().getState(Integer.parseInt(
				 * port)) .equalsIgnoreCase("BOOTING")) { JSONObject
				 * stopOnSpigot = new JSONObject(); stopOnSpigot.put("action",
				 * "stop"); stopOnSpigot.put("serverPortOrName", port);
				 * MainHalClientThread.getClient().send(stopOnSpigot.toString())
				 * ; hasOneClosed = true; System.out.println(
				 * "STOPPING A HikaBrain1v1"); break; } }
				 * 
				 * }
				 */

			}

		}
	}
}
