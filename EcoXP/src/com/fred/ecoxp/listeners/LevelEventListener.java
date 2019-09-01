package com.fred.ecoxp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.fred.ecoxp.events.PlayerLevelModifyEvent;

public class LevelEventListener implements Listener {
	
	/*
	 * 0 = ADD
	 * 1 = REMOVE
	 * 2 = SET
	 */
	
	@EventHandler
	public void onLevelModify(PlayerLevelModifyEvent e) {
		Player p = e.getPlayer();
		int levels = p.getLevel();
		int change = e.getChange();
		
		switch(e.getAction()) {
		case 0:
			p.setLevel(levels + change);
			break;
		case 1:
			p.setLevel(levels - change);
			break;
		case 2:
			p.setLevel(change);
			break;
		}
	}
	
}
