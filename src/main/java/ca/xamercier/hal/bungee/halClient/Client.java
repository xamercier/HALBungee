package ca.xamercier.hal.bungee.halClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import ca.xamercier.hal.bungee.HALBungee;
import ca.xamercier.hal.bungee.Games.GameManager;
import ca.xamercier.hal.bungee.halClient.thread.MainHalClientThread;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class Client extends Thread {

	String hostName = "localhost";
	int portNumber = 12;
	boolean run = true;
	static Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;

	public boolean init(String ip, int servPort) {
		this.portNumber = servPort;
		this.hostName = ip;
		try {
			socket = new Socket(hostName, portNumber);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			return false;
		}
		this.start();
		return true;
	}

	public void send(String toSend) {
		if (toSend.startsWith("@")) {
			System.out.println("[HalClientBungee]: Vous ne pouvez pas commencer un message par un @");
		} else {
			out.println(toSend);
		}
	}

	public void run() {
		String msg = "";
		while (run) {
			try {
				msg = in.readLine();
			} catch (IOException e) {
				System.out.println("[HalClientBungee]: Erreur lecture LINE");
				run = false;
			}
			if (msg != null && run == true) {
				if (msg.equalsIgnoreCase("@ServerClosed")) {
					run = false;
				} else if (!msg.equalsIgnoreCase("") && msg != null && !msg.equalsIgnoreCase("null")) {
					boolean isAPlayerRequest = false;
					JSONObject message = new JSONObject(msg);
					String log = "[HalClientBungee] read => " + message.toString();

					
					
					try {
						String player = message.getString("player");
						String typeOfServer = message.getString("typeOfServer");
						if (player != null) {
							isAPlayerRequest = true;
							ProxiedPlayer p = HALBungee.getInstance().getProxy().getPlayer(player);
							if (p == null) {
								return;
							}
							
							/*
							 * UTILISE MAINTENANT LE GAMEMANAGER
							if (typeOfServer.equalsIgnoreCase("hub")) {
								HubWaitingLine.add(p);
								log += " ok.";
							} else if (typeOfServer.equalsIgnoreCase("HikaBrain1v1")) {
								HikaBrain1v1WaitingLine.add(p);
								log += " ok.";
							} else if (typeOfServer.equalsIgnoreCase("HikaBrain2v2")) {
								HikaBrain2v2WaitingLine.add(p);
								log += " ok.";
							}
							*/
							
							for(GameManager game : GameManager.values()) {
								if(game.getGamePrefix().equalsIgnoreCase(typeOfServer)) {
									game.sendPlayerToGame(p);
									log += " ok.";
									break;
								}
							}
							
						} else {
							isAPlayerRequest = false;
						}
					} catch (JSONException e) {
						isAPlayerRequest = false;
					}

					
					if (!isAPlayerRequest == true) {
						String action = message.getString("action");
						switch (action.toLowerCase()) {
						case "register": {
							log += " ok.";
							String name = message.getString("name");
							String ip = message.getString("ip");
							int port = message.getInt("port");
							String serverType = message.getString("serverType");
							String portString = Integer.toString(port);
							ServerInfo serverInfo = HALBungee.getInstance().getProxy().constructServerInfo(name,
									InetSocketAddress.createUnresolved(ip, port), serverType, false);
							HALBungee.getInstance().getProxy().getServers().put(serverInfo.getName(), serverInfo);
							HALBungee.getInstance().getSQL().addServer(serverType, portString, "BOOTING", 0);
							break;
						}
						case "unregister": {
							log += " ok.";
							String name = message.getString("name");
							int port = message.getInt("port");
							String portString = Integer.toString(port);
							HALBungee.getInstance().getSQL().removeServer(portString);
							HALBungee.getInstance().getProxy().getServers().remove(name);
							break;
						}
						default: {
							log += " skipped.";
						}
						}
					}
					
					
					System.out.println(log);
				}
			}
		}
		try {
			socket.close();
			MainHalClientThread.setRun(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopClient() {
		run = false;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
