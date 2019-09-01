package com.fred.ecoxp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EcoXPCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender cs, Command c, String s, String[] args) {
		cs.sendMessage(ChatColor.YELLOW + "This plugin is using EcoXP.");
		cs.sendMessage(ChatColor.GRAY + "Created by fholland124");
		return false;
	}
}
