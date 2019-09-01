package com.fred.ecoxp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLevelModifyEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Player p;
	private int change;
	private int action;
	
	public Player getPlayer() {
		return p;
	}
	
	public int getChange() {
		return change;
	}
	
	public PlayerLevelModifyEvent(Player p, int change, int action) {
		this.p = p;
		this.change = change;
		this.action = action;
	}
	
	public int getAction() {
		return action;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
