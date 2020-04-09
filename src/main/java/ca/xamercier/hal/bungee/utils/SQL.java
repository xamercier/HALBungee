package ca.xamercier.hal.bungee.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.md_5.bungee.api.connection.ProxiedPlayer;

 
/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class SQL {
   
    private Connection connection;
    private String urlbase,host,database,user,pass;
   
    public SQL(String urlbase, String host, String database, String user, String pass) {
        this.urlbase = urlbase;
        this.host = host;
        this.database = database;
        this.user = user;
        this.pass = pass;
    }
   
    public void connection(){
        if(!isConnected()){
            try {
                connection = DriverManager.getConnection(urlbase + host + "/" + database, user, pass);
                System.out.println("Database Connected");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
   
    public void disconnect(){
        if(isConnected()){
            try {
                connection.close();
                System.out.println("Database Unconnected");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
   
    public boolean isConnected(){
        return connection != null;
    }
    
    public void trunkate(String table) {
        java.sql.Statement stmt;
		try {
			stmt = connection.createStatement();
	        String sql = "TRUNCATE " + table;
	        stmt.executeUpdate(sql);
	        sql = "DELETE FROM " + table;
	        stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    
    
    /////////////////////////////////////////////////////////////SQL COMMANDS/////////////////////////////////////////////////////////
    
    
    
    public void addServer(String type, String port, String state, int players){
    	connection();
            try {
                PreparedStatement q = connection.prepareStatement("INSERT INTO servers(type,port,state,players) VALUES (?,?,?,?)");
                q.setString(1, type);
                q.setString(2, port);
                q.setString(3, state);
                q.setInt(4, players);
                q.execute();
                q.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    
    public void removeServer(String port){
    	connection();
        try {
            PreparedStatement rs = connection.prepareStatement("DELETE FROM servers WHERE port = ?");
            rs.setString(1, port);
            rs.executeUpdate();
            rs.close();
        } catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
	public void setHubID(String port, String hub) {
		connection();
		String serverPort = port;
		String serverHub = hub;
		try {
			PreparedStatement rs = connection.prepareStatement("UPDATE servers SET hub = ? WHERE port = ?");
			rs.setString(1, serverHub);
			rs.setString(2, serverPort);
			rs.executeUpdate();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getHubID(int servPort) {
		connection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT hub FROM servers WHERE port = ?");
			ps.setInt(1, servPort);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("hub");
			}
			return null;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public boolean hasHub(String hubID) {
		connection();
		try {
			PreparedStatement q = connection.prepareStatement("SELECT hub FROM servers WHERE hub = ?");
			q.setString(1, hubID);
			ResultSet resultat = q.executeQuery();
			while (resultat.next()) {
				q.close();
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void setState(String port, String state) {
		connection();
		String serverPort = port;
		String serverState = state;
		try {
			PreparedStatement rs = connection.prepareStatement("UPDATE servers SET state = ? WHERE port = ?");
			rs.setString(1, serverState);
			rs.setString(2, serverPort);
			rs.executeUpdate();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getState(int servPort) {
		connection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT state FROM servers WHERE port = ?");
			ps.setInt(1, servPort);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("state");
			}
			return null;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public void setPlayers(String port, int players) {
		connection();
		String serverPort = port;
		int serverPlayers = players;
		try {
			PreparedStatement rs = connection.prepareStatement("UPDATE servers SET players = ? WHERE port = ?");
			rs.setInt(1, serverPlayers);
			rs.setString(2, serverPort);
			rs.executeUpdate();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getPlayers(int servPort) {
		connection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT players FROM servers WHERE port = ?");
			ps.setInt(1, servPort);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("players");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	
	public void setPlayerGameState(ProxiedPlayer player, String newGameState) {
		connection();
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE joueurs SET gamestate = ? WHERE uuid = ?;");
			ps.setString(1, newGameState);
			ps.setString(2, player.getUniqueId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getPlayerGameState(ProxiedPlayer player) {
		connection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT gamestate FROM joueurs WHERE uuid = ?");
			ps.setString(1, player.getUniqueId().toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("gamestate");
			}
			return null;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void setPlayerServer(ProxiedPlayer player, String newServer) {
		connection();
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE joueurs SET server = ? WHERE uuid = ?;");
			ps.setString(1, newServer);
			ps.setString(2, player.getUniqueId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getPlayerServer(ProxiedPlayer player) {
		connection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT server FROM joueurs WHERE uuid = ?");
			ps.setString(1, player.getUniqueId().toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("server");
			}
			return null;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
 
}