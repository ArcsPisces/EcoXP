package com.fred.ecoxp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAttemptBuyEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Player p;
	private int id;
	
	public Player getPlayer() {
		return p;
	}
	
	public int getID() {
		return id;
	}
	
	public PlayerAttemptBuyEvent(Player p, int id) {
		this.p = p;
		this.id = id;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
