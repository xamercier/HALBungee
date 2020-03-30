package ca.xamercier.hal.bungee.thread;

import ca.xamercier.hal.bungee.HALBungee;
import ca.xamercier.hal.bungee.utils.SQL;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class SQLThread extends Thread {

	public void run() {
		while (true) {
			try {
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			HALBungee.getInstance().getSQL().disconnect();
			HALBungee.getInstance().sql = null;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			HALBungee.getInstance().sql = new SQL("jdbc:mysql://", HALBungee.getInstance().getConfigurationManager().getSQLhost(), HALBungee.getInstance().getConfigurationManager().getSQLdatabase(),
					HALBungee.getInstance().getConfigurationManager().getSQLuser(), HALBungee.getInstance().getConfigurationManager().getSQLpassword());;
			HALBungee.getInstance().getSQL().connection();
		}
	}

}