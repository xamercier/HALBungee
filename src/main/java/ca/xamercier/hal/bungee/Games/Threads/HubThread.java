package ca.xamercier.hal.bungee.Games.Threads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import ca.xamercier.hal.bungee.HALBungee;
import ca.xamercier.hal.bungee.halClient.thread.MainHalClientThread;
import net.md_5.bungee.api.config.ServerInfo;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class HubThread extends Thread {

	@SuppressWarnings({ "rawtypes", "unused" })
	public void run() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		JSONObject initHub = new JSONObject();
		initHub.put("action", "start");
		initHub.put("serverType", "hub");
		MainHalClientThread.getClient().send(initHub.toString());
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
			ArrayList<String> Hubs = new ArrayList<String>();
			Hubs.clear();
			Map<String, ServerInfo> map = HALBungee.getInstance().getProxy().getServers();
			Iterator<Entry<String, ServerInfo>> entries = map.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
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
			int playersInHubs = 0;
			for (String port : Hubs) {
				playersInHubs = playersInHubs
						+ HALBungee.getInstance().getProxy().getServers().get("hub_" + port).getPlayers().size();
			}
			double howmanyhubs;
			howmanyhubs = playersInHubs * 1.6;
			howmanyhubs = howmanyhubs / 20;
			howmanyhubs = howmanyhubs + 1;
			System.out.println("howmanyhubs: " + howmanyhubs);
			int howmanyhubsINTEGER = (int) Math.round(howmanyhubs);
			System.out.println("howmanyhubsINTEGER: " + howmanyhubsINTEGER);
			if (Hubs.size() == howmanyhubsINTEGER) {
				continue;
			} else if (Hubs.size() < howmanyhubsINTEGER) {
				JSONObject startServer = new JSONObject();
				startServer.put("action", "start");
				startServer.put("serverType", "hub");
				MainHalClientThread.getClient().send(startServer.toString());
				System.out.println("STARTING A HUB");
			} else if (Hubs.size() > howmanyhubsINTEGER) {
				Boolean hasOneClosed = false;
				boolean isAHubNotStarted = false;
				for (String port : Hubs) {
					if (HALBungee.getInstance().getSQL().getState(Integer.parseInt(port)).equalsIgnoreCase("online")) {
					} else {
						isAHubNotStarted = true;
					}
				}
				if (isAHubNotStarted == false) {
					for (String port : Hubs) {
						if (HALBungee.getInstance().getProxy().getServers().get("hub_" + port).getPlayers().size() == 0
								&& hasOneClosed == false && HALBungee.getInstance().getSQL()
										.getState(Integer.parseInt(port)).equalsIgnoreCase("online")) {
							JSONObject stopOnSpigot = new JSONObject();
							stopOnSpigot.put("action", "stop");
							stopOnSpigot.put("serverPortOrName", port);
							MainHalClientThread.getClient().send(stopOnSpigot.toString());
							hasOneClosed = true;
							System.out.println("STOPPING A HUB");
							break;
						}
					}

				}

			}
		}

	}
}