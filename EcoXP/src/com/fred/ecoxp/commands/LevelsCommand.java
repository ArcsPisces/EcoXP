package com.fred.ecoxp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fred.ecoxp.EcoXP;
import com.fred.ecoxp.utils.Eco;

public class LevelsCommand implements CommandExecutor {
	
	EcoXP plugin;
	public LevelsCommand(EcoXP plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command c, String s, String[] args) {
		
		if(!(cs.hasPermission("ecoxp.levels"))) {
			cs.sendMessage(Eco.prefix + ChatColor.RED + "You don't have permission to do that.");
			return true;
		}
		
		if(args.length == 0) {
			if(!(cs instanceof Player)) {
				cs.sendMessage(Eco.prefix + ChatColor.RED + "You need to be a player to use that command.");
				return true;
			}
			
			Player p = (Player)cs;
			p.sendMessage(Eco.prefix + "You have: " + Eco.get(p.getName(), plugin) + " Levels.");
		} else if(args.length != 3) {
			cs.sendMessage(Eco.prefix + "/levels <player> <add/remove/set> <amount>");
		} else {
			
			if(!(cs.hasPermission("ecoxp.levels.modify"))) {
				cs.sendMessage(Eco.prefix + ChatColor.RED + "You don't have permission to do that.");
				return true;
			}
			
			String playername = args[0].toUpperCase();
			
			String action = args[1];
			int amount = Integer.parseInt(args[2]);
			
			switch(action) {
			case "add":
				Eco.add(playername, amount, plugin);
				cs.sendMessage(Eco.prefix + "Added " + amount + "LV to " + playername + "'s balance.");
				break;
			case "remove":
				boolean removed = Eco.remove(playername, amount, plugin);
				if(removed) {
					cs.sendMessage(Eco.prefix + "Removed " + amount + "L from " + playername + "'s balance.");
				} else {
					cs.sendMessage(Eco.prefix + "The amount is greater than the players level.");
				}
				break;
			case "set":
				Eco.set(playername, amount, plugin);
				cs.sendMessage(Eco.prefix + "Set " + playername + "'s levels to " + amount);
				break;
			}
		}
		
		return false;
	}
	
}
