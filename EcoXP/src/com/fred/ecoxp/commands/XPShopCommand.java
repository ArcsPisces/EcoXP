package com.fred.ecoxp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.fred.ecoxp.EcoXP;
import com.fred.ecoxp.utils.Eco;
import com.fred.ecoxp.utils.Icon;
import com.fred.ecoxp.utils.Shop;

public class XPShopCommand implements CommandExecutor {
	
	EcoXP pl;
	public XPShopCommand(EcoXP plugin) {
		this.pl = plugin;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command c, String s, String[] args) {
		
		if(!(cs instanceof Player)) {
			cs.sendMessage(Eco.prefix + "You need to be a player to use that command.");
			return true;
		}
		
		Player p = (Player)cs;
		
		if(!(cs.hasPermission("ecoxp.xpshop"))) {
			cs.sendMessage(Eco.prefix + ChatColor.RED + "You don't have permission to do that.");
			return true;
		}
		
		// Establish how many items there are
		int ids = Shop.getAvailableItems(pl);
		if(ids < 1) {
			cs.sendMessage(Eco.prefix + "There are currently no items available for purchase.");
			return true;
		}
		
		if(!pl.getConfig().contains("gui-title")) {
			if(cs.hasPermission("ecoxp.admin")) {
				cs.sendMessage(Eco.prefix + "There is no title setup for the GUI in the config.");
			} else {
				cs.sendMessage(Eco.prefix + "The shop is not currently setup.");
			}
			
			return true;
		}
		
		Inventory gui = Bukkit.createInventory(null, 45, pl.getConfig().getString("gui-title") + " Page 1");
		
		for(int i = 36; i < 45; i++) {
			gui.setItem(i, Icon.nothing());
		}
		gui.setItem(38, Icon.previous());
		gui.setItem(42, Icon.next());
		
		// Determine how many items go on the first page.
		// Use Icon.class to create icons for each item
		if(ids > 26) {
			// Create an inventory for PAGE 1
			for(int i = 0; i < 27; i++) {
				// For each id create an icon
				if(!Shop.checkPlayerHasPerm(i, p, pl)) {
					gui.setItem(i, Icon.getIcon(i, pl));
				} else {
					gui.setItem(i, Icon.purchased());
				}
				
			}
		} else {
			// Create an inventory for ALL items.
			for(int i = 0; i < ids; i++) {
				if(!Shop.checkPlayerHasPerm(i, p, pl)) {
					gui.setItem(i, Icon.getIcon(i, pl));
				} else {
					gui.setItem(i, Icon.purchased());
				}
			}
		}
		
		// Present the page.
		p.openInventory(gui);
		
		return false;
	}
}
