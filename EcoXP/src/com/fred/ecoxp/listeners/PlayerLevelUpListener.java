package com.fred.ecoxp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import com.fred.ecoxp.EcoXP;
import com.fred.ecoxp.utils.Eco;

public class PlayerLevelUpListener implements Listener {
	
	EcoXP plugin;
	
	public PlayerLevelUpListener(EcoXP plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onLevelUp(PlayerLevelChangeEvent e) {
		Eco.set(e.getPlayer().getName(), e.getNewLevel(), plugin);
	}
}
