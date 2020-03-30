package ca.xamercier.hal.bungee.Games.Threads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import ca.xamercier.hal.bungee.HALBungee;
import ca.xamercier.hal.bungee.halClient.thread.MainHalClientThread;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
		while (true) {
			ArrayList<String> Hubs = new ArrayList<String>();
			Hubs.clear();
			ArrayList<String> CompHubs = new ArrayList<String>();
			CompHubs.clear();
			boolean needCompHub = false;
			int howManymoreHubs = 0;
			int triedHub = 0;
			int failedHub = 0;
			int loop = Hubs.size();
			int tour = 0;
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
			
			if (Hubs.size() == 0) {
				JSONObject startServer = new JSONObject();
				startServer.put("action", "start");
				startServer.put("serverType", "hub");
				MainHalClientThread.getClient().send(startServer.toString());
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			
			for (String port : Hubs) {
				if (HALBungee.getInstance().getProxy().getServers().get("hub_" + port).getPlayers().size() > 15) {
					needCompHub = true;
					for (String port1 : Hubs) {
						if (!port1.equalsIgnoreCase(port)) {
							if (HALBungee.getInstance().getProxy().getServers().get("hub_" + port1).getPlayers().size()
									+ 5 < 15) {
								triedHub++;
								CompHubs.add(port1);
								ArrayList<ProxiedPlayer> players = new ArrayList<ProxiedPlayer>();
								for (ProxiedPlayer player : HALBungee.getInstance().getProxy().getServers()
										.get("hub_" + port1).getPlayers()) {
									players.add(player);
								}
								for (int i = 0; i < 5; i++) {
									ProxiedPlayer p = players.get(i);
									ServerInfo target = HALBungee.getInstance().getProxy()
											.getServerInfo("hub_" + port1);
									p.connect(target);
								}
								continue;
							} else {
								triedHub++;
								failedHub++;
								howManymoreHubs++;
								if (failedHub == triedHub) {
									for (int i = 0; i < howManymoreHubs; i++) {
										JSONObject startServer = new JSONObject();
										startServer.put("action", "start");
										startServer.put("serverType", "hub");
										MainHalClientThread.getClient().send(startServer.toString());
									}
								}
							}
						} else {
							continue;
						}
					}
				} else if (HALBungee.getInstance().getProxy().getServers().get("hub_" + port).getPlayers().size() == 0
						&& Hubs.size() > 1) {
					JSONObject stopOnSpigot = new JSONObject();
					stopOnSpigot.put("action", "stop");
					stopOnSpigot.put("serverPortOrName", port);
					MainHalClientThread.getClient().send(stopOnSpigot.toString());
				}
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}