package ca.xamercier.hal.bungee.config;

import java.io.*;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import ca.xamercier.hal.bungee.HALBungee;

/**
 * This file is a part of the HAL project
 *
 * @author Xavier Mercier | xamercier
 */
public class ConfigurationManager {
	
	private JSONObject sqlConfig;
	private JSONObject halClient;

    /**
     * Creating new instance of the ConfigurationManager
     *
     * @throws IOException if config parser don't finhish with success
     */
    public ConfigurationManager() throws IOException {
        loadConfigurationFile();
    }

    /**
     * Loading the config.json file and parse it
     *
     * @throws IOException if config parser don't finish with success
     */
    private void loadConfigurationFile() throws IOException {
        URL defaultFile = getClass().getClassLoader().getResource("config.default.json");
        File configFile = new File(HALBungee.getInstance().getDataFolder() + File.separator, "config.json");
        if (!configFile.exists()) {
            if (defaultFile == null) {
                throw new FileNotFoundException("Unable to create default config file");
            }
            FileUtils.copyURLToFile(defaultFile, configFile);
        }

        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line.trim());
        }
        reader.close();
        String content = builder.toString();
        
        JSONObject globalConfiguration = new JSONObject(content);
                
        sqlConfig = globalConfiguration.getJSONObject("SQL");
        halClient = globalConfiguration.getJSONObject("HALClient");
        
        
    }
    
    
    public String getHalClientHost() {
    	return this.halClient.getString("host");
    }
    
    public int getHalClientPort() {
    	return this.halClient.getInt("port");
    }
    
    /**
     * Get host of the sql database
     *
     * @return the channel name
     */
    public String getSQLhost() {
        return this.sqlConfig.getString("host");
    }

    /**
     * Get database of the sql database
     *
     * @return the channel name
     */
    public String getSQLdatabase() {
        return this.sqlConfig.getString("database");
    }
    
    /**
     * Get user of the sql database
     *
     * @return the channel name
     */
    public String getSQLuser() {
        return this.sqlConfig.getString("user");
    }
    
    /**
     * Get password of the sql database
     *
     * @return the channel name
     */
    public String getSQLpassword() {
        return this.sqlConfig.getString("password");
    }

}
