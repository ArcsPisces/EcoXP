package com.fred.ecoxp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.fred.ecoxp.EcoXP;

public class XPGain implements Listener {
	
	EcoXP plugin;
	public XPGain(EcoXP plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onGainXPEvent(PlayerExpChangeEvent e) {
		if(!plugin.getConfig().contains("xp-modifier"))
			return;
		
		double xp_modifier = plugin.getConfig().getDouble("xp-modifier");
		int initial_amount = e.getAmount();
		e.setAmount((int) (xp_modifier * initial_amount));
	}
}
