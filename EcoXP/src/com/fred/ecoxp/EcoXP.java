package com.fred.ecoxp;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.fred.ecoxp.commands.EcoXPCommand;
import com.fred.ecoxp.commands.LevelsCommand;
import com.fred.ecoxp.commands.XPShopCommand;
import com.fred.ecoxp.listeners.BuyListener;
import com.fred.ecoxp.listeners.InventoryClickListener;
import com.fred.ecoxp.listeners.LevelEventListener;
import com.fred.ecoxp.listeners.PlayerLevelUpListener;
import com.fred.ecoxp.listeners.XPGain;
import com.fred.ecoxp.utils.Eco;

import net.milkbowl.vault.permission.Permission;

public class EcoXP extends JavaPlugin {
	
	public static Permission perms = null;
	
	@Override
	public void onEnable() {
		
		setup(this);
		setupPermissions();
		
		// Register Commands.
		getCommand("levels").setExecutor(new LevelsCommand(this));
		getCommand("xpshop").setExecutor(new XPShopCommand(this));
		getCommand("ecoxp").setExecutor(new EcoXPCommand());
		
		// Register Listeners.
		getServer().getPluginManager().registerEvents(new XPGain(this), this);
		getServer().getPluginManager().registerEvents(new LevelEventListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
		getServer().getPluginManager().registerEvents(new BuyListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerLevelUpListener(this), this);
	}
	
	@Override
	public void onDisable() {}
	
	/*
	 * == CONFIGURING VAULT ==
	 */
	
	private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
	
	/*
	 * == CONFIGURING YAML FILES ==
	 */
	
	public FileConfiguration xp;
	File xpfile;
	// Setup xp.yml
	
	public void setup(Plugin p) {
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		if(getConfig().contains("eco-symbol")) {
			Eco.setCS(getConfig().getString("eco-symbol"));
		}
		
		if(getConfig().contains("chat-prefix")) {
			Eco.setPrefix(getConfig().getString("chat-prefix"));
		}
		
		if(!p.getDataFolder().exists()) {
			try {
				p.getDataFolder().createNewFile();
			}
			catch(IOException e) {
				Bukkit.getServer().getLogger().severe("Couldn't create xp folder.");
			}
		}
		
		xpfile = new File(p.getDataFolder(), "xp.yml");
		
		if(!xpfile.exists()) {
			try {
				xpfile.createNewFile();
			}
			catch(IOException e) {
				Bukkit.getServer().getLogger().severe("Could not create xp.yml");
			}
		}
		xp = YamlConfiguration.loadConfiguration(xpfile);
	}
	
	public FileConfiguration xp() {
		return xp;
	}
	
	public void savexp() {
		try {
			xp.save(xpfile);
		}
		catch (IOException e) {
			Bukkit.getServer().getLogger().severe("Couldn't save xp.yml");
		}
	}
}
