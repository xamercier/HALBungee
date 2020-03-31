package ca.xamercier.hal.bungee.Games;

import ca.xamercier.hal.bungee.Games.WaitingLines.HikaBrain1v1WaitingLine;
import ca.xamercier.hal.bungee.Games.WaitingLines.HikaBrain2v2WaitingLine;
import ca.xamercier.hal.bungee.Games.WaitingLines.HubWaitingLine;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public enum GameManager {
	
	hub(false, "hub", 20, "hub_", "hub", "Hub"),
	HikaBrain1v1(true, "HikaBrain1v1", 2, "HikaBrain1v1_", "InGame", "HikaBrain"),
	HikaBrain2v2(true, "HikaBrain2v2", 4, "HikaBrain2v2_", "InGame", "HikaBrain");
	
	String gamePrefix = null;
	int maxGamePlayersPerGames = 0;
	String serverPrefixEnum;
	String gamestate;
	String nameforevents;
	
	GameManager(Boolean isAGame, String prefix, int maxPlayersPerGames, String serverPrefix, String gameState, String nameForEvents) {
		gamePrefix = prefix;
		maxGamePlayersPerGames = maxPlayersPerGames;
		serverPrefixEnum = serverPrefix;
		gamestate = gameState;
		nameforevents = nameForEvents;
	}
	
	public String getGamePrefix() {
		return gamePrefix;
	}
	
	public int getMaxPlayerPerGame() {
		return maxGamePlayersPerGames;
	}
	
	public String getServerPrefix() {
		return serverPrefixEnum;
	}
	
	public void sendPlayerToGame(ProxiedPlayer p) {
		if(this.equals(hub)) {
			HubWaitingLine.add(p);
		} else if (this.equals(HikaBrain1v1)) {
			HikaBrain1v1WaitingLine.add(p);
		} else if (this.equals(HikaBrain2v2)) {
			HikaBrain2v2WaitingLine.add(p);
		}
	}
	
	public String getPlayerGameState() {
		return gamestate;
	}
	
	public String getNameForEvents() {
		return nameforevents;
	}
	
}
