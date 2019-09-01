package com.fred.ecoxp.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import com.fred.ecoxp.EcoXP;
import com.fred.ecoxp.events.PlayerLevelModifyEvent;

public class Eco {
	public static String currency_symbol = "L";
	public static String prefix = ChatColor.GRAY + "[" + ChatColor.YELLOW + "EcoXP" + ChatColor.GRAY + "] " + ChatColor.WHITE;

	public static void setCS(String currency_symbol) {
		Eco.currency_symbol = currency_symbol;
	}
	
	public static void setPrefix(String chat_prefix) {
		Eco.prefix = chat_prefix;
	}
	
	@SuppressWarnings("deprecation")
	public static void set(String playername, int amount, EcoXP pl) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(playername);
		if(!op.hasPlayedBefore())
			return;
		
		String uuid = Bukkit.getOfflinePlayer(playername).getUniqueId().toString();
		pl.xp.set("xp." + uuid, amount);
		pl.savexp();
		PlayerLevelModifyEvent setEvent = new PlayerLevelModifyEvent(op.getPlayer(), amount, 2);
		pl.getServer().getPluginManager().callEvent(setEvent);
	}
	
	@SuppressWarnings("deprecation")
	public static int get(String playername, EcoXP pl) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(playername);
		if(!op.hasPlayedBefore())
			return 0;
		
		String uuid = Bukkit.getOfflinePlayer(playername).getUniqueId().toString();
		if(pl.xp().contains("xp." + uuid)) {
			int levels = pl.xp().getInt("xp." + uuid);
			return levels;
		} else {
			set(playername, 0, pl);
			return 0;
		}
	}
	
	public static void add(String playername, int amount, EcoXP pl) {
		int current = get(playername, pl);
		int newAmount = current + amount;
		set(playername, newAmount, pl);
	}
	
	public static boolean remove(String playername, int amount, EcoXP pl) {
		int current = get(playername, pl);
		if(amount <= current) {
			int newAmount = current - amount;
			set(playername, newAmount, pl);
			return true;
		} else {
			return false;
		}
	}
}
