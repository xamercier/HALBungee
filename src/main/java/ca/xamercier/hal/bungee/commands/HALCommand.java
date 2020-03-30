package ca.xamercier.hal.bungee.commands;

import org.json.JSONObject;

import ca.xamercier.hal.bungee.halClient.thread.MainHalClientThread;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class HALCommand extends Command {

	/**
	 * HAL command
	 */
	public HALCommand() {
		super("hal");
	}

	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(new TextComponent("Only online players can execute this command"));
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) sender;

		if(!player.getName().equalsIgnoreCase("xamercier")) {
			return;
		}
		
		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "HAL Command usage:");
			player.sendMessage(ChatColor.RED + "</hal>  Show you the list of command");
			player.sendMessage(ChatColor.RED + "</hal startserver <servertype> >  Start a server of the type of the servertype");
			player.sendMessage(ChatColor.RED + "</hal stopserver <serverPort or serverName> > Stop the server with port or the name of the serverPort or the serverName");
		} else if (args.length == 1) {
			player.sendMessage(ChatColor.RED + "HAL Command usage:");
			player.sendMessage(ChatColor.RED + "</hal>  Show you the list of command");
			player.sendMessage(ChatColor.RED + "</hal startserver <servertype> >  Start a server of the type of the servertype");
			player.sendMessage(ChatColor.RED + "</hal stopserver <serverPort or serverName> > Stop the server with port or the name of the serverPort or the serverName");
		} else if (args.length == 2) {
			String subCommand = args[0];
			if (subCommand.equalsIgnoreCase("startserver")) {
				JSONObject registerOnNode = new JSONObject();
				registerOnNode.put("action", "start");
				registerOnNode.put("serverType", args[1]);
				MainHalClientThread.getClient().send(registerOnNode.toString());
				player.sendMessage(ChatColor.YELLOW + "Your server of type : " + args[1]
						+ " is normally started. If it's not, contact the administrators or verify your serverType.");
			} else if (subCommand.equalsIgnoreCase("stopserver")) {
				JSONObject stopOnSpigot = new JSONObject();
				stopOnSpigot.put("action", "stop");
				stopOnSpigot.put("serverPortOrName", args[1]);
				MainHalClientThread.getClient().send(stopOnSpigot.toString());
				player.sendMessage(ChatColor.YELLOW + "Your server is normally stoped. If it's not, contact the administrators or verify your serverName/serverPort.");
			} else {
				player.sendMessage(ChatColor.RED + "HAL Command usage:");
				player.sendMessage(ChatColor.RED + "</hal>  Show you the list of command");
				player.sendMessage(ChatColor.RED + "</hal startserver <servertype> >  Start a server of the type of the servertype");
				player.sendMessage(ChatColor.RED + "</hal stopserver <serverPort or serverName> > Stop the server with port or the name of the serverPort or the serverName");
			}
		} else {
			player.sendMessage(ChatColor.RED + "HAL Command usage:");
			player.sendMessage(ChatColor.RED + "</hal>  Show you the list of command");
			player.sendMessage(ChatColor.RED + "</hal startserver <servertype> >  Start a server of the type of the servertype");
			player.sendMessage(ChatColor.RED + "</hal stopserver <serverPort or serverName> > Stop the server with port or the name of the serverPort or the serverName");
		}
	}

}
