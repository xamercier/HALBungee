package ca.xamercier.hal.bungee.Games.Threads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import ca.xamercier.hal.bungee.HALBungee;
import ca.xamercier.hal.bungee.Games.Utils.HikaBrain1v1Utils;
import ca.xamercier.hal.bungee.Games.WaitingLines.HubWaitingLine;
import ca.xamercier.hal.bungee.halClient.thread.MainHalClientThread;
import net.md_5.bungee.api.ChatColor;
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

	@SuppressWarnings({ "rawtypes", "unused", "deprecation" })
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		while (true) {
			for (ProxiedPlayer p : HALBungee.waitingLine.keySet()) {
				if (HALBungee.waitingLine.get(p).equalsIgnoreCase("HikaBrain1v1")) {
					String srv = HikaBrain1v1Utils.getCompGame(p);
					if (srv != null) {
						ServerInfo target = HALBungee.getInstance().getProxy().getServerInfo(srv);
						p.connect(target);
						toRemove.add(p);
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			removePlayers();

			int minNumber = 1;

			ArrayList<String> StartedGames = new ArrayList<String>();
			StartedGames.clear();
			ArrayList<String> DisponibleGames = new ArrayList<String>();
			DisponibleGames.clear();
			ArrayList<String> NotReadyGames = new ArrayList<String>();
			NotReadyGames.clear();

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
					String srvState = HALBungee.getInstance().getSQL().getState(Integer.parseInt(srvPort));
					if (srvState.equalsIgnoreCase("WAITING")) {
						DisponibleGames.add(srvPort);
					} else if (srvState.equalsIgnoreCase("BOOTING")) {
						NotReadyGames.add(srvPort);
					} else {
						StartedGames.add(srvPort);
					}
				}
			}
			/*
			 * System.out.println("Hikabrain1v1:  StartGames:" +
			 * StartedGames.size() + "  DisponibleGames:" +
			 * DisponibleGames.size() + "  NotReadyGames:" +
			 * NotReadyGames.size());
			 */

			if (DisponibleGames.size() == 0 && NotReadyGames.size() == 0) {
				JSONObject startServer = new JSONObject();
				startServer.put("action", "start");
				startServer.put("serverType", "HikaBrain1v1");
				MainHalClientThread.getClient().send(startServer.toString());
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}

			if (DisponibleGames.size() < minNumber && NotReadyGames.size() + DisponibleGames.size() < minNumber) {
				JSONObject startServer = new JSONObject();
				startServer.put("action", "start");
				startServer.put("serverType", "HikaBrain1v1");
				MainHalClientThread.getClient().send(startServer.toString());
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}

			if (DisponibleGames.size() > minNumber) {
				if (HALBungee.getInstance().getSQL().getPlayers(Integer.parseInt(DisponibleGames.get(0))) > 0) {
					String newServer = HikaBrain1v1Utils.getCompEmptyGame();
					for (ProxiedPlayer player : HALBungee.getInstance().getProxy().getServers()
							.get("HikaBrain1v1_" + DisponibleGames.get(0)).getPlayers()) {
						if (newServer == null) {
							player.sendMessage(ChatColor.RED
									+ "Les serveurs de HikaBrains1v1 sont en surcharge ressaie dans quelques instants");
							HubWaitingLine.add(player);
						}
					}
				}
				JSONObject stopOnSpigot = new JSONObject();
				stopOnSpigot.put("action", "stop");
				stopOnSpigot.put("serverPortOrName", DisponibleGames.get(0));
				MainHalClientThread.getClient().send(stopOnSpigot.toString());
				DisponibleGames.remove(DisponibleGames.get(0));
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
