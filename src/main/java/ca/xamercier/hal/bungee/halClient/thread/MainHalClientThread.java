package ca.xamercier.hal.bungee.halClient.thread;

import ca.xamercier.hal.bungee.HALBungee;
import ca.xamercier.hal.bungee.halClient.Client;


/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class MainHalClientThread extends Thread {

	public static boolean run = true;
	private static Client client;
	
	public static void setRun(Boolean run1) {
		run = run1;
	}
	
	public static Client getClient() {
		return client;
	}

	public void run() {
		String ipServ = HALBungee.getInstance().getConfigurationManager().getHalClientHost();
		int servPort = HALBungee.getInstance().getConfigurationManager().getHalClientPort();

		client = new Client();

		if (client.init(ipServ, servPort) == true) {
			while (run) {
			}
			System.out.println("[HalClientBungee] Le serveur HAL a ete deconnecte");
		} else {
			System.out.print("");
			System.out.println("[HalClientBungee] La connexion HAL: " + ipServ + ":" + servPort
					+ " a echouer ! Redemarrer l'application pour reessayez");
		}

	}

}
