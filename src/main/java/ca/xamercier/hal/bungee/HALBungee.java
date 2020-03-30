package ca.xamercier.hal.bungee;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

import ca.xamercier.hal.bungee.Games.GameManagerStartThread;
import ca.xamercier.hal.bungee.commands.CommandManager;
import ca.xamercier.hal.bungee.config.ConfigurationManager;
import ca.xamercier.hal.bungee.halClient.thread.MainHalClientThread;
import ca.xamercier.hal.bungee.listeners.ListenerManager;
import ca.xamercier.hal.bungee.utils.ErrorCatcher;
import ca.xamercier.hal.bungee.utils.SQL;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class HALBungee extends Plugin {

    public SQL sql;
	private static HALBungee instance;
	private ConfigurationManager configurationManager;
	public static HashMap<ProxiedPlayer, String> waitingLine = new HashMap<>();

	@Override
	public void onLoad() {
		instance = this;
		
		try {
			this.configurationManager = new ConfigurationManager();
		} catch (IOException e) {
			ErrorCatcher.catchError(e);
		}
	}
	

	@Override
	public void onEnable() {
		new CommandManager().register();
		new ListenerManager().register();
		
        sql = new SQL("jdbc:mysql://",configurationManager.getSQLhost(),configurationManager.getSQLdatabase(),configurationManager.getSQLuser(),configurationManager.getSQLpassword());
        sql.connection();
        
        sql.trunkate("servers");
        
        waitingLine.clear();
        
		MainHalClientThread MainHalClientThread = new MainHalClientThread();
		MainHalClientThread.start();
		
		/*
		SOCKETThread SOCKETThread = new SOCKETThread();
		SOCKETThread.start();
		*/
		
		ServerInfo serverInfoBuild = HALBungee.getInstance().getProxy().constructServerInfo("BuildServ",
				InetSocketAddress.createUnresolved("192.168.1.51", 25566), "BuildServ", false);
		HALBungee.getInstance().getProxy().getServers().put(serverInfoBuild.getName(), serverInfoBuild);
		//HALBungee.getInstance().getProxy().getServers().remove("lobby"); If we remove the auto generated server an error happens in console ... F*CK*NG BU**SH*T
                
        GameManagerStartThread GameManagerThread = new GameManagerStartThread();
        GameManagerThread.start();
	}
		
	@Override
	public void onDisable() {
	}

	/**
	 * Get configuration manager ({@link ConfigurationManager})
	 *
	 * @return the configuration manager
	 */
	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	/**
	 * Get bungee ({@link HALBungee}) instance
	 *
	 * @return the {@link HALBungee}
	 */
	public static HALBungee getInstance() {
		return instance;
	}

	/**
	 * Get {@link SQL} instance class
	 *
	 * @return the {@link SQL} class
	 */
	public SQL getSQL() {
		return sql;
	}
	
}
